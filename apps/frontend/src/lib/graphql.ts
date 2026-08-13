import { redirect } from "next/navigation";
import { getServerTokens } from "./cookies";

const BASE_URL = process.env.API_URL;

async function gqlRequest<T>(
  query: string,
  options: RequestInit = {},
  variables?: Record<string, unknown>,
): Promise<T> {
  const { accessToken } = await getServerTokens();

  const headers: HeadersInit = { "Content-Type": "application/json" };
  if (accessToken) {
    (headers as Record<string, string>)["Authorization"] =
      `Bearer ${accessToken}`;
  }

  const res = await fetch(`${BASE_URL}/graphql`, {
    method: "POST",
    headers,
    body: JSON.stringify({ query, variables }),
    ...options,
  });

  if (res.status === 401 || res.status === 403) {
    redirect("/api/auth/clear-session");
  }

  const body = await res.json();

  if (body.errors?.length) {
    throw new Error(body.errors[0].message);
  }

  return body.data as T;
}

export const graphql = {
  query<T>(
    query: string,
    options?: RequestInit,
    variables?: Record<string, unknown>,
  ) {
    return gqlRequest<T>(query, options, variables);
  },
};
