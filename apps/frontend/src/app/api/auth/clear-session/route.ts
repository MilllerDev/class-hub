import { NextResponse } from "next/server";
import { clearServerTokens } from "@/lib/cookies";

export async function GET(request: Request) {
  await clearServerTokens();
  const url = new URL("/login", request.url);
  return NextResponse.redirect(url);
}