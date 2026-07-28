import { z } from "zod";

export const loginSchema = z.object({
  email: z.email({ message: "Correo electrónico inválido" }),
  password: z
    .string()
    .trim()
    .min(1, { message: "La contraseña es obligatoria" }),
});

export const registerSchema = z
  .object({
    name: z
      .string()
      .trim()
      .min(3, { message: "El nombre debería de tener al menos 3 carácteres" })
      .max(20, { message: "El nombre no debe de exceder los 20 carácteres" }),
    lastName: z
      .string()
      .min(6, {
        message: "El apellido debería de tener al menos 6 carácteres",
      })
      .max(50, { message: "El apellido no debe de exeder los 50 carácteres" }),
    user: z.object({
      email: z.email({ message: "Correo electrónico inválido" }),
      password: z.string().trim().min(6, {
        message: "La contraseña debería de tener mínimo 6 carácteres",
      }),
    }),
    confirmPassword: z.string(),
  })
  .refine((data) => data.user.password === data.confirmPassword, {
    message: "Las contraseñas no coinciden",
    path: ["confirmPassword"],
  });

export type RegisterFileds = z.infer<typeof registerSchema>;
