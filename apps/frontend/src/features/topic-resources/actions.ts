"use server";

import { api } from "@/lib/api";
import { updateTag } from "next/cache";
import { TopicResource, TopicResourceRequest } from "./types";

async function processUploadFile(request: TopicResourceRequest) {
  const res = await api.post<TopicResource>("/topic-resources", request);
  if (res.success) {
    updateTag("resources");
  }
  return res;
}

async function getTopicResources(topicId: string) {
  return await api.get<TopicResource[]>(`/topic-resources/topic/${topicId}`, {
    next: { revalidate: 3600, tags: ["resources"] },
  });
}

async function deleteTopicResource(topicId: string, id: string) {
  const res = await api.delete<TopicResource>(
    `/topic/${topicId}/resources/${id}`,
  );
  if (res.success) {
    updateTag("resources");
  }
  return res;
}

export { deleteTopicResource, getTopicResources, processUploadFile };
