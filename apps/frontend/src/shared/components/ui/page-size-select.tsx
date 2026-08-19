"use client";

import {
  Select,
  SelectContent,
  SelectItem,
  SelectTrigger,
  SelectValue,
} from "@/shared/components/ui/select";
import { usePathname, useRouter, useSearchParams } from "next/navigation";
import { useEffect } from "react";

export const SIZE_OPTIONS = [5, 10, 15, 30, 50, 100] as const;

export function PageSizeSelect() {
  const router = useRouter();
  const pathname = usePathname();
  const searchParams = useSearchParams();
  const raw = searchParams.get("size");
  const parsed = Number(raw);
  const current =
    raw !== null && SIZE_OPTIONS.find((size) => size === parsed)
      ? parsed
      : undefined;

  useEffect(() => {
    if (raw !== null && current === undefined) {
      const params = new URLSearchParams(searchParams.toString());
      params.delete("size");
      const qs = params.toString();
      router.replace(qs ? `${pathname}?${qs}` : pathname);
    }
  }, [raw, current, pathname, router, searchParams]);

  function handleChange(value: string | null) {
    const params = new URLSearchParams(searchParams.toString());
    if (value) params.set("size", value);
    router.replace(`?${params}`);
  }

  return (
    <Select value={String(current || 10)} onValueChange={handleChange}>
      <SelectTrigger size="sm" aria-label="Tamaño de página">
        <SelectValue />
      </SelectTrigger>
      <SelectContent alignItemWithTrigger={false}>
        {SIZE_OPTIONS.map((n) => (
          <SelectItem key={n} value={String(n)}>
            {n} por página
          </SelectItem>
        ))}
      </SelectContent>
    </Select>
  );
}
