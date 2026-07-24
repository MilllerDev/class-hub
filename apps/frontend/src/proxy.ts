import { NextResponse } from "next/server";
import type { NextProxy } from "next/server";

const protectedRoutes = ["/student"];
const authRoutes = ["/login", "/register"];

export const proxy: NextProxy = (request) => {
  const path = request.nextUrl.pathname;
  const accessToken = request.cookies.get("access_token")?.value;

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
