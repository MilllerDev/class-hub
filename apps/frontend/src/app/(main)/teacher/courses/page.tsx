import { ArrowLeft } from "lucide-react";
import Link from "next/link";
import { Suspense } from "react";
import {
  TeacherCourseList,
  TeacherCourseListSkeleton,
} from "@/features/courses/components/teacher-course-list";

export default function TeacherCoursesPage() {
  return (
    <div className="mx-auto w-full max-w-5xl space-y-8 px-4 py-8">
      <section className="space-y-1">
        <Link
          href="/teacher"
          className="inline-flex items-center gap-1.5 text-sm text-muted-foreground hover:text-foreground transition-colors"
        >
          <ArrowLeft className="size-4" />
          Volver al dashboard
        </Link>
        <h1 className="text-2xl font-bold tracking-tight text-foreground">
          Mis cursos
        </h1>
        <p className="text-sm text-muted-foreground">
          Todos los cursos que impartes.
        </p>
      </section>

      <Suspense fallback={<TeacherCourseListSkeleton />}>
        <TeacherCourseList />
      </Suspense>
    </div>
  );
}