import { Skeleton } from "@/shared/components/ui/skeleton";

export function ChatHistorySkeleton() {
  return (
    <div className="space-y-4" aria-hidden>
      <MessageSkeleton side="right" />
      <MessageSkeleton side="left" />
      <MessageSkeleton side="right" />
      <MessageSkeleton side="left" />
    </div>
  );
}

function MessageSkeleton({ side }: { side: "left" | "right" }) {
  const isRight = side === "right";

  return (
    <div className={isRight ? "flex justify-end" : "flex justify-start"}>
      <div className="flex max-w-[80%] gap-2">
        {isRight ? (
          <>
            <div className="min-w-0 rounded-2xl rounded-tr-md bg-primary px-4 py-2.5">
              <Skeleton className="h-3.5 w-36 bg-primary-foreground/30" />
              <Skeleton className="mt-2 h-3.5 w-24 bg-primary-foreground/30" />
            </div>
            <Skeleton className="size-7 shrink-0 rounded-full bg-primary/10" />
          </>
        ) : (
          <>
            <Skeleton className="size-7 shrink-0 rounded-full bg-muted" />
            <div className="min-w-0 rounded-2xl rounded-tl-md border border-border bg-card px-4 py-2.5">
              <Skeleton className="h-3.5 w-44 bg-muted" />
              <Skeleton className="mt-1.5 h-2 w-14 bg-muted" />
            </div>
          </>
        )}
      </div>
    </div>
  );
}