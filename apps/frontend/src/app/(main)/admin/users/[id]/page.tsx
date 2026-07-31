import { ArrowLeft, BookOpen, Calendar, CalendarCheck, Clock, ClipboardCheck } from "lucide-react";
import Link from "next/link";

const usersData: Record<
  string,
  {
    name: string;
    email: string;
    role: string;
    status: string;
    joinedDate: string;
    lastActive: string;
    coursesAssigned: number;
    tasksCompleted?: number;
  }
> = {
  "u1": {
    name: "Lucía Fernández",
    email: "lucia.f@school.edu",
    role: "Estudiante",
    status: "Activo",
    joinedDate: "28 jul 2026",
    lastActive: "Hoy",
    coursesAssigned: 5,
    tasksCompleted: 12,
  },
  "u2": {
    name: "Pedro Sánchez",
    email: "pedro.s@school.edu",
    role: "Docente",
    status: "Activo",
    joinedDate: "27 jul 2026",
    lastActive: "Ayer",
    coursesAssigned: 3,
  },
  default: {
    name: "Usuario",
    email: "usuario@school.edu",
    role: "Estudiante",
    status: "Activo",
    joinedDate: "—",
    lastActive: "—",
    coursesAssigned: 0,
    tasksCompleted: 0,
  },
};

function StatusBadge({ status }: { status: string }) {
  const color = status === "Activo"
    ? "bg-green-100 text-green-800 dark:bg-green-900/30 dark:text-green-400"
    : "bg-muted text-muted-foreground";

  return (
    <span className={`inline-block rounded-full px-2 py-0.5 text-xs font-medium ${color}`}>
      {status}
    </span>
  );
}

function RoleBadge({ role }: { role: string }) {
  const color = role === "Docente"
    ? "bg-violet-100 text-violet-800 dark:bg-violet-900/30 dark:text-violet-400"
    : role === "Administrador"
      ? "bg-amber-100 text-amber-800 dark:bg-amber-900/30 dark:text-amber-400"
      : "bg-blue-100 text-blue-800 dark:bg-blue-900/30 dark:text-blue-400";

  return (
    <span className={`inline-block rounded-full px-2 py-0.5 text-xs font-medium ${color}`}>
      {role}
    </span>
  );
}

export default async function AdminUserDetailPage({
  params,
}: {
  params: Promise<{ id: string }>;
}) {
  const { id } = await params;
  const user = usersData[id] ?? usersData.default;
  const isStudent = user.role === "Estudiante";

  return (
    <div className="mx-auto w-full max-w-3xl space-y-8 px-4 py-8">
      <section className="space-y-1">
        <Link
          href="/admin/users"
          className="inline-flex items-center gap-1.5 text-sm text-muted-foreground hover:text-foreground transition-colors"
        >
          <ArrowLeft className="size-4" />
          Volver a usuarios
        </Link>

        <div className="flex items-center gap-2 mt-2">
          <h1 className="text-2xl font-bold tracking-tight text-foreground">
            {user.name}
          </h1>
          <RoleBadge role={user.role} />
          <StatusBadge status={user.status} />
        </div>

        <p className="text-sm text-muted-foreground">{user.email}</p>
        <p className="text-xs text-muted-foreground font-mono">ID: {id}</p>
      </section>

      <section className="grid grid-cols-2 gap-3 md:grid-cols-4">
        <div className="rounded-xl border border-border bg-card p-4 text-center">
          <Calendar className="mx-auto size-5 text-muted-foreground" />
          <p className="mt-1.5 text-lg font-bold text-card-foreground">{user.joinedDate}</p>
          <p className="text-xs text-muted-foreground">Fecha ingreso</p>
        </div>
        <div className="rounded-xl border border-border bg-card p-4 text-center">
          <Clock className="mx-auto size-5 text-muted-foreground" />
          <p className="mt-1.5 text-lg font-bold text-card-foreground">{user.lastActive}</p>
          <p className="text-xs text-muted-foreground">Última actividad</p>
        </div>
        <div className="rounded-xl border border-border bg-card p-4 text-center">
          <BookOpen className="mx-auto size-5 text-muted-foreground" />
          <p className="mt-1.5 text-lg font-bold text-card-foreground">{user.coursesAssigned}</p>
          <p className="text-xs text-muted-foreground">
            {user.role === "Docente" ? "Cursos a cargo" : "Cursos inscritos"}
          </p>
        </div>
        {isStudent && (
          <div className="rounded-xl border border-border bg-card p-4 text-center">
            <ClipboardCheck className="mx-auto size-5 text-muted-foreground" />
            <p className="mt-1.5 text-lg font-bold text-card-foreground">{user.tasksCompleted}</p>
            <p className="text-xs text-muted-foreground">Tareas entregadas</p>
          </div>
        )}
        {!isStudent && (
          <div className="rounded-xl border border-border bg-card p-4 text-center">
            <CalendarCheck className="mx-auto size-5 text-muted-foreground" />
            <p className="mt-1.5 text-lg font-bold text-card-foreground">—</p>
            <p className="text-xs text-muted-foreground">Tareas entregadas</p>
          </div>
        )}
      </section>
    </div>
  );
}
