import { formatDate } from "@/lib/utils";
import { Skeleton } from "@/shared/components/ui/skeleton";
import { BookOpen, Calendar } from "lucide-react";
import Link from "next/link";
import { getCoursesForStudent } from "../action";

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
  const { courses } = await getCoursesForStudent();

  return (
    <section className="grid gap-4 sm:grid-cols-2 lg:grid-cols-3">
      {courses.map((course) => (
        <Link
          key={course.id}
          href={`/student/courses/${course.slug}`}
          className="group flex flex-col rounded-xl border border-border bg-card p-5 transition-colors hover:bg-accent"
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

          <p className="mt-1.5 line-clamp-2 flex-1 text-sm text-muted-foreground">
            {course.description?.trim()
              ? course.description
              : "Sin descripción"}
          </p>

          <div className="mt-4 flex items-center gap-1.5 border-t border-border pt-3 text-xs text-muted-foreground">
            <Calendar className="size-3.5" />
            Creado el {formatDate(course.createdAt)}
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
        <div key={i} className="rounded-xl border border-border bg-card p-5">
          <div className="flex items-start justify-between">
            <Skeleton className="size-10 rounded-lg" />
            <Skeleton className="h-5 w-16 rounded-full" />
          </div>
          <Skeleton className="mt-4 h-5 w-3/4" />
          <Skeleton className="mt-3 h-4 w-full" />
          <Skeleton className="mt-2 h-4 w-2/3" />
          <div className="mt-4 flex items-center justify-between border-t border-border pt-3">
            <Skeleton className="h-4 w-28" />
          </div>
        </div>
      ))}
    </section>
  );
}
