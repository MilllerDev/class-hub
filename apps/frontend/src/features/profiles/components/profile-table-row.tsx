import type { Profile } from "@/features/profiles/types";
import { formatDate } from "@/lib/utils";
import { RoleBadge } from "@/shared/components/ui/role-badge";
import { User } from "lucide-react";
import Image from "next/image";
import Link from "next/link";
import { ProfileStatusSwitch } from "./profile-status-switch";
import { RoleChangeDialog } from "./role-change-dialog";

export function ProfileTableRow({ profile }: { profile: Profile }) {
  const { name, lastName, user, avatarUrl } = profile;
  const fullName = `${name} ${lastName}`;

  return (
    <tr className="bg-card transition-colors hover:bg-muted/30">
      <td className="px-4 py-3">
        <Link href={`/admin/users/${profile.user.id}`}>
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
        </Link>
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
