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
import { Trash2, TriangleAlert } from "lucide-react";
import { useState } from "react";
import { deleteCourse } from "../action";

export function CourseDeleteDialog({ id, name }: { id: string; name: string }) {
  const [open, setOpen] = useState(false);
  async function handle() {
    const { message } = await deleteCourse(id);
    toast.add({ description: message });
    setOpen(false);
  }
  return (
    <AlertDialog open={open} onOpenChange={setOpen}>
      <AlertDialogTrigger
        render={
          <Button variant="ghost" size="icon-sm">
            <Trash2 className="size-4" />
          </Button>
        }
      />
      <AlertDialogContent>
        <AlertDialogHeader>
          <AlertDialogMedia>
            <TriangleAlert className="size-6 text-destructive" />
          </AlertDialogMedia>
          <AlertDialogTitle>Eliminar curso</AlertDialogTitle>
          <AlertDialogDescription>
            ¿Estás seguro de eliminar <strong>{name}</strong>? Esta acción no se
            puede deshacer.
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
            Eliminar
          </AlertDialogAction>
        </AlertDialogFooter>
      </AlertDialogContent>
    </AlertDialog>
  );
}
