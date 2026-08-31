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

export type ChangePasswordRequest = {
  password: string;
  newPassword: string;
  confirmPassword: string;
};

export type Auth = {
  accessToken: string;
  refreshToken: string;
  role: string;
};

export type User = {
  id: string;
  email: string;
  role: string;
  enabled: boolean;
  lastLogin: string;
  createdAt: string;
};
