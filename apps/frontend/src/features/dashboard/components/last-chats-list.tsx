import { formatDate } from "@/lib/utils";
import { Skeleton } from "@/shared/components/ui/skeleton";
import { getLastChats } from "../action";

export async function LastChatsList() {
  const chats = await getLastChats();

  if (chats.length === 0) {
    return (
      <p className="text-sm text-muted-foreground">
        Aún no tienes conversaciones.
      </p>
    );
  }

  return (
    <div className="divide-y divide-border overflow-hidden rounded-xl border border-border bg-card">
      {chats.map((chat) => (
        <div key={chat.id} className="space-y-0.5 p-4">
          <p className="text-xs text-muted-foreground">
            {formatDate(chat.createdAt)}
          </p>
          <p className="truncate text-sm font-medium text-card-foreground">
            {chat.question}
          </p>
          <p className="truncate text-sm text-muted-foreground">
            {chat.answer}
          </p>
        </div>
      ))}
    </div>
  );
}

export function LastChatsListSkeleton() {
  return (
    <div className="divide-y divide-border overflow-hidden rounded-xl border border-border bg-card">
      {Array.from({ length: 4 }).map((_, i) => (
        <div key={i} className="space-y-2 p-4">
          <Skeleton className="h-3 w-24" />
          <Skeleton className="h-4 w-3/4" />
          <Skeleton className="h-4 w-1/2" />
        </div>
      ))}
    </div>
  );
}