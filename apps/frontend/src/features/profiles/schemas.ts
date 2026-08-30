import { z } from "zod";

export const changeStatusSchema = z.object({
  enabled: z.boolean(),
});

export const changeRoleSchema = z.object({
  role: z.string().min(1, { message: "Selecciona un rol" }),
});

export const profileSchema = z.object({
  name: z
    .string()
    .trim()
    .min(3, { message: "El nombre debe tener al menos 3 caracteres" }),
  lastName: z
    .string()
    .trim()
    .min(6, { message: "El apellido debe tener al menos 6 caracteres" }),
});

export type ProfileSchema = z.infer<typeof profileSchema>;
export type ChangeStatusFields = z.infer<typeof changeStatusSchema>;
export type ChangeRoleFields = z.infer<typeof changeRoleSchema>;
