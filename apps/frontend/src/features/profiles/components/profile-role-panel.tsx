import type { Profile } from "@/features/profiles/types";
import { AdminProfilePanel } from "./admin-profile-panel";
import { StudentProfilePanel } from "./student-profile-panel";
import { TeacherProfilePanel } from "./teacher-profile-panel";

export function ProfileRolePanel({ profile }: { profile: Profile }) {
  switch (profile.user.role.toLowerCase()) {
    case "admin":
      return <AdminProfilePanel />;
    case "teacher":
      return <TeacherProfilePanel />;
    case "student":
      return <StudentProfilePanel />;
    default:
      return null;
  }
}
