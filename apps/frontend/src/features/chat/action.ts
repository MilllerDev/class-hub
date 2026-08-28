"use server";

import { api } from "@/lib/api";
import { updateTag } from "next/cache";
import type { ChatMessage, SendMessageRequest } from "./types";

async function sendMessage(topicId: string, request: SendMessageRequest) {
  const { success } = await api.post<ChatMessage>(
    `/topic/${topicId}/chat/messages`,
    request,
  );
  if (success) {
    updateTag(`chat-messages-${topicId}`);
  }
  return success;
}

async function getChatHistory(topicId: string) {
  return await api.get<ChatMessage[]>(`/topic/${topicId}/chat/messages`, {
    next: { revalidate: 3600, tags: [`chat-messages-${topicId}`] },
  });
}

export { getChatHistory, sendMessage };
