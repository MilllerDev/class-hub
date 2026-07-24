import { ArrowLeft, BookOpen, Calendar, CheckCircle, ClipboardList, User } from "lucide-react";
import Link from "next/link";

const coursesData: Record<
  string,
  {
    name: string;
    teacher: string;
    average: number;
    submittedTasks: number;
    totalTasks: number;
    attendance: number;
    tasks: {
      title: string;
      dueDate: string;
      status: "Calificada" | "Entregada" | "Pendiente";
      grade?: string;
    }[];
  }
> = {
  "math-101": {
    name: "Matemáticas",
    teacher: "Prof. Laura Gómez",
    average: 9.1,
    submittedTasks: 8,
    totalTasks: 11,
    attendance: 95,
    tasks: [
      { title: "Ecuaciones cuadráticas", dueDate: "20 jul 2026", status: "Calificada", grade: "9.5" },
      { title: "Sistemas de ecuaciones", dueDate: "22 jul 2026", status: "Entregada" },
      { title: "Ejercicios de derivadas", dueDate: "26 jul 2026", status: "Pendiente" },
      { title: "Parcial 1", dueDate: "30 jul 2026", status: "Pendiente" },
    ],
  },
  default: {
    name: "Curso",
    teacher: "Profesor",
    average: 8.5,
    submittedTasks: 5,
    totalTasks: 8,
    attendance: 90,
    tasks: [
      { title: "Tarea 1", dueDate: "20 jul 2026", status: "Calificada", grade: "8.0" },
      { title: "Tarea 2", dueDate: "25 jul 2026", status: "Entregada" },
      { title: "Tarea 3", dueDate: "28 jul 2026", status: "Pendiente" },
    ],
  },
};

function StatusBadge({ status }: { status: string }) {
  const color =
    status === "Calificada"
      ? "bg-green-100 text-green-800 dark:bg-green-900/30 dark:text-green-400"
      : status === "Entregada"
        ? "bg-blue-100 text-blue-800 dark:bg-blue-900/30 dark:text-blue-400"
        : "bg-muted text-muted-foreground";

  return (
    <span className={`inline-block rounded-full px-2 py-0.5 text-xs font-medium ${color}`}>
      {status}
    </span>
  );
}

export default async function CourseDetailPage({
  params,
}: {
  params: Promise<{ id: string }>;
}) {
  const { id } = await params;
  const course = coursesData[id] ?? coursesData.default;

  return (
    <div className="mx-auto w-full max-w-3xl space-y-8 px-4 py-8">
      <section className="space-y-1">
        <Link
          href="/student/courses"
          className="inline-flex items-center gap-1.5 text-sm text-muted-foreground hover:text-foreground transition-colors"
        >
          <ArrowLeft className="size-4" />
          Volver a cursos
        </Link>
        <h1 className="text-2xl font-bold tracking-tight text-foreground">
          {course.name}
        </h1>
        <div className="flex items-center gap-1.5 text-sm text-muted-foreground">
          <User className="size-4" />
          {course.teacher}
        </div>
        <p className="text-xs text-muted-foreground font-mono">ID: {id}</p>
      </section>

      <section className="grid grid-cols-3 gap-3">
        <div className="rounded-xl border border-border bg-card p-4 text-center">
          <p className="text-2xl font-bold text-card-foreground">{course.average}</p>
          <p className="text-xs text-muted-foreground">Promedio</p>
        </div>
        <div className="rounded-xl border border-border bg-card p-4 text-center">
          <p className="text-2xl font-bold text-card-foreground">
            {course.submittedTasks}/{course.totalTasks}
          </p>
          <p className="text-xs text-muted-foreground">Tareas entregadas</p>
        </div>
        <div className="rounded-xl border border-border bg-card p-4 text-center">
          <p className="text-2xl font-bold text-card-foreground">{course.attendance}%</p>
          <p className="text-xs text-muted-foreground">Asistencia</p>
        </div>
      </section>

      <section className="space-y-3">
        <h2 className="text-lg font-semibold text-foreground">Tareas del curso</h2>
        <div className="space-y-2">
          {course.tasks.map((task) => (
            <div
              key={task.title}
              className="flex items-center justify-between rounded-xl border border-border bg-card p-4"
            >
              <div className="space-y-0.5">
                <p className="text-sm font-medium text-card-foreground">{task.title}</p>
                <div className="flex items-center gap-1.5 text-xs text-muted-foreground">
                  <Calendar className="size-3" />
                  {task.dueDate}
                </div>
              </div>
              <div className="flex items-center gap-2">
                {task.grade && (
                  <span className="text-sm font-bold text-card-foreground">{task.grade}</span>
                )}
                <StatusBadge status={task.status} />
              </div>
            </div>
          ))}
        </div>
      </section>
    </div>
  );
}
