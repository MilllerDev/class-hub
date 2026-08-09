import { getCourses } from "@/features/courses/action";
import { CourseTableRow } from "./course-table-row";

export async function CoursesTable() {
  const { courses } = await getCourses();

  if (courses.length === 0) {
    return (
      <div className="rounded-xl border border-border bg-card p-8 text-center text-sm text-muted-foreground">
        No hay datos que mostrar
      </div>
    );
  }

  return (
    <>
      <div className="overflow-hidden rounded-xl border border-border">
        <table className="w-full text-sm">
          <thead>
            <tr className="bg-muted/50">
              <th className="px-4 py-3 text-left font-medium text-muted-foreground">
                Curso
              </th>
              <th className="hidden max-w-50 px-4 py-3 text-left font-medium text-muted-foreground sm:table-cell">
                Descripción
              </th>
              <th className="hidden px-4 py-3 text-left font-medium text-muted-foreground sm:table-cell">
                Docente
              </th>
              <th className="px-4 py-3 text-left font-medium text-muted-foreground">
                Estado
              </th>
              <th className="px-4 py-3 text-right font-medium text-muted-foreground">
                Acciones
              </th>
            </tr>
          </thead>
          <tbody className="divide-y divide-border">
            {courses.map((course) => (
              <CourseTableRow key={course.id} course={course} />
            ))}
          </tbody>
        </table>
      </div>
    </>
  );
}

export function CoursesTableSkeleton() {
  return (
    <div className="overflow-hidden rounded-xl border border-border">
      <table className="w-full text-sm">
        <thead>
          <tr className="bg-muted/50">
            <th className="px-4 py-3 text-left font-medium text-muted-foreground">
              Curso
            </th>
            <th className="hidden max-w-50 px-4 py-3 text-left font-medium text-muted-foreground sm:table-cell">
              Descripción
            </th>
            <th className="hidden px-4 py-3 text-left font-medium text-muted-foreground sm:table-cell">
              Docente
            </th>
            <th className="px-4 py-3 text-left font-medium text-muted-foreground">
              Estado
            </th>
            <th className="px-4 py-3 text-right font-medium text-muted-foreground">
              Acciones
            </th>
          </tr>
        </thead>
        <tbody className="divide-y divide-border">
          {Array.from({ length: 5 }).map((_, i) => (
            <tr key={i} className="bg-card">
              <td className="px-4 py-3">
                <div className="h-4 w-2/3 animate-pulse rounded bg-muted" />
              </td>
              <td className="hidden px-4 py-3 sm:table-cell">
                <div className="h-4 w-1/2 animate-pulse rounded bg-muted" />
              </td>
              <td className="hidden px-4 py-3 sm:table-cell">
                <div className="h-4 w-1/3 animate-pulse rounded bg-muted" />
              </td>
              <td className="px-4 py-3">
                <div className="h-5 w-16 animate-pulse rounded-full bg-muted" />
              </td>
              <td className="px-4 py-3">
                <div className="ml-auto h-8 w-24 animate-pulse rounded-lg bg-muted" />
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}
