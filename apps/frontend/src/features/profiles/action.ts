"use server";

import { api } from "@/lib/api";
import { Profile } from "./types";

async function getProfile() {
  return await api.get<Profile>("/profiles");
}

async function getProfiles() {
  return await api.get<Profile[]>("/profiles/list");
}

export { getProfile, getProfiles };

