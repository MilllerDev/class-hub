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
import {
  Select,
  SelectContent,
  SelectGroup,
  SelectItem,
  SelectTrigger,
  SelectValue,
} from "@/shared/components/ui/select";
import { toast } from "@/shared/components/ui/toast";
import { UserCog } from "lucide-react";
import { useState } from "react";

const roleOptions = [
  { value: "ADMIN", label: "Administrador" },
  { value: "TEACHER", label: "Docente" },
  { value: "STUDENT", label: "Estudiante" },
];

type RoleChangeDialogProps = {
  currentRole: string;
  userName: string;
};

export function RoleChangeDialog({
  currentRole,
  userName,
}: RoleChangeDialogProps) {
  const [open, setOpen] = useState(false);
  const [role, setRole] = useState(currentRole || null);

  function handleConfirm() {
    if (role === currentRole) return;
    toast.add({
      type: "success",
      description: `Mensaje`,
    });
    setOpen(false);
  }

  return (
    <Dialog open={open} onOpenChange={setOpen}>
      <DialogTrigger
        render={
          <Button
            variant="ghost"
            size="icon-sm"
            aria-label={`Cambiar rol de ${userName}`}
          >
            <UserCog className="size-4" />
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

        <Select value={role} onValueChange={setRole} items={roleOptions}>
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

        <DialogFooter>
          <DialogClose render={<Button variant="outline">Cancelar</Button>} />
          <Button
            onClick={handleConfirm}
            disabled={role === currentRole}
          >
            Guardar
          </Button>
        </DialogFooter>
      </DialogContent>
    </Dialog>
  );
}
