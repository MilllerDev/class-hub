"use client";

import { TopicResource } from "@/features/topic-resources/types";
import { createContext, ReactNode, useContext, useState } from "react";

type ResourcesContextProps = {
  checked: Set<string>;
  resources: TopicResource[];
  toggle: (id: string) => void;
  allToggle: () => void;
};

export const ResourcesContext = createContext<
  ResourcesContextProps | undefined
>(undefined);

export function ResourcesProvider({
  children,
  resources,
}: {
  children: ReactNode;
  resources: TopicResource[];
}) {
  const [checked, setChecked] = useState<Set<string>>(new Set());
  const toggle = (id: string) => {
    setChecked((prev) => {
      const next = new Set(prev);
      if (next.has(id)) {
        next.delete(id);
      } else {
        next.add(id);
      }
      return next;
    });
  };

  const allToggle = () => {
    if (checked.size === resources.length) {
      setChecked(new Set());
    } else {
      setChecked(new Set(resources.map((it) => it.id)));
    }
  };
  return (
    <ResourcesContext.Provider
      value={{ resources, checked, allToggle, toggle }}
    >
      {children}
    </ResourcesContext.Provider>
  );
}

export function useResources() {
  const context = useContext(ResourcesContext);
  if (!context) {
    throw new Error("El hook debería de estar dentro del ResourcesProvider");
  }
  return context;
}
