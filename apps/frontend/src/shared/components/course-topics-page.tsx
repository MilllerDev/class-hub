import { CourseStatusBadge } from "@/features/courses/components/course-status-badge";
import type { Course } from "@/features/courses/types";
import {
  TopicList,
  TopicListSkeleton,
} from "@/features/topics/components/topic-list";
import { formatDate } from "@/lib/utils";
import { ArrowLeft, Calendar, ListTree } from "lucide-react";
import Link from "next/link";
import { Suspense } from "react";

export function CourseTopicsPage({
  course,
  backHref,
}: {
  course: Course;
  backHref: string;
}) {
  return (
    <div className="mx-auto w-full max-w-3xl space-y-8 px-4 py-8">
      <section className="space-y-1">
        <Link
          href={backHref}
          className="inline-flex items-center gap-1.5 text-sm text-muted-foreground hover:text-foreground transition-colors"
        >
          <ArrowLeft className="size-4" />
          Volver a cursos
        </Link>
        <div className="flex items-center gap-3">
          <h1 className="text-2xl font-bold tracking-tight text-foreground">
            {course.name}
          </h1>
          <CourseStatusBadge enabled={course.enabled} />
        </div>
        <p className="text-sm text-muted-foreground">
          {course.description?.trim() ? course.description : "Sin descripción"}
        </p>
        <div className="flex items-center gap-1.5 text-xs text-muted-foreground">
          <Calendar className="size-3.5" />
          Creado el {formatDate(course.createdAt)}
        </div>
      </section>

      <section className="space-y-3">
        <h2 className="flex items-center gap-2 text-lg font-semibold text-foreground">
          <ListTree className="size-5" />
          Temas del curso
        </h2>
        <Suspense fallback={<TopicListSkeleton />}>
          <TopicList slug={course.slug} />
        </Suspense>
      </section>
    </div>
  );
}
