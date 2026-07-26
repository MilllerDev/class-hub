import { ResendForm } from "@/features/auth/components/resend-form";
import { TokenActivator } from "@/features/auth/components/token-activator";
import { Mail } from "lucide-react";
import { redirect } from "next/navigation";

export default async function ActivateAccountPage({
  searchParams,
}: {
  searchParams: Promise<{ token?: string; email?: string }>;
}) {
  const { token, email } = await searchParams;
  if (email && token) {
    redirect("/");
  }

  if (!email && !token) {
    redirect("/");
  }

  if (token) {
    return (
      <div className="w-full max-w-sm space-y-6 text-center">
        <TokenActivator token={token} />
      </div>
    );
  }

  return (
    email && (
      <div className="w-full max-w-sm space-y-6 text-center">
        <div className="mx-auto flex size-14 items-center justify-center rounded-2xl bg-primary/10 text-primary">
          <Mail size={28} />
        </div>

        <div className="space-y-2">
          <h1 className="text-2xl font-bold tracking-tight text-foreground">
            Revisa tu correo
          </h1>
          <p className="text-sm text-muted-foreground">
            Te enviamos un enlace de activación a{" "}
            <span className="font-medium text-foreground">{email}</span>. Haz
            clic en el enlace para activar tu cuenta.
          </p>
        </div>

        <ResendForm email={email} />
      </div>
    )
  );
}
