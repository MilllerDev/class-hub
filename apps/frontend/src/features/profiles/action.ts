"use server";

import { api } from "@/lib/api";
import { Page } from "@/shared/types/page";
import { updateTag } from "next/cache";
import { User } from "../auth/types";
import { GetProfilesParams, Profile } from "./types";

async function getProfile() {
  return await api.get<Profile>("/profiles", {
    next: { tags: ["user-me"], revalidate: 3600 },
  });
}

async function getProfiles({
  page = 0,
  size = 10,
}: { page?: number; size?: number } = {}) {
  const params = new URLSearchParams({
    page: String(page),
    size: String(size),
  });
  return await api.get<Page<Profile>>(`/profiles/list?${params.toString()}`, {
    next: { revalidate: 3600, tags: ["list-users"] },
  });
}
async function searchProfiles({ q, page = 0, size = 10 }: GetProfilesParams) {
  const params = new URLSearchParams({
    q: q ?? "",
    page: String(page),
    size: String(size),
  });
  return await api.get<Page<Profile>>(`/profiles/list?${params.toString()}`);
}

async function listProfiles(page: number, q?: string) {
  return q ? await searchProfiles({ q, page }) : await getProfiles({ page });
}

async function changeStatus(id: string, status: boolean) {
  const { message, success } = await api.patch<User>(`/users/${id}/status`, {
    enabled: status,
  });
  if (success) {
    updateTag("list-users");
  }
  return { success, message };
}

async function changeRole(id: string, role: string) {
  const { message, success } = await api.patch<User>(`/users/${id}/role`, {
    role,
  });
  if (success) {
    updateTag("list-users");
  }
  return { success, message };
}

export { changeRole, changeStatus, getProfile, listProfiles };
