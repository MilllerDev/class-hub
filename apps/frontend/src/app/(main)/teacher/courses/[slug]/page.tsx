import { getCourse } from "@/features/courses/action";
import { CourseStatusBadge } from "@/features/courses/components/course-status-badge";
import { TeacherTopicAccordion } from "@/features/topics/components/teacher-topic-accordion";
import { TopicFormDialog } from "@/features/topics/components/topic-form-dialog";
import { formatDate } from "@/lib/utils";
import { Calendar, ListTree } from "lucide-react";
import Link from "next/link";
import { notFound } from "next/navigation";
import { Suspense } from "react";

export default async function TeacherCourseDetailPage({
  params,
}: {
  params: Promise<{ slug: string }>;
}) {
  const { slug } = await params;
  const res = await getCourse(slug);

  if (!res.success) {
    notFound();
  }

  const course = res.data;

  return (
    <div className="mx-auto w-full max-w-3xl space-y-8 px-4 py-8">
      <section className="space-y-1">
        <Link
          href="/teacher/courses"
          className="inline-flex items-center gap-1.5 text-sm text-muted-foreground hover:text-foreground transition-colors"
        >
          Volver a mis cursos
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
        <div className="flex items-center justify-between">
          <h2 className="flex items-center gap-2 text-lg font-semibold text-foreground">
            <ListTree className="size-5" />
            Temas del curso
          </h2>
          <TopicFormDialog courseId={course.id} />
        </div>
        <Suspense fallback={<>cargando</>}>
          <TeacherTopicAccordion slug={course.slug} courseId={course.id} />
        </Suspense>
      </section>
    </div>
  );
}
