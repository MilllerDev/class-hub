import { ApiResponse } from "@/shared/types/api-response";

export type LoginRequest = {
  email: string;
  password: string;
};

export type RegisterRequest = {
  name: string;
  lastName: string;
  user: {
    email: string;
    password: string;
  };
};

export type Auth = {
  accessToken: string;
  refreshToken: string;
  role: string;
};

export type AuthResponse = ApiResponse<Auth>;

export type RegisterUser = {
  id: string;
  email: string;
  role: string;
  enabled: boolean;
};

export type RegisterResponse = ApiResponse<RegisterUser>;
