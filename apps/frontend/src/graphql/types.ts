import { Profile } from "@/features/profiles/types";

export type Course = {
  id: string;
  name: string;
  description: string | null;
  slug: string;
  enabled: boolean;
  createdAt: string;
  profile: Profile | null;
};
