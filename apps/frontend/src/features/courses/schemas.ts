import { z } from "zod";

export const courseSchema = z.object({
  name: z
    .string()
    .trim()
    .min(1, { message: "El nombre del curso es obligatorio" })
    .max(100, { message: "El nombre no debe de exceder los 100 carácteres" }),
  description: z
    .string()
    .trim()
    .max(500, {
      message: "La descripción no debe de exceder los 500 carácteres",
    })
    .optional()
    .nullable(),
  userId: z.uuid().optional().nullable(),
});

export type courseFields = z.infer<typeof courseSchema>;
