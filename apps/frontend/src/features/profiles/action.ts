"use server";

import { api } from "@/lib/api";
import { updateTag } from "next/cache";
import { User } from "../auth/types";
import { Profile } from "./types";

async function getProfile() {
  return await api.get<Profile>("/profiles", {
    next: { tags: ["user-me"], revalidate: 3600 },
  });
}

async function getProfiles() {
  return await api.get<Profile[]>("/profiles/list", {
    next: { tags: ["list-users"], revalidate: 3600 },
  });
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

export { changeRole, changeStatus, getProfile, getProfiles };
