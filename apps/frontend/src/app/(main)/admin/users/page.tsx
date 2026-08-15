import { Suspense } from "react";

import {
  ProfilesTable,
  ProfilesTableSkeleton,
} from "@/features/profiles/components/profiles-table";
import { Search } from "@/shared/components/ui/search";

const tabs = ["Todos", "Estudiantes", "Docentes"];

export default async function AdminUsersPage({
  searchParams,
}: {
  searchParams: Promise<{ q?: string; page?: string }>;
}) {
  const { q, page } = await searchParams;
  const currentPage = Number(page) || 0;

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
          {tabs.map((tab) => (
            <span
              key={tab}
              className="inline-block cursor-default rounded-full border border-border px-3 py-1 text-xs font-medium text-muted-foreground transition-colors first:bg-primary first:text-primary-foreground first:border-primary"
            >
              {tab}
            </span>
          ))}
        </div>
        <Search holder="Buscar por nombre, apellido o correo..." />
      </div>

      <Suspense
        key={q ?? "" + currentPage}
        fallback={<ProfilesTableSkeleton />}
      >
        <ProfilesTable q={q} page={currentPage} />
      </Suspense>
    </div>
  );
}
