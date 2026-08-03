import { getProfiles } from "@/features/profiles/action";
import { ProfileTableRow } from "./profile-table-row";

const mockJoinedDates = [
  "28 jul 2026",
  "27 jul 2026",
  "26 jul 2026",
  "25 jul 2026",
  "24 jul 2026",
  "22 jul 2026",
];

export async function ProfilesTable() {
  const result = await getProfiles();
  const profiles = result.success ? result.data : [];

  if (profiles.length === 0) {
    return (
      <div className="rounded-xl border border-border bg-card p-8 text-center text-sm text-muted-foreground">
        No hay datos que mostrar
      </div>
    );
  }

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
          </tr>
        </thead>
        <tbody className="divide-y divide-border">
          {profiles.map((profile, i) => (
            <ProfileTableRow
              key={profile.id}
              profile={
                profile.joinedDate
                  ? profile
                  : {
                      ...profile,
                      joinedDate: mockJoinedDates[i % mockJoinedDates.length],
                    }
              }
            />
          ))}
        </tbody>
      </table>
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
          </tr>
        </thead>
        <tbody className="divide-y divide-border">
          {Array.from({ length: 5 }).map((_, i) => (
            <tr key={i} className="bg-card">
              <td className="px-4 py-3">
                <div className="flex items-center gap-3">
                  <div className="size-8 animate-pulse rounded-full bg-muted" />
                  <div className="h-4 w-2/3 animate-pulse rounded bg-muted" />
                </div>
              </td>
              <td className="hidden px-4 py-3 sm:table-cell">
                <div className="h-4 w-1/2 animate-pulse rounded bg-muted" />
              </td>
              <td className="px-4 py-3">
                <div className="h-5 w-20 animate-pulse rounded-full bg-muted" />
              </td>
              <td className="px-4 py-3">
                <div className="h-5 w-16 animate-pulse rounded-full bg-muted" />
              </td>
              <td className="hidden px-4 py-3 md:table-cell">
                <div className="h-4 w-24 animate-pulse rounded bg-muted" />
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}
