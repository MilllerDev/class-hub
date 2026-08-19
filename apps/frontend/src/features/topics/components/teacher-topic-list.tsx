import { Skeleton } from "@/shared/components/ui/skeleton";
import { ListTree } from "lucide-react";
import { getTopics } from "../action";
import { TopicDeleteDialog } from "./topic-delete-dialog";
import { TopicFormDialog } from "./topic-form-dialog";

export async function TeacherTopicList({
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
    <div className="space-y-2">
      {topics.map((topic) => (
        <div
          key={topic.id}
          className="flex items-center justify-between gap-2.5 rounded-xl border border-border bg-card p-4"
        >
          <div className="flex min-w-0 items-center gap-2.5">
            <ListTree className="size-4 shrink-0 text-muted-foreground" />
            <p className="truncate text-sm font-medium text-card-foreground">
              {topic.title}
            </p>
          </div>
          <div className="flex shrink-0 items-center gap-0.5">
            <TopicFormDialog courseId={courseId} topic={topic} />
            <TopicDeleteDialog id={topic.id} title={topic.title} />
          </div>
        </div>
      ))}
    </div>
  );
}

export function TeacherTopicListSkeleton() {
  return (
    <div className="space-y-2">
      {Array.from({ length: 3 }).map((_, i) => (
        <Skeleton key={i} className="h-12 w-full rounded-xl" />
      ))}
    </div>
  );
}