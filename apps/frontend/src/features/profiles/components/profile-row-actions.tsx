import { DeactivateUserDialog } from "./deactivate-user-dialog";
import { RoleChangeDialog } from "./role-change-dialog";

type ProfileRowActionsProps = {
  role: string;
  enabled: boolean;
  userName: string;
};

export function ProfileRowActions({
  role,
  enabled,
  userName,
}: ProfileRowActionsProps) {
  return (
    <div className="flex items-center justify-end gap-1">
      <RoleChangeDialog currentRole={role} userName={userName} />
      <DeactivateUserDialog userName={userName} disabled={!enabled} />
    </div>
  );
}
