"use client";

import { createCourse, updateCourse } from "@/features/courses/action";
import { courseSchema } from "@/features/courses/schemas";
import type { Course, CourseRequest } from "@/features/courses/types";
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
import { Switch } from "@/shared/components/ui/switch";
import { toast } from "@/shared/components/ui/toast";
import { zodResolver } from "@hookform/resolvers/zod";
import { Pencil, Plus, Sparkles } from "lucide-react";
import { useState } from "react";
import { SubmitHandler, useForm } from "react-hook-form";

export function CourseFormDialog({ course }: { course?: Course }) {
  const isEditing = course !== undefined;
  const [useAI, setUseAI] = useState(false);
  const [open, setOpen] = useState(false);

  const {
    register,
    handleSubmit,
    reset,
    formState: { errors, isSubmitting },
  } = useForm({
    resolver: zodResolver(courseSchema),
    defaultValues: {
      name: course?.name,
      description: course?.description,
      userId: course?.userId,
    },
  });

  const onSubmit: SubmitHandler<CourseRequest> = async (data) => {
    const { description, ...rest } = data;
    const payload: CourseRequest = useAI ? rest : { ...rest, description };

    const { message, success } = course
      ? await updateCourse(course.id, payload)
      : await createCourse(payload);
    toast.add({ type: success ? "success" : "error", description: message });

    if (success) {
      setOpen(false);
      reset();
    }
  };

  return (
    <Dialog open={open} onOpenChange={setOpen}>
      <DialogTrigger
        render={
          isEditing ? (
            <Button variant="ghost" size="icon-sm">
              <Pencil className="size-4" />
            </Button>
          ) : (
            <Button>
              <Plus className="size-4" />
              Crear curso
            </Button>
          )
        }
      />
      <DialogContent>
        <form onSubmit={handleSubmit(onSubmit)}>
          <DialogHeader>
            <DialogTitle>
              {isEditing ? "Editar curso" : "Crear curso"}
            </DialogTitle>
            <DialogDescription>
              {isEditing
                ? "Modifica los datos del curso."
                : "Completa los datos para crear un nuevo curso."}
            </DialogDescription>
          </DialogHeader>

          <div className="space-y-4 py-4">
            <div className="space-y-2">
              <label htmlFor="name" className="text-sm font-medium">
                Nombre
              </label>
              <Input
                id="name"
                {...register("name")}
                placeholder="Nombre del curso"
              />
              {errors.name && (
                <p className="text-xs text-destructive">
                  {errors.name.message as string}
                </p>
              )}
            </div>

            <div className="flex items-center justify-between rounded-lg border border-border px-3 py-2.5">
              <div className="flex items-center gap-2">
                <Sparkles className="size-4 text-muted-foreground" />
                <label
                  className="cursor-pointer text-sm font-medium leading-none"
                  htmlFor="ai-switch"
                >
                  Generar descripción con IA
                </label>
              </div>
              <Switch
                id="ai-switch"
                checked={useAI}
                onCheckedChange={setUseAI}
                size="sm"
              />
            </div>

            <div className="space-y-2">
              <label htmlFor="description" className="text-sm font-medium">
                Descripción
              </label>
              <Input
                id="description"
                disabled={useAI}
                {...register("description")}
                placeholder={"Descripción (opcional)"}
              />
              {errors.description && (
                <p className="text-xs text-destructive">
                  {errors.description.message as string}
                </p>
              )}
            </div>
          </div>
          <DialogFooter>
            <DialogClose
              render={
                <Button variant={"outline"} onClick={() => reset()}>
                  Cancelar
                </Button>
              }
            />
            <Button type="submit" disabled={isSubmitting}>
              {isSubmitting
                ? "Guardando..."
                : isEditing
                  ? "Guardar cambios"
                  : "Crear curso"}
            </Button>
          </DialogFooter>
        </form>
      </DialogContent>
    </Dialog>
  );
}
