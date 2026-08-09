export type Course = {
  id: string;
  name: string;
  description: string | null;
  slug: string;
  enabled: boolean;
  createdAt: string;
  userId: string | null;
};

export type CourseRequest = {
  name: string;
  description?: string | null;
  userId?: string | null;
  useAi: boolean;
};
