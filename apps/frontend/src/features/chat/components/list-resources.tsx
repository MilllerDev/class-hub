"use client";

import { ResourceType } from "@/features/topics/types";
import {
  CheckCircle2,
  Download,
  File,
  FileText,
  Paperclip,
  Presentation,
} from "lucide-react";
import { useResources } from "./resources-provider";

export function ListResources() {
  const { allToggle, resources, checked, toggle } = useResources();
  const allChecked = resources.length > 0 && checked.size === resources.length;

  return (
    <aside className="hidden w-64 shrink-0 flex-col border-r border-border bg-muted/30 md:flex">
      <div className="flex items-center justify-between border-b border-border px-4 py-2.5">
        <p className="text-xs font-medium text-muted-foreground">Recursos</p>
        {resources.length > 0 && (
          <button
            onClick={allToggle}
            className="flex items-center gap-1 text-[11px] text-muted-foreground transition-colors hover:text-foreground"
          >
            <CheckCircle2
              className={`size-3 ${allChecked ? "text-green-500" : ""}`}
            />
            {allChecked ? "Desmarcar" : "Todo"}
          </button>
        )}
      </div>

      <div className="flex-1 overflow-y-auto px-3 py-2">
        {resources.length > 0 ? (
          <div className="space-y-1">
            {resources.map((resource) => {
              const isChecked = checked.has(resource.id);
              return (
                <div
                  key={resource.id}
                  className={`flex items-center gap-2 rounded-md px-2 py-1.5 transition-colors ${
                    isChecked
                      ? "bg-green-100 dark:bg-green-950/30"
                      : "hover:bg-accent/50"
                  }`}
                >
                  <input
                    type="checkbox"
                    checked={isChecked}
                    onChange={() => toggle(resource.id)}
                    className="size-3 shrink-0 cursor-pointer accent-green-600"
                  />
                  {getFileIcon(resource.fileType)}
                  <div className="min-w-0 flex-1">
                    <p className="truncate text-xs font-medium text-foreground">
                      {resource.fileName}
                    </p>
                  </div>
                  <a
                    href={resource.fileUrl}
                    target="_blank"
                    rel="noopener noreferrer"
                    className="shrink-0 rounded p-0.5 text-muted-foreground hover:text-foreground"
                  >
                    <Download className="size-3" />
                  </a>
                </div>
              );
            })}
          </div>
        ) : (
          <div className="flex flex-col items-center py-8 text-center">
            <Paperclip className="mb-2 size-6 text-muted-foreground/50" />
            <p className="text-xs text-muted-foreground">Sin recursos aún</p>
          </div>
        )}
      </div>
    </aside>
  );
}

function getFileIcon(type: ResourceType) {
  switch (type) {
    case ResourceType.PDF:
      return <FileText className="size-3.5 text-red-500" />;
    case ResourceType.DOCX:
      return <File className="size-3.5 text-blue-500" />;
    case ResourceType.PPTX:
      return <Presentation className="size-3.5 text-orange-500" />;
  }
}
