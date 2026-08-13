import { z } from "zod";

export const changeStatusSchema = z.object({
  enabled: z.boolean(),
});

export const changeRoleSchema = z.object({
  role: z.string().min(1, { message: "Selecciona un rol" }),
});

export type ChangeStatusFields = z.infer<typeof changeStatusSchema>;
export type ChangeRoleFields = z.infer<typeof changeRoleSchema>;