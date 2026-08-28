"use client";

import { Button } from "@/shared/components/ui/button";
import { toast } from "@/shared/components/ui/toast";
import { Loader2, Sparkles, Upload } from "lucide-react";
import { useRef, useState } from "react";
import { proccesUploadFile } from "../action";

export function TopicResourceUpload({ topicId }: { topicId: string }) {
  const [uploading, setUploading] = useState(false);
  const [processing, setProcessing] = useState(false);
  const inputRef = useRef<HTMLInputElement>(null);

  const handleFileChange = async (e: React.ChangeEvent<HTMLInputElement>) => {
    const selected = e.target.files;
    if (!selected || selected.length === 0) return;

    setUploading(true);
    for (const file of Array.from(selected)) {
      const formData = new FormData();
      formData.append("file", file);

      const res = await fetch("/api/upload", {
        method: "POST",
        body: formData,
      });

      const result = await res.json();
      const request = {
        fileName: file.name,
        fileUrl: result.url,
        fileType: result.type,
      };
      setUploading(false);
      setProcessing(true);

      const response = await proccesUploadFile(topicId, request);

      setProcessing(false);

      if (response.success) {
        toast.add({
          type: "success",
          description: `"${file.name}" procesado correctamente.`,
        });
      } else {
        toast.add({
          type: "error",
          description: `Error al procesar "${file.name}".`,
        });
      }
    }

    if (inputRef.current) inputRef.current.value = "";
  };

  const isBusy = uploading || processing;

  return (
    <div className="space-y-3">
      <input
        ref={inputRef}
        type="file"
        accept=".pdf,.docx,.pptx"
        multiple
        className="hidden"
        onChange={handleFileChange}
      />
      <Button
        variant="outline"
        size="sm"
        disabled={isBusy}
        onClick={() => inputRef.current?.click()}
      >
        {processing ? (
          <Sparkles className="size-3.5" />
        ) : uploading ? (
          <Loader2 className="size-3.5 animate-spin" />
        ) : (
          <Upload className="size-3.5" />
        )}
        {processing
          ? "Procesando con IA..."
          : uploading
            ? "Subiendo..."
            : "Subir archivos"}
      </Button>
    </div>
  );
}
