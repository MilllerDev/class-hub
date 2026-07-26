"use client";

import { useEffect } from "react";
import { LoaderCircle } from "lucide-react";
import { activateAccount } from "../action";

export function TokenActivator({ token }: { token: string }) {
  useEffect(() => {
    activateAccount(token);
  }, [token]);

  return (
    <div className="flex flex-col items-center gap-4">
      <LoaderCircle className="size-8 animate-spin text-primary" />
      <p className="text-sm text-muted-foreground">Activando tu cuenta...</p>
    </div>
  );
}
