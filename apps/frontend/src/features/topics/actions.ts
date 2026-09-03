"use server";

import { api } from "@/lib/api";
import { updateTag } from "next/cache";
import { Topic, TopicRequest } from "./types";

async function createTopic(request: TopicRequest) {
  const res = await api.post<Topic>("/topics", request);
  if (res.success) {
    updateTag("topics");
  }
  return res;
}

async function getTopics(slug: string) {
  return await api.get<Topic[]>(`/topics/course/${slug}`, {
    next: { revalidate: 3600, tags: ["topics"] },
  });
}

async function getTopic(id: string) {
  return await api.get<Topic>(`/topics/${id}`, {
    next: { revalidate: 3600, tags: ["topics"] },
  });
}

async function updateTopic(id: string, request: TopicRequest) {
  const res = await api.patch<Topic>(`/topics/${id}`, request);
  if (res.success) {
    updateTag("topics");
  }
  return res;
}

async function deleteTopic(id: string) {
  const res = await api.delete<Topic>(`/topics/${id}`);
  if (res.success) {
    updateTag("topics");
  }
  return res;
}

async function upsertTopic(request: TopicRequest, id?: string) {
  return id ? await updateTopic(id, request) : await createTopic(request);
}

export {
  createTopic,
  deleteTopic,
  getTopic,
  getTopics,
  updateTopic,
  upsertTopic
};

