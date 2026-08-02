import { getProfile } from "@/features/profiles/action";
import { ProfileCard } from "@/features/profiles/components/profile-card";
import { ProfileEditForm } from "@/features/profiles/components/profile-edit-form";
import { ProfileInfo } from "@/features/profiles/components/profile-info";
import { ProfileRolePanel } from "@/features/profiles/components/profile-role-panel";

export default async function ProfilePage() {
  const { data: profile } = await getProfile();

  return (
    <div className="mx-auto w-full max-w-5xl space-y-8 px-4 py-8">
      <section className="space-y-1">
        <h1 className="text-2xl font-bold tracking-tight text-foreground">
          Mi perfil
        </h1>
        <p className="text-sm text-muted-foreground">
          Consulta y administra la información de tu cuenta.
        </p>
      </section>

      <ProfileCard profile={profile} />

      <ProfileInfo profile={profile} />

      <ProfileRolePanel profile={profile} />

      <ProfileEditForm profile={profile} />
    </div>
  );
}
