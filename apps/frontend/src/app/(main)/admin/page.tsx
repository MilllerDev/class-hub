import { getDashboard } from "@/features/dashboard/action";
import { RoleBadge } from "@/shared/components/ui/role-badge";
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

function StatusBadge({ status }: { status: boolean }) {
  const color = status
    ? "bg-green-100 text-green-800 dark:bg-green-900/30 dark:text-green-400"
    : "bg-muted text-muted-foreground";

  return (
    <span
      className={`inline-block rounded-full px-2 py-0.5 text-xs font-medium ${color}`}
    >
      {status ? "Activo" : "Inactivo"}
    </span>
  );
}

function formatDate(value: string) {
  const date = new Date(value);
  if (Number.isNaN(date.getTime())) {
    return "—";
  }
  return new Intl.DateTimeFormat("es", {
    day: "numeric",
    month: "short",
    year: "numeric",
  }).format(date);
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
    <div className="mx-auto w-full max-w-6xl space-y-8 px-4 py-8 lg:px-6">
      <section>
        <h1 className="text-2xl font-bold tracking-tight text-foreground">
          Panel de administración
        </h1>
        <p className="text-sm text-muted-foreground">
          Resumen general de la plataforma.
        </p>
      </section>

      <section className="grid grid-cols-2 gap-4 md:grid-cols-3 xl:grid-cols-5">
        {stats.map((stat) => (
          <StatCard key={stat.label} {...stat} />
        ))}
      </section>

      <div className="grid items-start gap-6 xl:grid-cols-2">
        <section className="space-y-4">
          <h2 className="text-lg font-semibold text-foreground">
            Últimos usuarios registrados
          </h2>
          <div className="overflow-hidden rounded-xl border border-border">
            <table className="w-full text-sm">
              <thead>
                <tr className="bg-muted/50">
                  <th className="px-4 py-3 text-left font-medium text-muted-foreground">
                    Nombres y Apellidos
                  </th>
                  <th className="px-4 py-3 text-left font-medium text-muted-foreground">
                    Rol
                  </th>
                  <th className="px-4 py-3 text-left font-medium text-muted-foreground">
                    Estado
                  </th>
                  <th className="px-4 py-3 text-left font-medium text-muted-foreground">
                    Fecha de registro
                  </th>
                </tr>
              </thead>
              <tbody className="divide-y divide-border">
                {latestProfiles.map(({ id, name, lastName, user }) => (
                  <tr
                    key={id}
                    className="bg-card transition-colors hover:bg-muted/30"
                  >
                    <td className="px-4 py-3 font-medium text-card-foreground line-clamp-1 truncate">
                      {name} {lastName}
                    </td>
                    <td className="px-4 py-3">
                      <RoleBadge role={user.role} />
                    </td>
                    <td className="px-4 py-3">
                      <StatusBadge status={user.enabled} />
                    </td>
                    <td className="whitespace-nowrap px-4 py-3 text-muted-foreground">
                      {formatDate(user.createdAt)}
                    </td>
                  </tr>
                ))}
              </tbody>
            </table>
          </div>
        </section>

        <section className="space-y-4">
          <h2 className="text-lg font-semibold text-foreground">
            Últimos cursos agregados
          </h2>
          <div className="overflow-hidden rounded-xl border border-border">
            <table className="w-full text-sm">
              <thead>
                <tr className="bg-muted/50">
                  <th className="px-4 py-3 text-left font-medium text-muted-foreground">
                    Curso
                  </th>
                  <th className="px-4 py-3 text-left font-medium text-muted-foreground">
                    Docente
                  </th>
                  <th className="px-4 py-3 text-left font-medium text-muted-foreground">
                    Estado
                  </th>
                  <th className="px-4 py-3 text-left font-medium text-muted-foreground">
                    Creado
                  </th>
                </tr>
              </thead>
              <tbody className="divide-y divide-border">
                {latestCourses.map((course) => (
                  <tr
                    key={course.id}
                    className="bg-card transition-colors hover:bg-muted/30"
                  >
                    <td className="px-4 py-3 font-medium text-card-foreground">
                      {course.name}
                    </td>
                    <td className="px-4 py-3 text-muted-foreground">
                      {course.profile
                        ? `${course.profile.name} ${course.profile.lastName}`.trim()
                        : "Sin docente"}
                    </td>
                    <td className="px-4 py-3">
                      <StatusBadge status={course.enabled} />
                    </td>
                    <td className="whitespace-nowrap px-4 py-3 text-muted-foreground">
                      {formatDate(course.createdAt)}
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
