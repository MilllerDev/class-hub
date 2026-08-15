"use client";

import { changeRole } from "@/features/profiles/action";
import {
  ChangeRoleFields,
  changeRoleSchema,
} from "@/features/profiles/schemas";
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
} from "@/shared/components/ui/alert-dialog";
import { Button } from "@/shared/components/ui/button";
import {
  Dialog,
  DialogClose,
  DialogContent,
  DialogDescription,
  DialogFooter,
  DialogHeader,
  DialogTitle,
  DialogTrigger,
} from "@/shared/components/ui/dialog";
import {
  Select,
  SelectContent,
  SelectGroup,
  SelectItem,
  SelectTrigger,
  SelectValue,
} from "@/shared/components/ui/select";
import { toast } from "@/shared/components/ui/toast";
import { zodResolver } from "@hookform/resolvers/zod";
import { Shuffle } from "lucide-react";
import { useState } from "react";
import { Controller, SubmitHandler, useForm, useWatch } from "react-hook-form";
import { ChangeRoleProps } from "../types";

const roleOptions = [
  { value: "ADMIN", label: "Administrador" },
  { value: "TEACHER", label: "Docente" },
  { value: "STUDENT", label: "Estudiante" },
];

export function RoleChangeDialog(props: ChangeRoleProps) {
  const { currentRole, userId, userName } = props;
  const [open, setOpen] = useState(false);
  const [confirmOpen, setConfirmOpen] = useState(false);

  const { control, handleSubmit, formState, reset } = useForm<ChangeRoleFields>(
    {
      resolver: zodResolver(changeRoleSchema),
      defaultValues: { role: currentRole },
    },
  );
  const { errors, isSubmitting } = formState;

  const role = useWatch({ control, name: "role" });
  const roleLabel =
    roleOptions.find((option) => option.value === role)?.label ?? role;

  const onSubmit: SubmitHandler<ChangeRoleFields> = async (data) => {
    const { success, message } = await changeRole(userId, data.role);
    if (success) {
      setOpen(false);
      setConfirmOpen(false);
    }
    toast.add({
      type: success ? "success" : "error",
      description: success ? `Rol cambiado a: ${roleLabel}` : message,
    });
  };

  function handleOpenChange(isOpen: boolean) {
    setOpen(isOpen);
    reset();
  }

  return (
    <Dialog open={open} onOpenChange={handleOpenChange}>
      <DialogTrigger
        render={
          <Button
            variant="ghost"
            size="icon-sm"
            aria-label={`Cambiar rol de ${userName}`}
          >
            <Shuffle className="size-4" />
          </Button>
        }
      />
      <DialogContent>
        <DialogHeader>
          <DialogTitle>Cambiar rol</DialogTitle>
          <DialogDescription>
            Selecciona el nuevo rol para <strong>{userName}</strong>.
          </DialogDescription>
        </DialogHeader>

        <Controller
          name="role"
          control={control}
          render={({ field }) => (
            <Select
              value={field.value}
              onValueChange={field.onChange}
              items={roleOptions}
            >
              <SelectTrigger className="w-full" aria-label="Nuevo rol">
                <SelectValue placeholder="Seleccionar rol" />
              </SelectTrigger>
              <SelectContent alignItemWithTrigger={false}>
                <SelectGroup>
                  {roleOptions.map((option) => (
                    <SelectItem key={option.value} value={option.value}>
                      {option.label}
                    </SelectItem>
                  ))}
                </SelectGroup>
              </SelectContent>
            </Select>
          )}
        />
        {errors.role && (
          <p className="text-xs text-destructive">{errors.role.message}</p>
        )}

        <DialogFooter>
          <DialogClose render={<Button variant="outline">Cancelar</Button>} />
          <Button
            onClick={() => setConfirmOpen(true)}
            disabled={role === currentRole || !role || isSubmitting}
          >
            Guardar
          </Button>
        </DialogFooter>
      </DialogContent>

      <AlertDialog open={confirmOpen} onOpenChange={setConfirmOpen}>
        <AlertDialogContent>
          <form onSubmit={handleSubmit(onSubmit)}>
            <AlertDialogHeader>
              <AlertDialogMedia>
                <Shuffle className="size-6 text-primary" />
              </AlertDialogMedia>
              <AlertDialogTitle>Confirmar cambio de rol</AlertDialogTitle>
              <AlertDialogDescription>
                ¿Confirmas el cambio de rol de <strong>{userName}</strong> a{" "}
                <strong>{roleLabel}</strong>?
              </AlertDialogDescription>
            </AlertDialogHeader>
            <AlertDialogFooter>
              <AlertDialogCancel>Cancelar</AlertDialogCancel>
              <AlertDialogAction type="submit" disabled={isSubmitting}>
                {isSubmitting ? "Guardando..." : "Confirmar"}
              </AlertDialogAction>
            </AlertDialogFooter>
          </form>
        </AlertDialogContent>
      </AlertDialog>
    </Dialog>
  );
}
