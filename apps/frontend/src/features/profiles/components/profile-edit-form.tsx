"use client";

import type { Profile } from "@/features/profiles/types";
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
import { Input } from "@/shared/components/ui/input";
import { toast } from "@/shared/components/ui/toast";
import { zodResolver } from "@hookform/resolvers/zod";
import { Pencil } from "lucide-react";
import { useState } from "react";
import { SubmitHandler, useForm } from "react-hook-form";
import { updateMyProfile } from "../action";
import { ProfileSchema, profileSchema } from "../schemas";

export function ProfileEditForm({ profile }: { profile: Profile }) {
  const {
    register,
    formState: { isSubmitting, dirtyFields, isDirty, errors },
    reset,
    handleSubmit,
  } = useForm<ProfileSchema>({
    resolver: zodResolver(profileSchema),
    defaultValues: {
      name: profile.name,
      lastName: profile.lastName,
    },
  });
  const [open, setOpen] = useState(false);

  const onSubmit: SubmitHandler<ProfileSchema> = async (data) => {
    const payload = Object.keys(dirtyFields).reduce((acc, key) => {
      acc[key as keyof ProfileSchema] = data[key as keyof ProfileSchema];
      return acc;
    }, {} as Partial<ProfileSchema>);

    console.log(payload);

    const { message, success } = await updateMyProfile(payload);
    if (success) {
      setOpen(false);
    }
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
            <Pencil className="size-4" />
            Editar cuenta
          </Button>
        }
      />
      <DialogContent>
        <DialogHeader>
          <DialogTitle>Editar cuenta</DialogTitle>
          <DialogDescription>
            Actualiza la información de tu perfil.
          </DialogDescription>
        </DialogHeader>

        <form className="space-y-4" onSubmit={handleSubmit(onSubmit)}>
          <div className="grid gap-4">
            <div className="space-y-2">
              <label htmlFor="name" className="text-sm font-medium">
                Nombre
              </label>
              <Input
                id="name"
                aria-invalid={!!errors.name}
                {...register("name")}
              />
              {errors.name && (
                <p className="text-xs text-destructive">
                  {errors.name.message}
                </p>
              )}
            </div>
            <div className="space-y-2">
              <label htmlFor="lastName" className="text-sm font-medium">
                Apellidos
              </label>
              <Input
                id="lastName"
                aria-invalid={!!errors.lastName}
                {...register("lastName")}
              />
              {errors.lastName && (
                <p className="text-xs text-destructive">
                  {errors.lastName.message}
                </p>
              )}
            </div>
          </div>
          <DialogFooter>
            <DialogClose render={<Button variant="outline">Cancelar</Button>} />
            <Button type="submit" disabled={isSubmitting || !isDirty}>
              {isSubmitting ? "Guardando" : "Guardar"}
            </Button>
          </DialogFooter>
        </form>
      </DialogContent>
    </Dialog>
  );
}
