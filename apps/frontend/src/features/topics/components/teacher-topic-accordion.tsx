import { TopicResourceUpload } from "@/features/topic-resources/components/topic-resource-upload";
import { TopicResources } from "@/features/topic-resources/components/topic-resources";
import {
  Accordion,
  AccordionContent,
  AccordionItem,
  AccordionTrigger,
} from "@/shared/components/ui/accordion";
import { Skeleton } from "@/shared/components/ui/skeleton";
import { ListTree } from "lucide-react";
import { getTopics } from "../actions";
import { TopicDeleteDialog } from "./topic-delete-dialog";
import { TopicFormDialog } from "./topic-form-dialog";

async function TeacherTopicAccordion({
  slug,
  courseId,
}: {
  slug: string;
  courseId: string;
}) {
  const { data: topics } = await getTopics(slug);

  if (topics.length === 0) {
    return (
      <div className="rounded-xl border border-dashed border-border p-6 text-center text-sm text-muted-foreground">
        Aún no hay temas publicados para este curso.
      </div>
    );
  }

  return (
    <Accordion className="w-full rounded-xl border">
      {topics.map((topic) => (
        <AccordionItem
          key={topic.id}
          value={topic.id}
          className={"border-b last:border-b-0 p-2.5"}
        >
          <div className="flex items-center w-full justify-between">
            <AccordionTrigger className="min-w-0 hover:no-underline items-center px-1">
              <div className="flex w-full items-center gap-2.5">
                <ListTree className="size-4 shrink-0 text-muted-foreground" />
                <p className="truncate text-sm font-medium text-card-foreground">
                  {topic.title}
                </p>
              </div>
            </AccordionTrigger>
            <div className="flex shrink-0 items-center gap-0.5">
              <TopicFormDialog courseId={courseId} topic={topic} />
              <TopicDeleteDialog id={topic.id} title={topic.title} />
            </div>
          </div>
          <AccordionContent className="px-4">
            <div className="space-y-3">
              <TopicResources topicId={topic.id} />
              <TopicResourceUpload key={topic.id} topicId={topic.id} />
            </div>
          </AccordionContent>
        </AccordionItem>
      ))}
    </Accordion>
  );
}

function TeacherTopicAccordionSkeleton() {
  return (
    <div className="w-full rounded-xl border">
      {Array.from({ length: 3 }).map((_, i) => (
        <div key={i} className="border-b p-2.5 last:border-b-0">
          <div className="flex items-center justify-between gap-2 px-1">
            <div className="flex min-w-0 items-center gap-2.5">
              <Skeleton className="size-4 shrink-0 rounded-full" />
              <Skeleton className="h-4 w-40" />
            </div>
            <div className="flex shrink-0 items-center gap-0.5">
              <Skeleton className="size-7 rounded-[min(var(--radius-md),12px)]" />
              <Skeleton className="size-7 rounded-[min(var(--radius-md),12px)]" />
            </div>
          </div>
          <div className="px-3 py-3">
            <div className="flex flex-wrap gap-2">
              <Skeleton className="h-6 w-24 rounded-full" />
              <Skeleton className="h-6 w-32 rounded-full" />
            </div>
            <Skeleton className="mt-3 h-7 w-28 rounded-[min(var(--radius-md),12px)]" />
          </div>
        </div>
      ))}
    </div>
  );
}

export { TeacherTopicAccordion, TeacherTopicAccordionSkeleton };

