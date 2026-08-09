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
import { Ban, TriangleAlert } from "lucide-react";
import { useState } from "react";

type DeactivateUserDialogProps = {
  userName: string;
  disabled?: boolean;
};

export function DeactivateUserDialog({
  userName,
  disabled,
}: DeactivateUserDialogProps) {
  const [open, setOpen] = useState(false);

  function handleDeactivate() {
    toast.add({
      type: "success",
      description: `Cuenta de ${userName} desactivada`,
    });
    setOpen(false);
  }

  return (
    <AlertDialog open={open} onOpenChange={setOpen}>
      <AlertDialogTrigger
        render={
          <Button
            variant="ghost"
            size="icon-sm"
            disabled={disabled}
            aria-label={`Desactivar cuenta de ${userName}`}
          >
            <Ban className="size-4" />
          </Button>
        }
      />
      <AlertDialogContent>
        <AlertDialogHeader>
          <AlertDialogMedia>
            <TriangleAlert className="size-6 text-destructive" />
          </AlertDialogMedia>
          <AlertDialogTitle>Desactivar cuenta</AlertDialogTitle>
          <AlertDialogDescription>
            ¿Estás seguro de desactivar la cuenta de <strong>{userName}</strong>?
            El usuario no podrá iniciar sesión hasta que sea reactivado.
          </AlertDialogDescription>
        </AlertDialogHeader>
        <AlertDialogFooter>
          <AlertDialogCancel>Cancelar</AlertDialogCancel>
          <AlertDialogAction variant="destructive" onClick={handleDeactivate}>
            Desactivar
          </AlertDialogAction>
        </AlertDialogFooter>
      </AlertDialogContent>
    </AlertDialog>
  );
}
