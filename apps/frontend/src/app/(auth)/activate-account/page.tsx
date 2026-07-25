import Link from "next/link";
import { Mail } from "lucide-react";
import { ResendForm } from "@/features/auth/components/resend-form";

export default async function ActivateAccountPage({
  searchParams,
}: {
  searchParams: Promise<{ token?: string; email?: string; name?: string }>;
}) {
  const { token, email, name } = await searchParams;

  return (
    <div className="w-full max-w-sm space-y-6 text-center">
      <div className="mx-auto flex size-14 items-center justify-center rounded-2xl bg-primary/10 text-primary">
        <Mail size={28} />
      </div>

      <div className="space-y-2">
        <h1 className="text-2xl font-bold tracking-tight text-foreground">
          Revisa tu correo
        </h1>
        <p className="text-sm text-muted-foreground">
          {name && (
            <>
              Hola <span className="font-medium text-foreground">{name}</span>,{" "}
            </>
          )}
          Te enviamos un enlace de activación a{" "}
          {email && (
            <span className="font-medium text-foreground">{email}</span>
          )}
          . Haz clic en el enlace para activar tu cuenta.
        </p>
      </div>

      {token && (
        <div className="space-y-3">
          <div className="rounded-lg border p-3 text-xs break-all font-mono text-muted-foreground">
            {`${process.env.NEXT_PUBLIC_APP_URL || "http://localhost:3000"}/activate-account?token=${token}`}
          </div>
          <Link
            href={`/activate-account?token=${token}`}
            className="inline-flex w-full items-center justify-center rounded-lg border border-border bg-background px-4 py-2 text-sm font-medium transition-colors hover:bg-muted"
          >
            Activar cuenta (dev)
          </Link>
        </div>
      )}

      <p className="text-sm text-muted-foreground">
        ¿No recibiste el correo?{" "}
        {email && name ? (
          <ResendForm email={email} name={name} />
        ) : (
          <button className="font-medium text-foreground hover:underline">
            Reenviar
          </button>
        )}
      </p>
    </div>
  );
}
