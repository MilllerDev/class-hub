"use server";

import { api } from "@/lib/api";
import { clearServerTokens, setServerTokens } from "@/lib/cookies";
import { redirect } from "next/navigation";
import {
  Auth,
  ChangePasswordRequest,
  LoginRequest,
  RegisterRequest,
  User,
} from "./types";

async function login(request: LoginRequest) {
  const res = await api.post<Auth>("/auth/login", request);

  if (res.data && res.success) {
    await setServerTokens({
      accessToken: res.data.accessToken,
      refreshToken: res.data.refreshToken,
    });
    redirect(`/${res.data.role.toLocaleLowerCase()}`);
  }
  return res;
}

async function register(request: RegisterRequest) {
  const res = await api.post<User>("/auth/register", request);

  if (res.success) {
    const params = new URLSearchParams({
      email: request.user.email,
    });
    redirect(`/activate-account?${params.toString()}`);
  }
  return res;
}

async function activateAccount(token: string) {
  const res = await api.get<Auth>(`/auth/activate?token=${token}`);
  if (res.success) {
    await setServerTokens({
      accessToken: res.data.accessToken,
      refreshToken: res.data.refreshToken,
    });
  }
  return res;
}

async function resendActivation(email: string) {
  const res = await api.post("/auth/resend-activation", { email });
  return { message: res.message, success: res.success };
}

async function updateSession(token: string) {
  const res = await api.post<Auth>("/auth/refresh", { refreshToken: token });
  const { data, success } = res;
  if (success && data) {
    const { accessToken, refreshToken } = data;
    await setServerTokens({ accessToken, refreshToken });
    return { success, role: data.role };
  }
  await clearServerTokens();
  return { success };
}

async function changePassword(request: ChangePasswordRequest) {
  const res = await api.post<Auth>("/auth/change-password", request);
  const { data, success, message } = res;
  if (success && data) {
    const { accessToken, refreshToken } = data;
    await setServerTokens({ accessToken, refreshToken });
    return { success, message };
  }
  return { success, message };
}

export {
  activateAccount,
  changePassword,
  login,
  register,
  resendActivation,
  updateSession
};

