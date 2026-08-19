import { z } from "zod";

export const topicSchema = z.object({
  title: z
    .string()
    .trim()
    .min(1, { message: "El título del tema es obligatorio" })
    .max(100, { message: "El título no debe de exceder los 100 carácteres" }),
});

export type topicFields = z.infer<typeof topicSchema>;