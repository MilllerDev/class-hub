import { Skeleton } from "@/shared/components/ui/skeleton";

function TableSkeleton({ rows = 5 }: { rows?: number }) {
  return (
    <div className="overflow-hidden rounded-xl border border-border">
      <table className="w-full text-sm">
        <thead>
          <tr className="bg-muted/50">
            <th className="px-4 py-3">
              <Skeleton className="h-4 w-32" />
            </th>
            <th className="px-4 py-3">
              <Skeleton className="h-4 w-16" />
            </th>
            <th className="px-4 py-3">
              <Skeleton className="h-4 w-16" />
            </th>
            <th className="px-4 py-3">
              <Skeleton className="h-4 w-20" />
            </th>
          </tr>
        </thead>
        <tbody className="divide-y divide-border">
          {Array.from({ length: rows }).map((_, index) => (
            <tr key={index} className="bg-card">
              <td className="px-4 py-3">
                <Skeleton className="h-4 w-3/4" />
              </td>
              <td className="px-4 py-3">
                <Skeleton className="h-5 w-20 rounded-full" />
              </td>
              <td className="px-4 py-3">
                <Skeleton className="h-5 w-16 rounded-full" />
              </td>
              <td className="px-4 py-3">
                <Skeleton className="h-4 w-24" />
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}

export default function Loading() {
  return (
    <div className="mx-auto w-full max-w-6xl space-y-8 px-4 py-8 lg:px-6">
      <section className="space-y-2">
        <Skeleton className="h-7 w-56" />
        <Skeleton className="h-4 w-72" />
      </section>

      <section className="grid grid-cols-2 gap-4 md:grid-cols-3 xl:grid-cols-5">
        {Array.from({ length: 5 }).map((_, index) => (
          <Skeleton key={index} className="h-24 rounded-xl" />
        ))}
      </section>

      <div className="grid items-start gap-6 xl:grid-cols-2">
        <section className="space-y-4">
          <Skeleton className="h-5 w-56" />
          <TableSkeleton />
        </section>

        <section className="space-y-4">
          <Skeleton className="h-5 w-56" />
          <TableSkeleton />
        </section>
      </div>
    </div>
  );
}
