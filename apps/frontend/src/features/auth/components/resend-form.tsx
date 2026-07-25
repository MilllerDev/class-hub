"use client";

import { resendActivation } from "../action";

export function ResendForm({
  email,
  name,
}: {
  email: string;
  name: string;
}) {
  async function handleResend() {
    await resendActivation(email, name);
  }

  return (
    <form action={handleResend}>
      <button
        type="submit"
        className="font-medium text-foreground hover:underline"
      >
        Reenviar
      </button>
    </form>
  );
}
