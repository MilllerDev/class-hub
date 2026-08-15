import { User } from "../auth/types";

export type Profile = {
  id: string;
  name: string;
  lastName: string;
  avatarUrl: string | null;
  user: User;
};

export type GetProfilesParams = {
  q?: string;
  page?: number;
  size?: number;
};

export type ChangeStatusProps = {
  userId: string;
  enabled: boolean;
  userName: string;
};

export type ChangeRoleProps = {
  userId: string;
  currentRole: string;
  userName: string;
};
