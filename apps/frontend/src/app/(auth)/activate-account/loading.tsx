import { LoaderCircle } from "lucide-react";

export default function Loading() {
  return (
    <div className="flex flex-col items-center gap-4">
      <LoaderCircle className="size-8 animate-spin text-primary" />
      <p className="text-sm text-muted-foreground">Activando tu cuenta...</p>
    </div>
  );
}
