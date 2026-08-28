import { File, FileText, Presentation } from "lucide-react";
import { getTopicResources } from "../action";
import { ResourceType } from "../types";

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

function getPillStyles(type: ResourceType) {
  switch (type) {
    case ResourceType.PDF:
      return "bg-red-50 text-red-700 border-red-200 hover:bg-red-100 dark:bg-red-950/30 dark:text-red-400 dark:border-red-800";
    case ResourceType.DOCX:
      return "bg-blue-50 text-blue-700 border-blue-200 hover:bg-blue-100 dark:bg-blue-950/30 dark:text-blue-400 dark:border-blue-800";
    case ResourceType.PPTX:
      return "bg-orange-50 text-orange-700 border-orange-200 hover:bg-orange-100 dark:bg-orange-950/30 dark:text-orange-400 dark:border-orange-800";
  }
}

async function TopicResources({ topicId }: { topicId: string }) {
  const { data: resources } = await getTopicResources(topicId);

  if (resources.length === 0) return null;

  return (
    <div className="flex flex-wrap gap-2">
      {resources.map(({ id, fileName, fileUrl, fileType }) => (
        <a
          key={id}
          href={fileUrl}
          target="_blank"
          rel="noopener noreferrer"
          className={`inline-flex items-center gap-1.5 rounded-full border px-2.5 py-1 text-xs font-medium transition-colors ${getPillStyles(fileType)}`}
        >
          {getFileIcon(fileType)}
          {fileName}
        </a>
      ))}
    </div>
  );
}

export { TopicResources };
