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
} from "@/shared/components/ui/dialog";
import { toast } from "@/shared/components/ui/toast";
import { Camera, Pencil, Upload, User } from "lucide-react";
import Image from "next/image";
import { useRef, useState } from "react";
import { updateMyProfile } from "../action";

const ACCEPTED_TYPES = ["image/png", "image/jpeg", "image/webp"];

type ProfileAvatarProps = {
  avatarUrl: string | null;
  name: string;
};

export function ProfileAvatar({ avatarUrl, name }: ProfileAvatarProps) {
  const inputRef = useRef<HTMLInputElement>(null);
  const [previewUrl, setPreviewUrl] = useState<string | null>(null);
  const [open, setOpen] = useState(false);
  const formData = new FormData();

  function handleSelect(event: React.ChangeEvent<HTMLInputElement>) {
    const file = event.target.files?.[0];
    if (!file) return;

    if (!ACCEPTED_TYPES.includes(file.type)) {
      toast.add({
        type: "error",
        description: "Solo se permiten imágenes .png, .jpg, .jpeg o .webp.",
      });
      return;
    }
    formData.append("avatarUrl", file);
    setPreviewUrl(URL.createObjectURL(file));
    setOpen(true);
    event.target.value = "";
  }

  function handleClose(isOpen: boolean) {
    setOpen(isOpen);
    if (!isOpen) {
      if (previewUrl) URL.revokeObjectURL(previewUrl);
      setPreviewUrl(null);
    }
  }

  const updateProfile = async () => {
    const result = await fetch("/api/upload-image", {
      method: "POST",
      body: formData,
    });
    if (!result) {
      toast.add({ type: "error", description: "Error al subir la foto" });
      return;
    }
    const response = await result.json();
    const res = await updateMyProfile({ avatarUrl: response.url });
    toast.add({ type: "success", description: res.message });
    setOpen(false);
    setPreviewUrl(null);
  };

  return (
    <div className="relative shrink-0">
      {avatarUrl ? (
        <Image
          width={200}
          height={200}
          src={avatarUrl}
          alt={name}
          className="size-18 rounded-full object-cover"
        />
      ) : (
        <div className="flex size-18 items-center justify-center rounded-full bg-muted text-muted-foreground">
          <User className="size-8" />
        </div>
      )}
      <Button
        type="button"
        variant="secondary"
        size="icon-sm"
        aria-label={
          avatarUrl ? "Cambiar foto de perfil" : "Agregar foto de perfil"
        }
        onClick={() => inputRef.current?.click()}
        className="absolute -bottom-1 -right-1 rounded-full border-2 border-card"
      >
        {avatarUrl ? (
          <Pencil className="size-3.5" />
        ) : (
          <Camera className="size-3.5" />
        )}
      </Button>

      <input
        ref={inputRef}
        type="file"
        accept={ACCEPTED_TYPES.join(",")}
        className="hidden"
        onChange={handleSelect}
      />

      <Dialog open={open} onOpenChange={handleClose}>
        <DialogContent showCloseButton={false}>
          <DialogHeader>
            <DialogTitle>Previsualizar foto de perfil</DialogTitle>
            <DialogDescription>
              Esta será tu nueva foto de perfil.
            </DialogDescription>
          </DialogHeader>

          {previewUrl && (
            <div className="mx-auto flex size-40 items-center justify-center overflow-hidden rounded-full bg-muted">
              {/* eslint-disable-next-line @next/next/no-img-element */}
              <img
                src={previewUrl}
                alt="Vista previa de la foto de perfil"
                className="size-full object-cover"
              />
            </div>
          )}

          <DialogFooter>
            <DialogClose render={<Button variant="outline">Cancelar</Button>} />
            <Button type="button" onClick={updateProfile}>
              <Upload className="size-4" />
              Guardar
            </Button>
          </DialogFooter>
        </DialogContent>
      </Dialog>
    </div>
  );
}
