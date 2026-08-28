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

export enum ResourceType {
  PDF = "PDF",
  DOCX = "DOCX",
  PPTX = "PPTX",
}

export type TopicResource = {
  id: string;
  fileName: string;
  fileUrl: string;
  fileType: ResourceType;
  topicId: string;
  createdAt: string;
};

export type TopicResourceRequest = {
  fileName: string;
  fileUrl: string;
  fileType: string;
};
