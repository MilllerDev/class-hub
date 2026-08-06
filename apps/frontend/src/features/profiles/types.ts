import { User } from "../auth/types";

export type Profile = {
  id: string;
  name: string;
  lastName: string;
  avatarUrl: string | null;
  user: User;
};
