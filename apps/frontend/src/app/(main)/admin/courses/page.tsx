import { Suspense } from "react";

import { CourseFormDialog } from "@/features/courses/components/course-form-dialog";
import {
  CoursesTable,
  CoursesTableSkeleton,
} from "@/features/courses/components/courses-table";

export default function AdminCoursesPage() {
  return (
    <div className="mx-auto w-full max-w-5xl space-y-6 px-4 py-8">
      <section className="flex items-start justify-between">
        <div>
          <h1 className="text-2xl font-bold tracking-tight text-foreground">
            Cursos del sistema
          </h1>
          <p className="text-sm text-muted-foreground">
            Gestiona los cursos creados en la plataforma.
          </p>
        </div>
        <CourseFormDialog />
      </section>

      <Suspense fallback={<CoursesTableSkeleton />}>
        <CoursesTable />
      </Suspense>
    </div>
  );
}
