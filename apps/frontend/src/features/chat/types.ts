export type SendMessageRequest = {
  question: string;
  resourceIds: string[];
};

export type ChatMessage = {
  id: string;
  userId: string;
  topicId: string;
  question: string;
  answer: string;
  createdAt: string;
};
