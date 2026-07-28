"use client";

import { toast } from "@/shared/components/ui/toast";
import { LoaderCircle } from "lucide-react";
import { useRouter } from "next/navigation";
import { useEffect } from "react";
import { activateAccount } from "../action";

export function TokenActivator({ token }: { token: string }) {
  const router = useRouter();
  useEffect(() => {
    async function activate() {
      const { data, message, success } = await activateAccount(token);
      toast.add({ description: message });
      if (success) {
        router.replace(`/${data.role.toLowerCase()}`);
        return;
      }
      router.replace("/login");
    }
    activate();
  }, [router, token]);

  return (
    <div className="flex flex-col items-center gap-4">
      <LoaderCircle className="size-8 animate-spin text-primary" />
      <p className="text-sm text-muted-foreground">Activando tu cuenta...</p>
    </div>
  );
}
