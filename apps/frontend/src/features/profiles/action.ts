"use server";

import { api } from "@/lib/api";
import { Page } from "@/shared/types/page";
import { updateTag } from "next/cache";
import { User } from "../auth/types";
import { Profile, ProfileParams } from "./types";

async function getProfile() {
  return await api.get<Profile>("/profiles", {
    next: { tags: ["user-me"], revalidate: 3600 },
  });
}

async function getProfileByRole(role?: string) {
  return await api.get<Profile[]>(`/profiles/role?role=${role}`, {
    next: { revalidate: 3600, tags: [`${role}-profiles`] },
  });
}

async function getProfiles({ page = 1, size = 10 }: ProfileParams) {
  const currentSize = size > 4 ? size : 10;
  const params = new URLSearchParams({
    page: String(Math.max(0, page - 1)),
    size: String(currentSize),
  });
  return await api.get<Page<Profile>>(`/profiles/list?${params.toString()}`, {
    next: { revalidate: 3600, tags: ["list-users"] },
  });
}
async function searchProfiles({ q, role, page = 1, size = 10 }: ProfileParams) {
  const currentSize = size > 4 ? size : 10;
  const params = new URLSearchParams({
    page: String(Math.max(0, page - 1)),
    size: String(currentSize),
  });
  if (q) params.set("q", q);
  if (role) params.set("role", role);
  return await api.get<Page<Profile>>(`/profiles/list?${params.toString()}`);
}

async function listProfiles({ q, page, role, size }: ProfileParams) {
  return q || role
    ? await searchProfiles({ q, role, page, size })
    : await getProfiles({ page, size });
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

export { changeRole, changeStatus, getProfile, getProfileByRole, listProfiles };

