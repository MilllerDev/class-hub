export type Topic = {
  id: string;
  title: string;
  courseId: string;
  createdAt: string;
};

export type TopicRequest = {
  title: string;
  courseId: string;
};