import { ArrowLeft } from "lucide-react";
import Link from "next/link";
import { Suspense } from "react";
import { CourseList, CourseListSkeleton } from "@/features/courses/components/course-list";

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

      <Suspense fallback={<CourseListSkeleton />}>
        <CourseList />
      </Suspense>
    </div>
  );
}
