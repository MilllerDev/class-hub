"use client";

import { Input } from "@/shared/components/ui/input";
import { Search as S } from "lucide-react";
import { usePathname, useRouter, useSearchParams } from "next/navigation";
import { useState } from "react";

export function Search({ holder }: { holder: string }) {
  const { replace } = useRouter();
  const pathname = usePathname();
  const searchParams = useSearchParams();
  const [query, setQuery] = useState(() => searchParams.get("q") ?? "");

  const handleSearch = (query: string) => {
    setQuery(query);
    const params = new URLSearchParams(searchParams);
    if (query) {
      params.set("q", query);
      params.set("page", "1");
    } else {
      params.delete("q");
      params.delete("page");
    }
    replace(`${pathname}?${params.toString()}`);
  };

  return (
    <div className="relative w-full max-w-sm">
      <S className="pointer-events-none absolute top-1/2 left-2.5 size-4 -translate-y-1/2 text-muted-foreground" />
      <Input
        value={query}
        onChange={(event) => handleSearch(event.target.value)}
        placeholder={holder}
        aria-label="Buscar perfiles"
        className="pl-8"
      />
    </div>
  );
}
