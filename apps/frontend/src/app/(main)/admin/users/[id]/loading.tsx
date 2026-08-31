import { Skeleton } from "@/shared/components/ui/skeleton";

export default function AdminUserDetailLoading() {
  return (
    <div className="mx-auto w-full max-w-5xl space-y-8 px-4 py-8">
      <Skeleton className="h-4 w-28" />

      <section className="rounded-xl border border-border bg-card p-6">
        <div className="mt-2 flex flex-col items-center gap-4 sm:flex-row sm:items-center sm:gap-5">
          <Skeleton className="size-16 shrink-0 rounded-full" />
          <div className="flex flex-col items-center gap-2 sm:items-start">
            <Skeleton className="h-7 w-48" />
            <Skeleton className="h-4 w-64" />
            <div className="flex gap-2">
              <Skeleton className="h-5 w-24 rounded-full" />
              <Skeleton className="h-5 w-16 rounded-full" />
            </div>
          </div>
        </div>

        <div className="mt-6 grid gap-4 border-t border-border pt-6 sm:grid-cols-2 lg:grid-cols-3">
          {Array.from({ length: 5 }).map((_, index) => (
            <div key={index} className="flex items-center gap-3">
              <Skeleton className="size-9 shrink-0 rounded-lg" />
              <div className="space-y-1.5">
                <Skeleton className="h-3 w-20" />
                <Skeleton className="h-4 w-32" />
              </div>
            </div>
          ))}
        </div>
      </section>
    </div>
  );
}
