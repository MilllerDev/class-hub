import { Suspense } from "react";

import {
  ProfilesTable,
  ProfilesTableSkeleton,
} from "@/features/profiles/components/profiles-table";
import { ProfileParams } from "@/features/profiles/types";
import { FilterTabs } from "@/shared/components/ui/filter-tabs";
import { Search } from "@/shared/components/ui/search";

const tabs = [
  { label: "Todos", value: "" },
  { label: "Estudiantes", value: "STUDENT" },
  { label: "Docentes", value: "TEACHER" },
  { label: "Administradores", value: "ADMIN" },
];

export default async function AdminUsersPage({
  searchParams,
}: {
  searchParams: Promise<ProfileParams>;
}) {
  const { q, page, role, size } = await searchParams;
  const currentPage = Math.floor(Math.max(1, Number(page) || 1));

  return (
    <div className="mx-auto w-full max-w-5xl space-y-6 px-4 py-8">
      <section>
        <h1 className="text-2xl font-bold tracking-tight text-foreground">
          Usuarios
        </h1>
        <p className="text-sm text-muted-foreground">
          Gestiona todos los usuarios de la plataforma.
        </p>
      </section>

      <div className="flex flex-col gap-4 sm:flex-row sm:items-center sm:justify-between">
        <div className="flex items-center gap-2">
          <FilterTabs items={tabs} param="role" />
        </div>
        <Search holder="Buscar por nombre, apellido o correo..." />
      </div>

      <Suspense
        key={`${q ?? ""}-${currentPage}-${role ?? ""}-${size}`}
        fallback={<ProfilesTableSkeleton />}
      >
        <ProfilesTable q={q} page={currentPage} role={role} size={size} />
      </Suspense>
    </div>
  );
}
