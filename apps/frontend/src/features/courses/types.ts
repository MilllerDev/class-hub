export type Course = {
  id: string;
  name: string;
  description: string | null;
  slug: string;
  userId: string | null;
  enabled: boolean;
};

export type CourseRequest = {
  name: string;
  description?: string | null;
  userId?: string | null;
  useAi: boolean;
};
