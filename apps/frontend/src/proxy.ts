import type { NextProxy } from "next/server";
import { NextResponse } from "next/server";
import { updateSession } from "./features/auth/action";
import { getProxyCookies } from "./lib/cookies";

const protectedRoutes = ["/student", "/admin", "/teacher"];
const authRoutes = ["/login", "/register"];

export const proxy: NextProxy = async (request) => {
  const path = request.nextUrl.pathname;
  const { accessToken, refreshToken } = getProxyCookies(request);
  const isProtected = protectedRoutes.some((r) => path.startsWith(r));
  const isAuthRoute = authRoutes.includes(path);

  if (!accessToken && refreshToken && (isProtected || isAuthRoute)) {
    const { success, role } = await updateSession(refreshToken);
    if (success && role) {
      return NextResponse.redirect(
        new URL(`/${role.toLocaleLowerCase()}`, request.url),
      );
    } else {
      return NextResponse.redirect(new URL("/login", request.url));
    }
  }

  if (protectedRoutes.some((r) => path.startsWith(r)) && !accessToken) {
    return NextResponse.redirect(new URL("/login", request.url));
  }

  if (authRoutes.includes(path) && accessToken) {
    return NextResponse.redirect(new URL("/student", request.url));
  }
  return NextResponse.next();
};

export const config = {
  matcher: ["/((?!api|_next/static|_next/image|favicon.ico).*)"],
};
