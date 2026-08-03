import { BookOpen, CheckCircle, User } from "lucide-react";
import Link from "next/link";
import { getCourses } from "../action";

function Badge({ enabled }: { enabled: boolean }) {
  const color = enabled
    ? "bg-green-100 text-green-800 dark:bg-green-900/30 dark:text-green-400"
    : "bg-muted text-muted-foreground";

  return (
    <span
      className={`inline-block rounded-full px-2 py-0.5 text-xs font-medium ${color}`}
    >
      {enabled ? "Activo" : "Inactivo"}
    </span>
  );
}

export async function CourseList() {
  const res = await getCourses();
  const courses = (res.data ?? []).map((course) => ({
    id: course.id,
    name: course.name,
    userId: course.userId,
    enabled: course.enabled,
    average: 0,
  }));

  return (
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
            <Badge enabled={course.enabled} />
          </div>

          <h3 className="mt-4 text-base font-semibold text-card-foreground group-hover:text-accent-foreground">
            {course.name}
          </h3>

          <div className="mt-1 flex items-center gap-1.5 text-xs text-muted-foreground">
            <User className="size-3" />
            {course.userId ?? "—"}
          </div>

          <div className="mt-4 flex items-center justify-between border-t border-border pt-3">
            <span className="text-xs text-muted-foreground" />
            <div className="flex items-center gap-1 text-xs font-medium text-card-foreground">
              <CheckCircle className="size-3" />
              {course.average}
            </div>
          </div>
        </Link>
      ))}
    </section>
  );
}

export function CourseListSkeleton() {
  return (
    <section className="grid gap-4 sm:grid-cols-2 lg:grid-cols-3">
      {Array.from({ length: 6 }).map((_, i) => (
        <div
          key={i}
          className="animate-pulse rounded-xl border border-border bg-card p-5"
        >
          <div className="flex items-start justify-between">
            <div className="size-10 rounded-lg bg-muted" />
            <div className="h-5 w-16 rounded-full bg-muted" />
          </div>
          <div className="mt-4 h-5 w-3/4 rounded bg-muted" />
          <div className="mt-3 h-4 w-1/2 rounded bg-muted" />
          <div className="mt-4 flex items-center justify-between border-t border-border pt-3">
            <div className="h-4 w-24 rounded bg-muted" />
            <div className="h-4 w-12 rounded bg-muted" />
          </div>
        </div>
      ))}
    </section>
  );
}
