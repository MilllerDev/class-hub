"use client";

import { upsertCourse } from "@/features/courses/action";
import { SelectTeacher } from "@/features/courses/components/select-teacher";
import { courseSchema } from "@/features/courses/schemas";
import type { CourseRequest } from "@/features/courses/types";
import type { Course } from "@/graphql/types";
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
import { Textarea } from "@/shared/components/ui/textarea";
import { toast } from "@/shared/components/ui/toast";
import { zodResolver } from "@hookform/resolvers/zod";
import { Pencil, Plus, Sparkles } from "lucide-react";
import { useState } from "react";
import { Controller, SubmitHandler, useForm, useWatch } from "react-hook-form";

export function CourseFormDialog({ course }: { course?: Course }) {
  const isEditing = course !== undefined;
  const [open, setOpen] = useState(false);

  const {
    register,
    handleSubmit,
    reset,
    control,
    formState: { errors, isSubmitting },
  } = useForm({
    resolver: zodResolver(courseSchema),
    defaultValues: {
      name: course?.name,
      description: course?.description,
      userId: course?.profile?.user?.id,
      useAi: false,
    },
  });
  const useAi = useWatch({ control, name: "useAi" });

  const onSubmit: SubmitHandler<CourseRequest> = async (data) => {
    const { success, message } = await upsertCourse(data, course?.id);
    if (success) {
      setOpen(false);
      reset();
    }
    toast.add({ type: success ? "success" : "error", description: message });
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

            <div className="space-y-2">
              <Controller
                name="userId"
                control={control}
                render={({ field }) => (
                  <SelectTeacher value={field.value} onChange={field.onChange} />
                )}
              />
              {errors.userId && (
                <p className="text-xs text-destructive">
                  {errors.userId.message as string}
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
              <Controller
                name="useAi"
                control={control}
                render={({ field }) => (
                  <Switch
                    id="ai-switch"
                    {...register("useAi")}
                    checked={field.value}
                    onCheckedChange={field.onChange}
                    size="sm"
                  />
                )}
              />
            </div>

            <div className="space-y-2">
              <label htmlFor="description" className="text-sm font-medium">
                Descripción
              </label>
              <Textarea
                id="description"
                disabled={useAi}
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
