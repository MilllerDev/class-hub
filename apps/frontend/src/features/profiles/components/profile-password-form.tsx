"use client";

import { changePassword } from "@/features/auth/action";
import { changePasswordSchema } from "@/features/auth/schemas";
import { ChangePasswordRequest } from "@/features/auth/types";
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

export function ProfilePasswordForm() {
  const [open, setOpen] = useState(false);

  const {
    register,
    handleSubmit,
    reset,
    formState: { errors, isSubmitting, isDirty },
  } = useForm<ChangePasswordRequest>({
    resolver: zodResolver(changePasswordSchema),
  });

  const onSubmit: SubmitHandler<ChangePasswordRequest> = async (data) => {
    const { success, message } = await changePassword(data);
    if (success) setOpen(false);
    toast.add({ type: success ? "success" : "error", description: message });
  };

  const handleOpen = (isOpen: boolean) => {
    setOpen(isOpen);
    reset();
  };

  return (
    <Dialog open={open} onOpenChange={handleOpen}>
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
            id="password"
            label="Contraseña actual"
            error={errors.password?.message}
            {...register("password")}
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
