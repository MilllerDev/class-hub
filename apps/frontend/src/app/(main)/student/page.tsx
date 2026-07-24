import {
  Bell,
  BookOpen,
  ClipboardCheck,
  Clock,
  GraduationCap,
} from "lucide-react";
import Link from "next/link";

const student = {
  name: "Carlos Méndez",
  email: "carlos.mendez@school.edu",
};

const stats = [
  { label: "Cursos inscritos", value: 6, icon: BookOpen },
  { label: "Tareas pendientes", value: 4, icon: ClipboardCheck },
  { label: "Promedio general", value: "8.7", icon: GraduationCap },
  { label: "Notificaciones", value: 2, icon: Bell },
];

const upcomingTasks = [
  {
    title: "Ensayo sobre la Revolución Francesa",
    subject: "Historia",
    dueDate: "25 jul 2026",
    status: "Pendiente",
  },
  {
    title: "Ejercicios de derivadas",
    subject: "Matemáticas",
    dueDate: "26 jul 2026",
    status: "Pendiente",
  },
  {
    title: "Presentación: Ecosistemas",
    subject: "Biología",
    dueDate: "28 jul 2026",
    status: "En progreso",
  },
  {
    title: "Lectura: Cien años de soledad (cap. 1-5)",
    subject: "Literatura",
    dueDate: "30 jul 2026",
    status: "Pendiente",
  },
];

const todaySchedule = [
  { time: "08:00 - 09:30", name: "Matemáticas", room: "Aula 204" },
  { time: "10:00 - 11:30", name: "Historia", room: "Aula 108" },
  { time: "12:00 - 13:30", name: "Biología", room: "Lab. 3" },
  { time: "15:00 - 16:30", name: "Literatura", room: "Aula 301" },
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
  const color =
    status === "En progreso"
      ? "bg-yellow-100 text-yellow-800 dark:bg-yellow-900/30 dark:text-yellow-400"
      : "bg-muted text-muted-foreground";

  return (
    <span className={`inline-block rounded-full px-2 py-0.5 text-xs font-medium ${color}`}>
      {status}
    </span>
  );
}

export default function StudentPage() {
  return (
    <div className="mx-auto w-full max-w-5xl space-y-8 px-4 py-8">
      <section>
        <h1 className="text-2xl font-bold tracking-tight text-foreground">
          Hola, {student.name}
        </h1>
        <p className="text-sm text-muted-foreground">
          Bienvenido de vuelta. Este es tu resumen del día.
        </p>
      </section>

      <section className="grid grid-cols-2 gap-3 lg:grid-cols-4">
        {stats.map((stat) => (
          <StatCard key={stat.label} {...stat} />
        ))}
      </section>

      <section>
        <Link
          href="/student/courses"
          className="inline-flex items-center gap-2 rounded-xl border border-border bg-card px-4 py-3 text-sm font-medium text-card-foreground transition-colors hover:bg-accent"
        >
          <BookOpen className="size-4" />
          Ver todos mis cursos
        </Link>
      </section>

      <div className="grid gap-6 lg:grid-cols-5">
        <section className="space-y-3 lg:col-span-3">
          <h2 className="text-lg font-semibold text-foreground">
            Próximas tareas
          </h2>
          <div className="space-y-2">
            {upcomingTasks.map((task) => (
              <div
                key={task.title}
                className="flex items-center justify-between rounded-xl border border-border bg-card p-4"
              >
                <div className="min-w-0 space-y-0.5">
                  <p className="truncate text-sm font-medium text-card-foreground">
                    {task.title}
                  </p>
                  <p className="text-xs text-muted-foreground">
                    {task.subject} · {task.dueDate}
                  </p>
                </div>
                <StatusBadge status={task.status} />
              </div>
            ))}
          </div>
        </section>

        <section className="space-y-3 lg:col-span-2">
          <h2 className="text-lg font-semibold text-foreground">
            Horario de hoy
          </h2>
          <div className="space-y-2">
            {todaySchedule.map((cls) => (
              <div
                key={cls.time}
                className="rounded-xl border border-border bg-card p-4"
              >
                <div className="flex items-center gap-2">
                  <Clock className="size-3.5 text-muted-foreground" />
                  <p className="text-xs font-medium text-muted-foreground">
                    {cls.time}
                  </p>
                </div>
                <p className="mt-1 text-sm font-medium text-card-foreground">
                  {cls.name}
                </p>
                <p className="text-xs text-muted-foreground">{cls.room}</p>
              </div>
            ))}
          </div>
        </section>
      </div>
    </div>
  );
}
