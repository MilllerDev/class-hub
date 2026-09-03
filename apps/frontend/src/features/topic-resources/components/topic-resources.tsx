import { File, FileText, Presentation } from "lucide-react";
import { getTopicResources } from "../actions";
import { ResourceType } from "../types";
import { ResourceDeleteDialog } from "./resource-delete-dialog";

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
        <div
          key={id}
          className={`inline-flex items-center gap-1 rounded-full border py-1 pl-2.5 pr-1 text-xs font-medium transition-colors ${getPillStyles(fileType)}`}
        >
          <a
            href={fileUrl}
            target="_blank"
            rel="noopener noreferrer"
            className="inline-flex min-w-0 items-center gap-1.5"
          >
            {getFileIcon(fileType)}
            <span className="truncate">{fileName}</span>
          </a>
          <ResourceDeleteDialog topicId={topicId} id={id} fileName={fileName} />
        </div>
      ))}
    </div>
  );
}

export { TopicResources };

