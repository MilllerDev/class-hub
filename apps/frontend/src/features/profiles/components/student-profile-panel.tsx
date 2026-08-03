import {
  BookOpen,
  ClipboardCheck,
  GraduationCap,
  NotebookPen,
} from "lucide-react";

const studentStats = [
  { label: "Cursos inscritos", value: 6, icon: BookOpen },
  { label: "Tareas pendientes", value: 4, icon: ClipboardCheck },
  { label: "Promedio general", value: "8.7", icon: GraduationCap },
  { label: "Trabajos entregados", value: 18, icon: NotebookPen },
];

const enrolledCourses = [
  { name: "Álgebra Lineal", progress: 65 },
  { name: "Literatura del Siglo XX", progress: 40 },
  { name: "Química Orgánica", progress: 80 },
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

export function StudentProfilePanel() {
  return (
    <section className="space-y-4">
      <h2 className="text-lg font-semibold text-foreground">
        Rendimiento académico
      </h2>

      <div className="grid grid-cols-2 gap-3 lg:grid-cols-4">
        {studentStats.map((stat) => (
          <StatCard key={stat.label} {...stat} />
        ))}
      </div>

      <div className="rounded-xl border border-border bg-card p-6">
        <h3 className="text-sm font-semibold text-foreground">Mis cursos</h3>
        <ul className="mt-3 space-y-4">
          {enrolledCourses.map((course) => (
            <li key={course.name}>
              <div className="flex items-center justify-between text-sm">
                <span className="text-card-foreground">{course.name}</span>
                <span className="text-xs text-muted-foreground">
                  {course.progress}%
                </span>
              </div>
              <div className="mt-1.5 h-2 overflow-hidden rounded-full bg-muted">
                <div
                  className="h-full rounded-full bg-primary"
                  style={{ width: `${course.progress}%` }}
                />
              </div>
            </li>
          ))}
        </ul>
      </div>
    </section>
  );
}
