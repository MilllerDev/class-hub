import { getCoursesForTeacher } from "@/features/courses/action";
import { BookOpen, ClipboardCheck, Users } from "lucide-react";
import Link from "next/link";

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

export default async function TeacherDashboard() {
  const { courses } = await getCoursesForTeacher();

  const stats = [
    { label: "Cursos a cargo", value: courses.length, icon: BookOpen },
    { label: "Estudiantes totales", value: "—", icon: Users },
    { label: "Tareas por calificar", value: "—", icon: ClipboardCheck },
  ];

  return (
    <div className="mx-auto w-full max-w-5xl space-y-8 px-4 py-8">
      <section>
        <h1 className="text-2xl font-bold tracking-tight text-foreground">
          Bienvenido de nuevo
        </h1>
        <p className="text-sm text-muted-foreground">
          Bienvenido de vuelta. Este es el resumen de tus cursos.
        </p>
      </section>

      <section className="grid grid-cols-1 gap-3 sm:grid-cols-3">
        {stats.map((stat) => (
          <StatCard key={stat.label} {...stat} />
        ))}
      </section>

      <section>
        <Link
          href="/teacher/courses"
          className="inline-flex items-center gap-2 rounded-xl border border-border bg-card px-4 py-3 text-sm font-medium text-card-foreground transition-colors hover:bg-accent"
        >
          <BookOpen className="size-4" />
          Ver todos mis cursos
        </Link>
      </section>
    </div>
  );
}
