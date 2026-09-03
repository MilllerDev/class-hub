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
import { TriangleAlert, X } from "lucide-react";
import { useState } from "react";
import { deleteTopicResource } from "../actions";

function ResourceDeleteDialog({
  topicId,
  id,
  fileName,
}: {
  topicId: string;
  id: string;
  fileName: string;
}) {
  const [open, setOpen] = useState(false);

  const deleteResource = async () => {
    const { success, message } = await deleteTopicResource(topicId, id);
    if (success) setOpen(false);
    toast.add({ type: success ? "success" : "error", title: message });
  };

  return (
    <AlertDialog open={open} onOpenChange={setOpen}>
      <AlertDialogTrigger
        aria-label={`Eliminar recurso ${fileName}`}
        render={
          <Button variant="ghost" size="icon-xs">
            <X className="size-3" />
          </Button>
        }
      />
      <AlertDialogContent>
        <AlertDialogHeader>
          <AlertDialogMedia>
            <TriangleAlert className="size-6 text-destructive" />
          </AlertDialogMedia>
          <AlertDialogTitle>Eliminar recurso</AlertDialogTitle>
          <AlertDialogDescription>
            ¿Estás seguro de eliminar <strong>{fileName}</strong>? Esta acción
            no se puede deshacer.
          </AlertDialogDescription>
        </AlertDialogHeader>
        <AlertDialogFooter>
          <AlertDialogCancel>Cancelar</AlertDialogCancel>
          <AlertDialogAction onClick={deleteResource} variant="destructive">
            Eliminar
          </AlertDialogAction>
        </AlertDialogFooter>
      </AlertDialogContent>
    </AlertDialog>
  );
}

export { ResourceDeleteDialog };
