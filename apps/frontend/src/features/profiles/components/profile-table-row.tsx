import type { Profile } from "@/features/profiles/types";
import { formatDate } from "@/lib/utils";
import { User } from "lucide-react";
import Image from "next/image";
import { ProfileStatusSwitch } from "./profile-status-switch";
import { RoleChangeDialog } from "./role-change-dialog";

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

export function ProfileTableRow({ profile }: { profile: Profile }) {
  const { name, lastName, user, avatarUrl } = profile;
  const fullName = `${name} ${lastName}`;

  return (
    <tr className="bg-card transition-colors hover:bg-muted/30">
      <td className="px-4 py-3">
        <div className="flex items-center gap-3">
          {avatarUrl ? (
            <Image
              src={avatarUrl}
              alt={fullName}
              width={32}
              height={32}
              className="size-8 shrink-0 rounded-full object-cover"
            />
          ) : (
            <div className="flex size-8 shrink-0 items-center justify-center rounded-full bg-muted text-muted-foreground">
              <User className="size-4" />
            </div>
          )}
          <span className="font-medium text-card-foreground">{fullName}</span>
        </div>
      </td>
      <td className="hidden px-4 py-3 text-muted-foreground sm:table-cell">
        {user.email}
      </td>
      <td className="px-4 py-3">
        <RoleBadge role={user.role} />
      </td>
      <td className="px-4 py-3">
        <ProfileStatusSwitch
          userId={user.id}
          enabled={user.enabled}
          userName={fullName}
        />
      </td>
      <td className="hidden px-4 py-3 text-muted-foreground md:table-cell">
        {formatDate(user.createdAt)}
      </td>
      <td className="px-4 py-3">
        <div className="flex items-center justify-end">
          <RoleChangeDialog
            userId={user.id}
            currentRole={user.role}
            userName={fullName}
            key={user.role}
          />
        </div>
      </td>
    </tr>
  );
}
