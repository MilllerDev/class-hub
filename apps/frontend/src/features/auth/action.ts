"use server";

import { api } from "@/lib/api";
import { setServerTokens } from "@/lib/cookies";
import { redirect } from "next/navigation";
import {
  Auth,
  AuthResponse,
  LoginRequest,
  RegisterRequest,
  RegisterResponse,
  RegisterUser,
} from "./types";

async function login(_prevState: AuthResponse | null, formData: FormData) {
  const request: LoginRequest = {
    email: String(formData.get("email")),
    password: String(formData.get("password")),
  };

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

async function register(
  _prevState: RegisterResponse | null,
  formData: FormData,
) {
  const request: RegisterRequest = {
    name: String(formData.get("name")),
    lastName: String(formData.get("lastName")),
    user: {
      email: String(formData.get("email")),
      password: String(formData.get("password")),
    },
  };

  const res = await api.post<RegisterUser>("/auth/register", request);

  if (res.success) {
    const params = new URLSearchParams(request.user.email);
    redirect(`/activate-account?email=${params}`);
  }

  return res;
}

async function resendActivation(email: string) {
  console.log("Reenviar activación a:", email);
}

export { login, register, resendActivation };

