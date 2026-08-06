import { getDashboard } from "@/features/dashboard/action";
import {
  BookOpen,
  ClipboardCheck,
  GraduationCap,
  UserPlus,
  Users,
} from "lucide-react";

function StatCard({
  label,
  value,
  icon: Icon,
}: {
  label: string;
  value: string | number;
  icon: React.ComponentType<{ className?: string }>;
}) {
  return (
    <div className="rounded-xl border border-border bg-card p-4">
      <div className="flex items-center justify-between">
        <p className="text-sm text-muted-foreground">{label}</p>
        <Icon className="size-4 text-muted-foreground" />
      </div>
      <p className="mt-2 text-2xl font-bold text-card-foreground">{value}</p>
    </div>
  );
}

function StatusBadge({
  status,
  activeLabel = "Activo",
  inactiveLabel = "Inactivo",
}: {
  status: boolean;
  activeLabel?: string;
  inactiveLabel?: string;
}) {
  const color = status
    ? "bg-green-100 text-green-800 dark:bg-green-900/30 dark:text-green-400"
    : "bg-muted text-muted-foreground";

  return (
    <span
      className={`inline-block rounded-full px-2 py-0.5 text-xs font-medium ${color}`}
    >
      {status ? activeLabel : inactiveLabel}
    </span>
  );
}

export default async function AdminDashboard() {
  const { courseCount, latestCourses, latestProfiles } = await getDashboard();

  const stats = [
    { label: "Total estudiantes", value: 342, icon: GraduationCap },
    { label: "Total docentes", value: 28, icon: Users },
    { label: "Cursos activos", value: courseCount, icon: BookOpen },
    { label: "Usuarios nuevos (mes)", value: 23, icon: UserPlus },
    { label: "Tareas por revisar", value: 67, icon: ClipboardCheck },
  ];
  return (
    <div className="mx-auto w-full max-w-5xl space-y-8 px-4 py-8">
      <section>
        <h1 className="text-2xl font-bold tracking-tight text-foreground">
          Panel de administración
        </h1>
        <p className="text-sm text-muted-foreground">
          Resumen general de la plataforma.
        </p>
      </section>

      <section className="grid grid-cols-2 gap-3 md:grid-cols-3 lg:grid-cols-5">
        {stats.map((stat) => (
          <StatCard key={stat.label} {...stat} />
        ))}
      </section>

      <div className="grid gap-6 lg:grid-cols-2">
        <section className="space-y-3">
          <h2 className="text-lg font-semibold text-foreground">
            Últimos usuarios registrados
          </h2>
          <div className="overflow-hidden rounded-xl border border-border">
            <table className="w-full text-sm">
              <thead>
                <tr className="bg-muted/50">
                  <th className="px-4 py-2.5 text-left font-medium text-muted-foreground">
                    Nombre
                  </th>
                  <th className="px-4 py-2.5 text-left font-medium text-muted-foreground hidden sm:table-cell">
                    Email
                  </th>
                  <th className="px-4 py-2.5 text-left font-medium text-muted-foreground">
                    Rol
                  </th>
                  <th className="px-4 py-2.5 text-left font-medium text-muted-foreground">
                    Estado
                  </th>
                </tr>
              </thead>
              <tbody className="divide-y divide-border">
                {latestProfiles.map(({ id, name, user }) => (
                  <tr key={id} className="bg-card">
                    <td className="px-4 py-2.5 font-medium text-card-foreground">
                      {name}
                    </td>
                    <td className="px-4 py-2.5 text-muted-foreground hidden sm:table-cell">
                      {user.email}
                    </td>
                    <td className="px-4 py-2.5 text-muted-foreground">
                      {user.role}
                    </td>
                    <td className="px-4 py-2.5">
                      <StatusBadge status={user.enabled} />
                    </td>
                  </tr>
                ))}
              </tbody>
            </table>
          </div>
        </section>

        <section className="space-y-3">
          <h2 className="text-lg font-semibold text-foreground">
            Últimos cursos creados
          </h2>
          <div className="overflow-hidden rounded-xl border border-border">
            <table className="w-full text-sm">
              <thead>
                <tr className="bg-muted/50">
                  <th className="px-4 py-2.5 text-left font-medium text-muted-foreground">
                    Curso
                  </th>
                  <th className="px-4 py-2.5 text-left font-medium text-muted-foreground hidden sm:table-cell">
                    Docente
                  </th>
                  <th className="px-4 py-2.5 text-left font-medium text-muted-foreground">
                    Estado
                  </th>
                </tr>
              </thead>
              <tbody className="divide-y divide-border">
                {latestCourses.map((course) => (
                  <tr key={course.id} className="bg-card">
                    <td className="px-4 py-2.5 font-medium text-card-foreground">
                      {course.name}
                    </td>
                    <td className="px-4 py-2.5 text-muted-foreground hidden sm:table-cell">
                      {course.user ? course.user.email : "Sin docente"}
                    </td>
                    <td className="px-4 py-2.5">
                      <StatusBadge
                        status={course.enabled}
                        activeLabel="Publicado"
                        inactiveLabel="Borrador"
                      />
                    </td>
                  </tr>
                ))}
              </tbody>
            </table>
          </div>
        </section>
      </div>
    </div>
  );
}
