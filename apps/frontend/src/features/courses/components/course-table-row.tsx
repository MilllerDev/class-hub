import type { Course } from "@/graphql/types";
import { CourseDeleteDialog } from "./course-delete-dialog";
import { CourseFormDialog } from "./course-form-dialog";
import { CourseRemoveTeacherDialog } from "./course-remove-teacher-dialog";
import { CourseStatusBadge } from "./course-status-badge";

export function CourseTableRow({ course }: { course: Course }) {
  const teacher = course.profile
    ? `${course.profile.name} ${course.profile.lastName}`.trim()
    : null;

  return (
    <tr className="bg-card transition-colors hover:bg-muted/30">
      <td className="px-4 py-3 font-medium text-card-foreground">
        {course.name}
      </td>
      <td className="hidden max-w-50 truncate px-4 py-3 text-muted-foreground sm:table-cell">
        {course.description?.trim() ? course.description : "Sin descripción"}
      </td>
      <td className="hidden px-4 py-3 text-muted-foreground sm:table-cell">
        {teacher ?? "—"}
      </td>
      <td className="px-4 py-3">
        <CourseStatusBadge enabled={course.enabled} />
      </td>
      <td className="px-4 py-3">
        <div className="flex items-center justify-end gap-0.5">
          <CourseFormDialog course={course} />
          {teacher && (
            <CourseRemoveTeacherDialog id={course.id} teacherName={teacher} />
          )}
          <CourseDeleteDialog id={course.id} name={course.name} />
        </div>
      </td>
    </tr>
  );
}
