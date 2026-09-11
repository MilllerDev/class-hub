"use server";

import { api } from "@/lib/api";
import { updateTag } from "next/cache";
import type { ChatMessage, SendMessageRequest } from "./types";

async function sendMessage(topicId: string, request: SendMessageRequest) {
  const { data, message, success } = await api.post<ChatMessage>(
    `/topic/${topicId}/chat/messages`,
    request,
  );
  console.log("sendMessage response:", { data, message, success });
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

async function deleteChat(topicId: string, id: string) {
  const { message, success } = await api.delete(
    `/topic/${topicId}/chat/messages/${id}`,
  );
  if (success) {
    updateTag(`chat-messages-${topicId}`);
  }
  return { message, success };
}

export { deleteChat, getChatHistory, sendMessage };

