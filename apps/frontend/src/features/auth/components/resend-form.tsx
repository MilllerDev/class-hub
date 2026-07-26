"use client";

import { CheckCircle } from "lucide-react";
import { useActionState } from "react";
import { resendActivation } from "../action";

type ResendState = { message: string; success: boolean } | null;

export function ResendForm({ email }: { email: string }) {
  const [state, formAction, isPending] = useActionState<ResendState, FormData>(
    async () => {
      return resendActivation(email);
    },
    null,
  );

  return (
    <div className="space-y-4">
      <form action={formAction}>
        <button
          type="submit"
          disabled={isPending}
          className="font-medium text-foreground hover:underline disabled:opacity-50"
        >
          {isPending ? "Reenviando..." : "Reenviar correo"}
        </button>
      </form>

      {state?.success && (
        <div className="flex items-center gap-2 rounded-lg border border-primary/50 bg-primary/10 p-3 text-sm text-primary">
          <CheckCircle size={16} />
          {state.message}
        </div>
      )}

      {state && !state.success && (
        <div className="rounded-lg border border-destructive/50 bg-destructive/10 p-3 text-sm text-destructive">
          {state.message}
        </div>
      )}
    </div>
  );
}
