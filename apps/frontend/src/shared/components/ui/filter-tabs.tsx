"use client";

import Link from "next/link";
import { usePathname, useSearchParams } from "next/navigation";
import { ItemParams } from "../types";

function FilterTabs({ items, param }: ItemParams) {
  const pathname = usePathname();
  const searchParams = useSearchParams();
  const params = new URLSearchParams(searchParams.toString());
  const current = params.get(param);

  const handleClik = (value: string) => {
    params.set(param, value);
    return `${value ? `${pathname}?${params}` : `${pathname}`}`;
  };

  return (
    <>
      {items.map(({ label, value }) => {
        const isActive = (current ?? "") === value;
        return (
          <Link
            key={label}
            href={handleClik(value)}
            aria-current={isActive ? "page" : undefined}
            className={`inline-block rounded-full border px-3 py-1 text-xs font-medium transition-colors ${
              isActive
                ? "border-primary bg-primary text-primary-foreground"
                : "border-border text-muted-foreground hover:bg-muted"
            }`}
          >
            {label}
          </Link>
        );
      })}
    </>
  );
}

export { FilterTabs };

