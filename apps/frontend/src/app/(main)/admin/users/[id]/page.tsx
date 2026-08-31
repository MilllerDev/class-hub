import { getOtherProfile } from "@/features/profiles/action";
import { formatDate } from "@/lib/utils";
import { RoleBadge } from "@/shared/components/ui/role-badge";
import { ArrowLeft, AtSign, CalendarDays, IdCard, Timer, User, UserRound } from "lucide-react";
import Image from "next/image";
import Link from "next/link";

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

export default async function AdminUserDetailPage({
  params,
}: {
  params: Promise<{ id: string }>;
}) {
  const { id } = await params;
  const { data: profile } = await getOtherProfile(id);

  if (!profile) {
    return (
      <div className="mx-auto w-full max-w-3xl space-y-8 px-4 py-8">
        <Link
          href="/admin/users"
          className="inline-flex items-center gap-1.5 text-sm text-muted-foreground hover:text-foreground transition-colors"
        >
          <ArrowLeft className="size-4" />
          Volver a usuarios
        </Link>

        <section className="rounded-xl border border-border bg-card p-8 text-center">
          <h1 className="text-xl font-bold text-foreground">
            Usuario no encontrado
          </h1>
          <p className="mt-1 text-sm text-muted-foreground">
            No se encontró un usuario con el ID: {id}
          </p>
        </section>
      </div>
    );
  }

  const fullName = `${profile.name} ${profile.lastName}`;

  return (
    <div className="mx-auto w-full max-w-5xl space-y-8 px-4 py-8">
      <Link
        href="/admin/users"
        className="inline-flex items-center gap-1.5 text-sm text-muted-foreground hover:text-foreground transition-colors"
      >
        <ArrowLeft className="size-4" />
        Volver a usuarios
      </Link>

      <section className="rounded-xl border border-border bg-card p-6">
        <div className="mt-2 flex flex-col items-center gap-4 sm:flex-row sm:items-center sm:gap-5">
          {profile.avatarUrl ? (
            <Image
              src={profile.avatarUrl}
              alt={fullName}
              width={64}
              height={64}
              className="size-16 shrink-0 rounded-full object-cover"
            />
          ) : (
            <div className="flex size-16 shrink-0 items-center justify-center rounded-full bg-muted text-muted-foreground">
              <User className="size-8" />
            </div>
          )}
          <div className="flex flex-col items-center gap-2 sm:items-start">
            <h1 className="text-2xl font-bold tracking-tight text-foreground">
              {fullName}
            </h1>
            <p className="text-sm text-muted-foreground">{profile.user.email}</p>
            <div className="flex items-center gap-2">
              <RoleBadge role={profile.user.role} />
              <StatusBadge enabled={profile.user.enabled} />
            </div>
          </div>
        </div>

        <div className="mt-6 grid gap-4 border-t border-border pt-6 sm:grid-cols-2 lg:grid-cols-3">
          <DetailItem icon={UserRound} label="Nombre" value={fullName} />
          <DetailItem icon={AtSign} label="Email" value={profile.user.email} />
          <DetailItem
            icon={IdCard}
            label="ID de perfil"
            value={profile.id}
          />
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
    </div>
  );
}
