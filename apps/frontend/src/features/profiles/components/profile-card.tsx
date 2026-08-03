import type { Profile } from "@/features/profiles/types";
import { User } from "lucide-react";
import Image from "next/image";

const roleLabels: Record<string, string> = {
  admin: "Administrador",
  teacher: "Docente",
  student: "Estudiante",
};

const roleColors: Record<string, string> = {
  admin: "bg-amber-100 text-amber-800 dark:bg-amber-900/30 dark:text-amber-400",
  teacher:
    "bg-violet-100 text-violet-800 dark:bg-violet-900/30 dark:text-violet-400",
  student: "bg-blue-100 text-blue-800 dark:bg-blue-900/30 dark:text-blue-400",
};

function RoleBadge({ role }: { role: string }) {
  const key = role.toLowerCase();
  const label = roleLabels[key] ?? role;
  const color = roleColors[key] ?? "bg-muted text-muted-foreground";

  return (
    <span
      className={`inline-block rounded-full px-2 py-0.5 text-xs font-medium ${color}`}
    >
      {label}
    </span>
  );
}

function StatusBadge({ enabled }: { enabled: boolean }) {
  return (
    <span
      className={`inline-block rounded-full px-2 py-0.5 text-xs font-medium ${
        enabled
          ? "bg-green-100 text-green-800 dark:bg-green-900/30 dark:text-green-400"
          : "bg-muted text-muted-foreground"
      }`}
    >
      {enabled ? "Activo" : "Inactivo"}
    </span>
  );
}

export function ProfileCard({ profile }: { profile: Profile }) {
  const fullName = `${profile.name} ${profile.lastName}`;

  return (
    <section className="flex flex-col items-center gap-4 rounded-xl border border-border bg-card p-6 sm:flex-row sm:items-center sm:gap-5">
      {profile.avatarUrl ? (
        <Image
          src={profile.avatarUrl}
          alt={fullName}
          width={72}
          height={72}
          className="size-18 rounded-full object-cover"
        />
      ) : (
        <div className="flex size-18 items-center justify-center rounded-full bg-muted text-muted-foreground">
          <User className="size-8" />
        </div>
      )}

      <div className="flex flex-col items-center gap-2 sm:items-start">
        <h2 className="text-xl font-bold tracking-tight text-foreground">
          {fullName}
        </h2>
        <p className="text-sm text-muted-foreground">{profile.user.email}</p>
        <div className="flex items-center gap-2">
          <RoleBadge role={profile.user.role} />
          <StatusBadge enabled={profile.user.enabled} />
        </div>
      </div>
    </section>
  );
}
