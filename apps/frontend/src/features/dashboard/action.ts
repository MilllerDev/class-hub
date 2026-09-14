"use server";

import { lastChats } from "@/graphql/queries/chats";
import { dashboard } from "@/graphql/queries/dashboard";
import { graphql } from "@/lib/graphql";
import { ChatMessage } from "../chat/types";
import { Dashboard } from "./types";

async function getDashboard() {
  const data = await graphql.query<{ dashboard: Dashboard }>(dashboard, {
    next: { revalidate: 3600, tags: ["dashboard"] },
  });
  return data.dashboard;
}

async function getLastChats() {
  const data = await graphql.query<{ lastChats: ChatMessage[] }>(lastChats, {
    next: { revalidate: 3600, tags: ["last-chats"] },
  });
  return data.lastChats;
}

export { getDashboard, getLastChats };

