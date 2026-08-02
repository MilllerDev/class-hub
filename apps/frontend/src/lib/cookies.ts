import { cookies } from "next/headers";
import { NextRequest } from "next/server";

const TOKENS = {
  access: "access_token",
  refresh: "refresh_token",
} as const;

const COOKIE_OPTIONS = {
  httpOnly: true,
  secure: process.env.NODE_ENV === "production",
  sameSite: "lax" as const,
  path: "/",
} as const;

export async function setServerTokens(tokens: {
  accessToken: string;
  refreshToken: string;
}) {
  const cookieStore = await cookies();
  cookieStore.set(TOKENS.access, tokens.accessToken, {
    ...COOKIE_OPTIONS,
    maxAge: 60 * 60,
  });
  cookieStore.set(TOKENS.refresh, tokens.refreshToken, {
    ...COOKIE_OPTIONS,
    maxAge: 60 * 60 * 24,
  });
}

export async function getServerTokens() {
  const cookieStore = await cookies();
  return {
    accessToken: cookieStore.get(TOKENS.access)?.value,
    refreshToken: cookieStore.get(TOKENS.refresh)?.value,
  };
}

export async function clearServerTokens() {
  const cookieStore = await cookies();
  cookieStore.delete(TOKENS.access);
  cookieStore.delete(TOKENS.refresh);
}

export function getProxyCookies(request: NextRequest) {
  return {
    accessToken: request.cookies.get(TOKENS.access)?.value,
    refreshToken: request.cookies.get(TOKENS.refresh)?.value,
  };
}
