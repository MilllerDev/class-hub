"use client";

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
import { PasswordInput } from "@/shared/components/ui/input";
import { toast } from "@/shared/components/ui/toast";
import { zodResolver } from "@hookform/resolvers/zod";
import { KeyRound, Lock } from "lucide-react";
import { useState } from "react";
import { SubmitHandler, useForm } from "react-hook-form";
import { z } from "zod";

const passwordSchema = z
  .object({
    currentPassword: z
      .string()
      .trim()
      .min(1, { message: "La contraseña actual es obligatoria" }),
    newPassword: z.string().trim().min(6, {
      message: "La nueva contraseña debe tener mínimo 6 carácteres",
    }),
    confirmPassword: z.string(),
  })
  .refine((data) => data.newPassword === data.confirmPassword, {
    message: "Las contraseñas no coinciden",
    path: ["confirmPassword"],
  });

type PasswordFields = z.infer<typeof passwordSchema>;

export function ProfilePasswordForm() {
  const [open, setOpen] = useState(false);

  const {
    register,
    handleSubmit,
    reset,
    formState: { errors, isSubmitting, isDirty },
  } = useForm<PasswordFields>({
    resolver: zodResolver(passwordSchema),
  });

  const onSubmit: SubmitHandler<PasswordFields> = async () => {
    await new Promise((resolve) => setTimeout(resolve, 600));
    setOpen(false);
    reset();
    toast.add({
      type: "success",
      description: "Contraseña actualizada correctamente (demo).",
    });
  };

  return (
    <Dialog
      open={open}
      onOpenChange={(isOpen) => {
        setOpen(isOpen);
        if (!isOpen) {
          reset();
        }
      }}
    >
      <DialogTrigger
        render={
          <Button type="button" variant="outline">
            <Lock className="size-4" />
            Cambiar contraseña
          </Button>
        }
      />
      <DialogContent>
        <DialogHeader>
          <DialogTitle>Cambiar contraseña</DialogTitle>
          <DialogDescription>
            Ingresa tu contraseña actual y define una nueva.
          </DialogDescription>
        </DialogHeader>

        <form className="space-y-4" onSubmit={handleSubmit(onSubmit)}>
          <PasswordInput
            id="currentPassword"
            label="Contraseña actual"
            error={errors.currentPassword?.message}
            {...register("currentPassword")}
          />

          <PasswordInput
            id="newPassword"
            label="Nueva contraseña"
            error={errors.newPassword?.message}
            {...register("newPassword")}
          />

          <PasswordInput
            id="confirmPassword"
            label="Confirmar nueva contraseña"
            error={errors.confirmPassword?.message}
            {...register("confirmPassword")}
          />

          <DialogFooter>
            <DialogClose render={<Button variant="outline">Cancelar</Button>} />
            <Button type="submit" disabled={isSubmitting || !isDirty}>
              <KeyRound className="size-4" />
              {isSubmitting ? "Cambiando..." : "Guardar"}
            </Button>
          </DialogFooter>
        </form>
      </DialogContent>
    </Dialog>
  );
}
