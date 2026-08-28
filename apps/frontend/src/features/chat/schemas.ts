import { z } from "zod";

export const sendMessageSchema = z.object({
  question: z
    .string()
    .trim()
    .min(1, { message: "La pregunta es obligatoria" })
    .max(2000, { message: "La pregunta no puede superar los 2000 caracteres" }),
});

export type SendMessageFields = z.infer<typeof sendMessageSchema>;
