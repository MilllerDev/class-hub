import { formatDate } from "@/lib/utils";
import { Bot, User } from "lucide-react";
import { getChatHistory } from "../action";
import { ChatAutoScroll } from "./chat-auto-scroll";
import { ChatMessageDeleteButton } from "./chat-message-delete-button";

export async function ChatHistory({ topicId }: { topicId: string }) {
  const { data: messages } = await getChatHistory(topicId);

  return (
    <div className="space-y-4">
      {messages.map((msg) => (
        <div key={msg.id} className="space-y-2">
          <div className="flex justify-end">
            <div className="flex max-w-[80%] gap-2">
              <div className="min-w-0 rounded-2xl rounded-tr-md bg-primary px-4 py-2.5 text-sm text-primary-foreground">
                <p className="whitespace-pre-wrap warp-break-words">
                  {msg.question}
                </p>
              </div>
              <div className="flex size-7 shrink-0 items-center justify-center rounded-full bg-primary/10">
                <User className="size-3.5 text-primary" />
              </div>
              <ChatMessageDeleteButton topicId={topicId} id={msg.id} />
            </div>
          </div>

          <div className="flex justify-start">
            <div className="flex max-w-[80%] gap-2">
              <div className="flex size-7 shrink-0 items-center justify-center rounded-full bg-muted">
                <Bot className="size-3.5 text-muted-foreground" />
              </div>
              <div className="min-w-0 rounded-2xl rounded-tl-md border border-border bg-card px-4 py-2.5 text-sm text-card-foreground">
                <p className="whitespace-pre-wrap warp-break-words">
                  {msg.answer}
                </p>
                <p className="mt-1.5 text-[10px] text-muted-foreground">
                  {formatDate(msg.createdAt)}
                </p>
              </div>
            </div>
          </div>
        </div>
      ))}
      <ChatAutoScroll messageCount={messages.length} />
    </div>
  );
}
