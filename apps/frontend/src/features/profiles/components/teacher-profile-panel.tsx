import { BookOpen, CalendarDays, Users } from "lucide-react";

const teacherStats = [
  { label: "Cursos a cargo", value: 4, icon: BookOpen },
  { label: "Estudiantes", value: 92, icon: Users },
  { label: "Clases esta semana", value: 8, icon: CalendarDays },
];

const courses = [
  { name: "Álgebra Lineal", students: 28 },
  { name: "Cálculo Diferencial", students: 24 },
  { name: "Ecuaciones Diferenciales", students: 22 },
  { name: "Matemáticas Discretas", students: 18 },
];

const upcomingClasses = [
  { course: "Álgebra Lineal", time: "08:00 - 09:30", room: "Aula 204" },
  { course: "Cálculo Diferencial", time: "10:00 - 11:30", room: "Aula 108" },
  { course: "Matemáticas Discretas", time: "15:00 - 16:30", room: "Lab. 3" },
];

function StatCard({
  label,
  value,
  icon: Icon,
}: {
  label: string;
  value: number;
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

export function TeacherProfilePanel() {
  return (
    <section className="space-y-4">
      <h2 className="text-lg font-semibold text-foreground">Mi docencia</h2>

      <div className="grid grid-cols-2 gap-3 lg:grid-cols-3">
        {teacherStats.map((stat) => (
          <StatCard key={stat.label} {...stat} />
        ))}
      </div>

      <div className="grid gap-6 lg:grid-cols-2">
        <div className="rounded-xl border border-border bg-card p-6">
          <h3 className="text-sm font-semibold text-foreground">
            Cursos a cargo
          </h3>
          <ul className="mt-3 divide-y divide-border">
            {courses.map((course) => (
              <li
                key={course.name}
                className="flex items-center justify-between gap-4 py-2.5 text-sm"
              >
                <span className="text-card-foreground">{course.name}</span>
                <span className="shrink-0 text-xs text-muted-foreground">
                  {course.students} estudiantes
                </span>
              </li>
            ))}
          </ul>
        </div>

        <div className="rounded-xl border border-border bg-card p-6">
          <h3 className="text-sm font-semibold text-foreground">
            Próximas clases
          </h3>
          <ul className="mt-3 space-y-3">
            {upcomingClasses.map((cls) => (
              <li
                key={`${cls.course}-${cls.time}`}
                className="flex items-center justify-between gap-4 text-sm"
              >
                <div className="min-w-0">
                  <p className="truncate font-medium text-card-foreground">
                    {cls.course}
                  </p>
                  <p className="text-xs text-muted-foreground">{cls.room}</p>
                </div>
                <span className="shrink-0 text-xs text-muted-foreground">
                  {cls.time}
                </span>
              </li>
            ))}
          </ul>
        </div>
      </div>
    </section>
  );
}
