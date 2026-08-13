"use client";

import { changeStatus } from "@/features/profiles/action";
import {
  ChangeStatusFields,
  changeStatusSchema,
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
import { Switch } from "@/shared/components/ui/switch";
import { toast } from "@/shared/components/ui/toast";
import { zodResolver } from "@hookform/resolvers/zod";
import { TriangleAlert } from "lucide-react";
import { useState } from "react";
import { Controller, SubmitHandler, useForm, useWatch } from "react-hook-form";
import { ChangeStatusProps } from "../types";

export function ProfileStatusSwitch(props: ChangeStatusProps) {
  const { enabled, userId, userName } = props;
  const [open, setOpen] = useState(false);

  const { control, handleSubmit, reset, formState } =
    useForm<ChangeStatusFields>({
      resolver: zodResolver(changeStatusSchema),
      defaultValues: { enabled },
    });
  const { errors, isSubmitting } = formState;

  const isActive = useWatch({ control, name: "enabled" });

  const onSubmit: SubmitHandler<ChangeStatusFields> = async (data) => {
    const { success, message } = await changeStatus(userId, data.enabled);
    if (success) {
      setOpen(false);
    }
    toast.add({ type: success ? "success" : "error", description: message });
  };

  function handleOpenChange(isOpen: boolean) {
    setOpen(isOpen);
    if (!isOpen) reset({ enabled });
  }

  return (
    <>
      <div className="flex items-center gap-2">
        <Controller
          name="enabled"
          control={control}
          render={({ field }) => (
            <Switch
              checked={field.value}
              disabled={isSubmitting}
              onCheckedChange={(next) => {
                field.onChange(next);
                setOpen(true);
              }}
              size="sm"
            />
          )}
        />
        <span className="text-xs text-muted-foreground">
          {isActive ? "Activo" : "Inactivo"}
        </span>
      </div>

      <AlertDialog open={open} onOpenChange={handleOpenChange}>
        <AlertDialogContent>
          <form onSubmit={handleSubmit(onSubmit)}>
            <AlertDialogHeader>
              <AlertDialogMedia>
                <TriangleAlert className="size-6 text-destructive" />
              </AlertDialogMedia>
              <AlertDialogTitle>
                {isActive ? "Activar cuenta" : "Desactivar cuenta"}
              </AlertDialogTitle>
              <AlertDialogDescription>
                {isActive
                  ? `¿Estás seguro de activar la cuenta de ${userName}?`
                  : `¿Estás seguro de desactivar la cuenta de ${userName}? El usuario no podrá iniciar sesión hasta que sea reactivado.`}
              </AlertDialogDescription>
            </AlertDialogHeader>
            {errors.enabled && (
              <p className="px-4 text-xs text-destructive">
                {errors.enabled.message}
              </p>
            )}
            <AlertDialogFooter>
              <AlertDialogCancel>Cancelar</AlertDialogCancel>
              <AlertDialogAction
                type="submit"
                variant={isActive ? "default" : "destructive"}
                disabled={isSubmitting}
              >
                {isSubmitting
                  ? "Guardando..."
                  : isActive
                    ? "Activar"
                    : "Desactivar"}
              </AlertDialogAction>
            </AlertDialogFooter>
          </form>
        </AlertDialogContent>
      </AlertDialog>
    </>
  );
}
