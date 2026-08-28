import { Skeleton } from "@/shared/components/ui/skeleton";
import { ChatHistorySkeleton } from "./chat-history-skeleton";
import { ListResourcesSkeleton } from "./list-resources-skeleton";

export function ChatLayoutSkeleton() {
  return (
    <>
      <ListResourcesSkeleton />

      <div className="flex min-h-0 flex-1 flex-col">
        <div className="min-h-0 flex-1 overflow-y-auto px-4 py-4">
          <ChatHistorySkeleton />
        </div>

        <div
          className="shrink-0 border-t border-border bg-background px-4 py-3"
          aria-hidden
        >
          <div className="mx-auto flex max-w-2xl items-end gap-2">
            <Skeleton className="h-9 flex-1 rounded-md" />
            <Skeleton className="size-9 rounded-md" />
          </div>
        </div>
      </div>
    </>
  );
}