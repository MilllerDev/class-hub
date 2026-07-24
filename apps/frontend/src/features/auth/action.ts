"use server";

import { api } from "@/lib/api";
import { setServerTokens } from "@/lib/cookies";
import { redirect } from "next/navigation";
import { Auth, AuthResponse, LoginRequest } from "./types";

async function login(_prevState: AuthResponse | null, formData: FormData) {
  const request: LoginRequest = {
    email: String(formData.get("email")),
    password: String(formData.get("password")),
  };

  const res = await api.post<Auth>("/auth/login", request);

  if (res.data) {
    await setServerTokens({
      accessToken: res.data.accessToken,
      refreshToken: res.data.refreshToken,
    });
    redirect(`/${res.data.role.toLocaleLowerCase()}`);
  }

  return res;
}

export { login };

