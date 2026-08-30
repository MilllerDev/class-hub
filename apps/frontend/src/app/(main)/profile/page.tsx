import { getProfile } from "@/features/profiles/action";
import { ProfileAvatar } from "@/features/profiles/components/profile-avatar";
import { ProfileEditForm } from "@/features/profiles/components/profile-edit-form";
import { ProfilePasswordForm } from "@/features/profiles/components/profile-password-form";
import { formatDate } from "@/lib/utils";
import {
  AtSign,
  CalendarDays,
  IdCard,
  ShieldCheck,
  Timer,
  UserRound,
} from "lucide-react";

export const dynamic = "force-dynamic";

const roleLabels: Record<string, string> = {
  admin: "Administrador",
  teacher: "Docente",
  student: "Estudiante",
};

function RoleBadge({ role }: { role: string }) {
  const key = role.toLowerCase();
  const label = roleLabels[key] ?? role;
  const color =
    key === "admin"
      ? "bg-amber-100 text-amber-800 dark:bg-amber-900/30 dark:text-amber-400"
      : key === "teacher"
        ? "bg-violet-100 text-violet-800 dark:bg-violet-900/30 dark:text-violet-400"
        : key === "student"
          ? "bg-blue-100 text-blue-800 dark:bg-blue-900/30 dark:text-blue-400"
          : "bg-muted text-muted-foreground";

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

function DetailItem({
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

export default async function ProfilePage() {
  const { data: profile } = await getProfile();
  const fullName = `${profile.name} ${profile.lastName}`;
  const roleLabel =
    roleLabels[profile.user.role.toLowerCase()] ?? profile.user.role;

  return (
    <div className="mx-auto w-full max-w-5xl space-y-8 px-4 py-8">
      <section className="rounded-xl border border-border bg-card p-6">
        <div className="flex flex-col gap-4 sm:flex-row sm:items-start sm:justify-between">
          <div>
            <h1 className="text-lg font-semibold text-foreground">Mi cuenta</h1>
            <p className="mt-1 text-sm text-muted-foreground">
              Consulta y administra la información de tu cuenta.
            </p>
          </div>
          <ProfileEditForm profile={profile} />
        </div>

        <div className="mt-6 flex flex-col items-center gap-4 sm:flex-row sm:items-center sm:gap-5">
          <ProfileAvatar avatarUrl={profile.avatarUrl} name={fullName} />
          <div className="flex flex-col items-center gap-2 sm:items-start">
            <h2 className="text-xl font-bold tracking-tight text-foreground">
              {fullName}
            </h2>
            <p className="text-sm text-muted-foreground">
              {profile.user.email}
            </p>
            <div className="flex items-center gap-2">
              <RoleBadge role={profile.user.role} />
              <StatusBadge enabled={profile.user.enabled} />
            </div>
          </div>
        </div>

        <div className="mt-6 grid gap-4 border-t border-border pt-6 sm:grid-cols-2 lg:grid-cols-3">
          <DetailItem icon={UserRound} label="Nombre" value={fullName} />
          <DetailItem icon={AtSign} label="Email" value={profile.user.email} />
          <DetailItem icon={ShieldCheck} label="Rol" value={roleLabel} />
          <DetailItem icon={IdCard} label="ID de usuario" value={profile.user.id} />
          <DetailItem
            icon={CalendarDays}
            label="Fecha de ingreso"
            value={formatDate(profile.user.createdAt)}
          />
          <DetailItem
            icon={Timer}
            label="Última actividad"
            value={formatDate(profile.user.lastLogin)}
          />
        </div>
      </section>

      <section className="flex flex-col gap-4 rounded-xl border border-border bg-card p-6 sm:flex-row sm:items-center sm:justify-between">
        <div>
          <h2 className="text-lg font-semibold text-foreground">
            Cambiar contraseña
          </h2>
          <p className="mt-1 text-sm text-muted-foreground">
            Actualiza la contraseña de tu cuenta.
          </p>
        </div>
        <ProfilePasswordForm />
      </section>
    </div>
  );
}
