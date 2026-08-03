import {
  BookOpen,
  GraduationCap,
  ClipboardCheck,
  Users,
  UserPlus,
} from "lucide-react";

const stats = [
  { label: "Total estudiantes", value: 342, icon: GraduationCap },
  { label: "Total docentes", value: 28, icon: Users },
  { label: "Cursos activos", value: 45, icon: BookOpen },
  { label: "Usuarios nuevos (mes)", value: 23, icon: UserPlus },
  { label: "Tareas por revisar", value: 67, icon: ClipboardCheck },
];

const recentUsers = [
  { name: "Lucía Fernández", email: "lucia.f@school.edu", role: "Estudiante", status: "Activo", date: "28 jul 2026" },
  { name: "Pedro Sánchez", email: "pedro.s@school.edu", role: "Docente", status: "Activo", date: "27 jul 2026" },
  { name: "María López", email: "maria.l@school.edu", role: "Estudiante", status: "Activo", date: "26 jul 2026" },
  { name: "Jorge Martínez", email: "jorge.m@school.edu", role: "Estudiante", status: "Inactivo", date: "25 jul 2026" },
  { name: "Ana Ramírez", email: "ana.r@school.edu", role: "Docente", status: "Activo", date: "24 jul 2026" },
];

const recentCourses = [
  { name: "Álgebra Lineal", teacher: "Prof. Laura Gómez", students: 28, status: "Publicado", date: "20 jul 2026" },
  { name: "Literatura del Siglo XX", teacher: "Prof. Carlos Ruiz", students: 22, status: "Publicado", date: "19 jul 2026" },
  { name: "Química Orgánica", teacher: "Prof. Ana Morales", students: 24, status: "Borrador", date: "18 jul 2026" },
  { name: "Inglés Avanzado", teacher: "Prof. Sarah Williams", students: 18, status: "Publicado", date: "17 jul 2026" },
];

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

function StatusBadge({ status }: { status: string }) {
  const isActive = status === "Activo" || status === "Publicado";
  const color = isActive
    ? "bg-green-100 text-green-800 dark:bg-green-900/30 dark:text-green-400"
    : "bg-muted text-muted-foreground";

  return (
    <span className={`inline-block rounded-full px-2 py-0.5 text-xs font-medium ${color}`}>
      {status}
    </span>
  );
}

export default function AdminDashboard() {
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
                  <th className="px-4 py-2.5 text-left font-medium text-muted-foreground">Nombre</th>
                  <th className="px-4 py-2.5 text-left font-medium text-muted-foreground hidden sm:table-cell">Email</th>
                  <th className="px-4 py-2.5 text-left font-medium text-muted-foreground">Rol</th>
                  <th className="px-4 py-2.5 text-left font-medium text-muted-foreground">Estado</th>
                </tr>
              </thead>
              <tbody className="divide-y divide-border">
                {recentUsers.map((user) => (
                  <tr key={user.email} className="bg-card">
                    <td className="px-4 py-2.5 font-medium text-card-foreground">{user.name}</td>
                    <td className="px-4 py-2.5 text-muted-foreground hidden sm:table-cell">{user.email}</td>
                    <td className="px-4 py-2.5 text-muted-foreground">{user.role}</td>
                    <td className="px-4 py-2.5"><StatusBadge status={user.status} /></td>
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
                  <th className="px-4 py-2.5 text-left font-medium text-muted-foreground">Curso</th>
                  <th className="px-4 py-2.5 text-left font-medium text-muted-foreground hidden sm:table-cell">Docente</th>
                  <th className="px-4 py-2.5 text-left font-medium text-muted-foreground">Est.</th>
                  <th className="px-4 py-2.5 text-left font-medium text-muted-foreground">Estado</th>
                </tr>
              </thead>
              <tbody className="divide-y divide-border">
                {recentCourses.map((course) => (
                  <tr key={course.name} className="bg-card">
                    <td className="px-4 py-2.5 font-medium text-card-foreground">{course.name}</td>
                    <td className="px-4 py-2.5 text-muted-foreground hidden sm:table-cell">{course.teacher}</td>
                    <td className="px-4 py-2.5 text-muted-foreground">{course.students}</td>
                    <td className="px-4 py-2.5"><StatusBadge status={course.status} /></td>
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
