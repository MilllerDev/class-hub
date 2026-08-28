"use client";

import { useEffect, useRef } from "react";

export function ChatAutoScroll({ messageCount }: { messageCount: number }) {
  const sentinelRef = useRef<HTMLDivElement>(null);

  useEffect(() => {
    sentinelRef.current?.scrollIntoView({ block: "end" });
  }, [messageCount]);

  return <div ref={sentinelRef} aria-hidden />;
}