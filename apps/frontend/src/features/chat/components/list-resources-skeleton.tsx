import { Skeleton } from "@/shared/components/ui/skeleton";

export function ListResourcesSkeleton() {
  return (
    <aside
      className="hidden w-64 shrink-0 flex-col border-r border-border bg-muted/30 md:flex"
      aria-hidden
    >
      <div className="flex items-center justify-between border-b border-border px-4 py-2.5">
        <Skeleton className="h-3 w-14" />
        <Skeleton className="h-3 w-10" />
      </div>

      <div className="flex-1 space-y-1 overflow-y-auto px-3 py-2">
        {Array.from({ length: 5 }).map((_, i) => (
          <div
            key={i}
            className="flex items-center gap-2 rounded-md px-2 py-1.5"
          >
            <Skeleton className="size-3 shrink-0" />
            <Skeleton className="size-3.5 shrink-0 rounded-sm" />
            <Skeleton className="h-3 w-full" />
          </div>
        ))}
      </div>
    </aside>
  );
}