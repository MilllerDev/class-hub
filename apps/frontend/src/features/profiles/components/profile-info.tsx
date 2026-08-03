import type { Profile } from "@/features/profiles/types";
import { AtSign, CalendarDays, Fingerprint, IdCard, Timer, UserRound } from "lucide-react";

const roleLabels: Record<string, string> = {
  admin: "Administrador",
  teacher: "Docente",
  student: "Estudiante",
};

const mockInfo = {
  joinedDate: "15 mar 2025",
  lastActive: "Hoy",
};

function InfoItem({
  icon: Icon,
  label,
  value,
}: {
  icon: React.ComponentType<{ className?: string }>;
  label: string;
  value: string;
}) {
  return (
    <div className="flex items-center gap-3">
      <div className="flex size-9 shrink-0 items-center justify-center rounded-lg bg-muted text-muted-foreground">
        <Icon className="size-4" />
      </div>
      <div className="min-w-0">
        <p className="text-xs text-muted-foreground">{label}</p>
        <p className="truncate text-sm font-medium text-card-foreground">
          {value}
        </p>
      </div>
    </div>
  );
}

export function ProfileInfo({ profile }: { profile: Profile }) {
  const roleLabel =
    roleLabels[profile.user.role.toLowerCase()] ?? profile.user.role;

  return (
    <section className="rounded-xl border border-border bg-card p-6">
      <h2 className="text-lg font-semibold text-foreground">
        Información de la cuenta
      </h2>
      <div className="mt-4 grid gap-4 sm:grid-cols-2 lg:grid-cols-3">
        <InfoItem
          icon={Fingerprint}
          label="ID de usuario"
          value={profile.id}
        />
        <InfoItem icon={AtSign} label="Email" value={profile.user.email} />
        <InfoItem icon={UserRound} label="Rol" value={roleLabel} />
        <InfoItem
          icon={IdCard}
          label="ID de perfil"
          value={profile.user.id}
        />
        <InfoItem
          icon={CalendarDays}
          label="Fecha de ingreso"
          value={mockInfo.joinedDate}
        />
        <InfoItem icon={Timer} label="Última actividad" value={mockInfo.lastActive} />
      </div>
    </section>
  );
}
