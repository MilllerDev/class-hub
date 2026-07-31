import type { Course } from "@/features/courses/types";
import { Button } from "@/shared/components/ui/button";
import { Ban } from "lucide-react";
import { CourseDeleteDialog } from "./course-delete-dialog";
import { CourseFormDialog } from "./course-form-dialog";
import { CourseStatusBadge } from "./course-status-badge";

export function CourseTableRow({ course }: { course: Course }) {
  return (
    <tr className="bg-card transition-colors hover:bg-muted/30">
      <td className="px-4 py-3 font-medium text-card-foreground">
        {course.name}
      </td>
      <td className="hidden max-w-50 truncate px-4 py-3 text-muted-foreground sm:table-cell">
        {course.description?.trim() ? course.description : "Sin descripción"}
      </td>
      <td className="px-4 py-3">
        <CourseStatusBadge enabled={course.enabled} />
      </td>
      <td className="px-4 py-3">
        <div className="flex items-center justify-end gap-0.5">
          <CourseFormDialog course={course} />
          <Button variant="ghost" size="icon-sm">
            <Ban className="size-4" />
          </Button>
          <CourseDeleteDialog id={course.id} name={course.name} />
        </div>
      </td>
    </tr>
  );
}
