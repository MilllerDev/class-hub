import { ArrowLeft, BookOpen, CheckCircle, User } from "lucide-react";
import Link from "next/link";

const courses = [
  {
    id: "math-101",
    name: "Matemáticas",
    teacher: "Prof. Laura Gómez",
    pendingTasks: 3,
    average: 9.1,
    status: "Activo" as const,
  },
  {
    id: "hist-201",
    name: "Historia",
    teacher: "Prof. Roberto Díaz",
    pendingTasks: 1,
    average: 8.5,
    status: "Activo" as const,
  },
  {
    id: "bio-101",
    name: "Biología",
    teacher: "Prof. Ana Morales",
    pendingTasks: 2,
    average: 8.8,
    status: "Activo" as const,
  },
  {
    id: "lit-301",
    name: "Literatura",
    teacher: "Prof. Carlos Ruiz",
    pendingTasks: 0,
    average: 9.4,
    status: "Finalizado" as const,
  },
  {
    id: "eng-201",
    name: "Inglés",
    teacher: "Prof. Sarah Williams",
    pendingTasks: 1,
    average: 8.2,
    status: "Activo" as const,
  },
  {
    id: "phys-101",
    name: "Física",
    teacher: "Prof. Miguel Torres",
    pendingTasks: 2,
    average: 7.9,
    status: "Activo" as const,
  },
];

function StatusBadge({ status }: { status: "Activo" | "Finalizado" }) {
  const color =
    status === "Activo"
      ? "bg-green-100 text-green-800 dark:bg-green-900/30 dark:text-green-400"
      : "bg-muted text-muted-foreground";

  return (
    <span className={`inline-block rounded-full px-2 py-0.5 text-xs font-medium ${color}`}>
      {status}
    </span>
  );
}

export default function CoursesPage() {
  return (
    <div className="mx-auto w-full max-w-5xl space-y-8 px-4 py-8">
      <section className="space-y-1">
        <Link
          href="/student"
          className="inline-flex items-center gap-1.5 text-sm text-muted-foreground hover:text-foreground transition-colors"
        >
          <ArrowLeft className="size-4" />
          Volver al dashboard
        </Link>
        <h1 className="text-2xl font-bold tracking-tight text-foreground">
          Mis cursos
        </h1>
        <p className="text-sm text-muted-foreground">
          Todos los cursos en los que estás inscrito.
        </p>
      </section>

      <section className="grid gap-4 sm:grid-cols-2 lg:grid-cols-3">
        {courses.map((course) => (
          <Link
            key={course.id}
            href={`/student/courses/${course.id}`}
            className="group rounded-xl border border-border bg-card p-5 transition-colors hover:bg-accent"
          >
            <div className="flex items-start justify-between">
              <div className="flex size-10 items-center justify-center rounded-lg bg-primary/10 text-primary">
                <BookOpen className="size-5" />
              </div>
              <StatusBadge status={course.status} />
            </div>

            <h3 className="mt-4 text-base font-semibold text-card-foreground group-hover:text-accent-foreground">
              {course.name}
            </h3>

            <div className="mt-1 flex items-center gap-1.5 text-xs text-muted-foreground">
              <User className="size-3" />
              {course.teacher}
            </div>

            <div className="mt-4 flex items-center justify-between border-t border-border pt-3">
              <span className="text-xs text-muted-foreground">
                {course.pendingTasks} tareas pendientes
              </span>
              <div className="flex items-center gap-1 text-xs font-medium text-card-foreground">
                <CheckCircle className="size-3" />
                {course.average}
              </div>
            </div>
          </Link>
        ))}
      </section>
    </div>
  );
}
