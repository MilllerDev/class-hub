import { Pagination } from "@/shared/components/ui/pagination";
import { Skeleton } from "@/shared/components/ui/skeleton";
import { listProfiles } from "../action";
import { ProfileTableRow } from "./profile-table-row";

export async function ProfilesTable({ q, page }: { q?: string; page: number }) {
  const { data: profiles } = await listProfiles(page, q);
  const currentPage = profiles.page ?? 0;
  const totalPages = profiles.totalPages ?? 0;

  if (profiles.content.length === 0) {
    return (
      <div className="rounded-xl border border-border bg-card p-8 text-center text-sm text-muted-foreground">
        {q ? (
          <>
            No se encontraron resultados para{" "}
            <span className="font-medium text-card-foreground">«{q}»</span>
          </>
        ) : (
          "No hay datos que mostrar"
        )}
      </div>
    );
  }

  return (
    <div className="space-y-3">
      <div className="overflow-hidden rounded-xl border border-border">
        <table className="w-full text-sm">
          <thead>
            <tr className="bg-muted/50">
              <th className="px-4 py-3 text-left font-medium text-muted-foreground">
                Nombre
              </th>
              <th className="hidden px-4 py-3 text-left font-medium text-muted-foreground sm:table-cell">
                Email
              </th>
              <th className="px-4 py-3 text-left font-medium text-muted-foreground">
                Rol
              </th>
              <th className="px-4 py-3 text-left font-medium text-muted-foreground">
                Estado
              </th>
              <th className="hidden px-4 py-3 text-left font-medium text-muted-foreground md:table-cell">
                Registro
              </th>
              <th className="px-4 py-3 text-right font-medium text-muted-foreground">
                Acciones
              </th>
            </tr>
          </thead>
          <tbody className="divide-y divide-border">
            {profiles.content.map((profile) => (
              <ProfileTableRow key={profile.id} profile={profile} />
            ))}
          </tbody>
        </table>
      </div>

      {profiles && (
        <Pagination currentPage={currentPage} totalPages={totalPages} last />
      )}
    </div>
  );
}

export function ProfilesTableSkeleton() {
  return (
    <div className="overflow-hidden rounded-xl border border-border">
      <table className="w-full text-sm">
        <thead>
          <tr className="bg-muted/50">
            <th className="px-4 py-3 text-left font-medium text-muted-foreground">
              Nombre
            </th>
            <th className="hidden px-4 py-3 text-left font-medium text-muted-foreground sm:table-cell">
              Email
            </th>
            <th className="px-4 py-3 text-left font-medium text-muted-foreground">
              Rol
            </th>
            <th className="px-4 py-3 text-left font-medium text-muted-foreground">
              Estado
            </th>
            <th className="hidden px-4 py-3 text-left font-medium text-muted-foreground md:table-cell">
              Registro
            </th>
            <th className="px-4 py-3 text-right font-medium text-muted-foreground">
              Acciones
            </th>
          </tr>
        </thead>
        <tbody className="divide-y divide-border">
          {Array.from({ length: 5 }).map((_, i) => (
            <tr key={i} className="bg-card">
              <td className="px-4 py-3">
                <div className="flex items-center gap-3">
                  <Skeleton className="size-8 rounded-full" />
                  <Skeleton className="h-4 w-2/3" />
                </div>
              </td>
              <td className="hidden px-4 py-3 sm:table-cell">
                <Skeleton className="h-4 w-1/2" />
              </td>
              <td className="px-4 py-3">
                <Skeleton className="h-5 w-20 rounded-full" />
              </td>
              <td className="px-4 py-3">
                <Skeleton className="h-4 w-8 rounded-full" />
              </td>
              <td className="hidden px-4 py-3 md:table-cell">
                <Skeleton className="h-4 w-24" />
              </td>
              <td className="px-4 py-3">
                <div className="flex items-center justify-end">
                  <Skeleton className="size-7 rounded-md" />
                </div>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}
