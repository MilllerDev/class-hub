"use server";

import { api } from "@/lib/api";
import { updateTag } from "next/cache";
import { TopicResource, TopicResourceRequest } from "./types";

async function processUploadFile(
  topicId: string,
  request: TopicResourceRequest,
) {
  const res = await api.post<TopicResource>(
    `/topic/${topicId}/resources`,
    request,
  );
  if (res.success) {
    updateTag("resources");
  }
  return res;
}

async function getTopicResources(topicId: string) {
  return await api.get<TopicResource[]>(`/topic/${topicId}/resources`, {
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
