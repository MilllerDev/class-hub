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
import { Input } from "@/shared/components/ui/input";
import { toast } from "@/shared/components/ui/toast";
import { zodResolver } from "@hookform/resolvers/zod";
import { Pencil, Plus } from "lucide-react";
import { useState } from "react";
import { SubmitHandler, useForm } from "react-hook-form";
import { upsertTopic } from "../actions";
import { TopicFields, topicSchema } from "../schemas";
import { Topic } from "../types";

export function TopicFormDialog({
  courseId,
  topic,
}: {
  courseId: string;
  topic?: Topic;
}) {
  const isEditing = topic !== undefined;
  const [open, setOpen] = useState(false);

  const {
    register,
    handleSubmit,
    reset,
    formState: { errors, isSubmitting },
  } = useForm({
    resolver: zodResolver(topicSchema),
    defaultValues: {
      title: topic?.title ?? "",
    },
  });

  const onSubmit: SubmitHandler<TopicFields> = async (data) => {
    const { success, message } = await upsertTopic(
      { title: data.title, courseId },
      topic?.id,
    );
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
              Crear tema
            </Button>
          )
        }
      />
      <DialogContent>
        <form onSubmit={handleSubmit(onSubmit)}>
          <DialogHeader>
            <DialogTitle>
              {isEditing ? "Editar tema" : "Crear tema"}
            </DialogTitle>
            <DialogDescription>
              {isEditing
                ? "Modifica los datos del tema."
                : "Completa los datos para crear un nuevo tema del curso."}
            </DialogDescription>
          </DialogHeader>

          <div className="space-y-4 py-4">
            <div className="space-y-2">
              <label htmlFor="title" className="text-sm font-medium">
                Título
              </label>
              <Input
                id="title"
                {...register("title")}
                placeholder="Título del tema"
              />
              {errors.title && (
                <p className="text-xs text-destructive">
                  {errors.title.message as string}
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
                  : "Crear tema"}
            </Button>
          </DialogFooter>
        </form>
      </DialogContent>
    </Dialog>
  );
}
