"use client";

import { Button } from "@/shared/components/ui/button";
import { Textarea } from "@/shared/components/ui/textarea";
import { zodResolver } from "@hookform/resolvers/zod";
import { Loader2, Send } from "lucide-react";
import { useState } from "react";
import { SubmitHandler, useForm } from "react-hook-form";
import { sendMessage } from "../action";
import { SendMessageFields, sendMessageSchema } from "../schemas";
import { useResources } from "./resources-provider";

export function InputSendMessage({ topicId }: { topicId: string }) {
  const { checked } = useResources();
  const [isSending, setIsSending] = useState(false);

  const { register, handleSubmit, reset, watch } = useForm<SendMessageFields>({
    resolver: zodResolver(sendMessageSchema),
  });

  const onSubmit: SubmitHandler<SendMessageFields> = async (data) => {
    setIsSending(true);
    const success = await sendMessage(topicId, {
      question: data.question,
      resourceIds: [...checked],
    });
    if (success) {
      reset();
    }
    setIsSending(false);
  };
  const question = watch("question");

  return (
    <div className="shrink-0 border-t border-border bg-background px-4 py-3">
      <form
        onSubmit={handleSubmit(onSubmit)}
        className="mx-auto flex max-w-2xl items-end gap-2"
      >
        <Textarea
          {...register("question")}
          placeholder="Escribe un mensaje sobre este tema..."
          rows={2}
          className="resize-none h-auto min-h-10 max-h-18"
        />
        <Button
          size="icon"
          type="submit"
          disabled={isSending || !question?.trim() || checked.size === 0}
        >
          {isSending ? (
            <Loader2 className="size-4 animate-spin" />
          ) : (
            <Send className="size-4" />
          )}
        </Button>
      </form>
    </div>
  );
}
