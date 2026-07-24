import { ApiResponse } from "@/shared/types/api-response";

export type LoginRequest = {
  email: string;
  password: string;
};

export type Auth = {
  accessToken: string;
  refreshToken: string;
  role: string;
};

export type AuthResponse = ApiResponse<Auth>;
