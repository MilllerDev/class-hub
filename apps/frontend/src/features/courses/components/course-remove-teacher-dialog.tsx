"use client";

import {
  AlertDialog,
  AlertDialogAction,
  AlertDialogCancel,
  AlertDialogContent,
  AlertDialogDescription,
  AlertDialogFooter,
  AlertDialogHeader,
  AlertDialogMedia,
  AlertDialogTitle,
  AlertDialogTrigger,
} from "@/shared/components/ui/alert-dialog";
import { Button } from "@/shared/components/ui/button";
import { toast } from "@/shared/components/ui/toast";
import { TriangleAlert, UserRoundMinus } from "lucide-react";
import { useState } from "react";
import { removeCourseTeacher } from "../action";

export function CourseRemoveTeacherDialog({
  id,
  teacherName,
}: {
  id: string;
  teacherName: string;
}) {
  const [open, setOpen] = useState(false);

  async function handle() {
    const { message } = await removeCourseTeacher(id);
    toast.add({ description: message });
    setOpen(false);
  }

  return (
    <AlertDialog open={open} onOpenChange={setOpen}>
      <AlertDialogTrigger
        render={
          <Button variant="ghost" size="icon-sm">
            <UserRoundMinus className="size-4" />
          </Button>
        }
      />
      <AlertDialogContent>
        <AlertDialogHeader>
          <AlertDialogMedia>
            <TriangleAlert className="size-6 text-destructive" />
          </AlertDialogMedia>
          <AlertDialogTitle>Quitar docente</AlertDialogTitle>
          <AlertDialogDescription>
            ¿Estás seguro de quitar a <strong>{teacherName}</strong> del curso?
            Esta acción no se puede deshacer.
          </AlertDialogDescription>
        </AlertDialogHeader>
        <AlertDialogFooter>
          <AlertDialogCancel>Cancelar</AlertDialogCancel>
          <AlertDialogAction
            variant="destructive"
            onClick={async () => {
              await handle();
            }}
          >
            Quitar
          </AlertDialogAction>
        </AlertDialogFooter>
      </AlertDialogContent>
    </AlertDialog>
  );
}
