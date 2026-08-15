"use client";

import { ChevronLeft, ChevronRight } from "lucide-react";
import Link from "next/link";
import { usePathname, useSearchParams } from "next/navigation";
import { PaginationProps } from "../types";
import { Button } from "./button";

export function Pagination(props: PaginationProps) {
  const { currentPage, totalPages, last } = props;
  const searchParams = useSearchParams();
  const pathname = usePathname();

  const handlePage = (page: number) => {
    const params = new URLSearchParams(searchParams.toString());
    params.set("page", String(page));
    return `${pathname}?${params.toString()}`;
  };

  return (
    <div className="flex items-center justify-between">
      <p className="text-xs text-muted-foreground">
        Página {currentPage + 1} de {totalPages}
      </p>
      <div className="flex items-center gap-1.5">
        <Button variant={"outline"} size={"sm"} disabled={currentPage === 0}>
          <Link
            href={handlePage(currentPage - 1)}
            aria-disabled={currentPage === 0}
            className="flex flex-row gap-1 items-center"
          >
            <ChevronLeft className="size-3.5" />
            Anterior
          </Link>
        </Button>
        <Button variant={"outline"} size={"sm"} disabled={last}>
          <Link
            href={handlePage(currentPage + 1)}
            aria-disabled={last}
            className="flex flex-row gap-1 items-center"
          >
            Siguiente
            <ChevronRight className="size-3.5" />
          </Link>
        </Button>
      </div>
    </div>
  );
}
