import { Skeleton } from "@/shared/components/ui/skeleton";
import { ChevronRight, ListTree } from "lucide-react";
import Link from "next/link";
import { getTopics } from "../actions";

export async function TopicList({ slug }: { slug: string }) {
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
        <Link
          key={topic.id}
          href={`/student/courses/${slug}/${topic.id}`}
          className="flex items-center gap-2.5 rounded-xl border border-border bg-card p-4 transition-colors hover:bg-accent/50"
        >
          <ListTree className="size-4 shrink-0 text-muted-foreground" />
          <p className="flex-1 truncate text-sm font-medium text-card-foreground">
            {topic.title}
          </p>
          <ChevronRight className="size-4 shrink-0 text-muted-foreground" />
        </Link>
      ))}
    </div>
  );
}

export function TopicListSkeleton() {
  return (
    <div className="space-y-2">
      {Array.from({ length: 3 }).map((_, i) => (
        <Skeleton key={i} className="h-12 w-full rounded-xl" />
      ))}
    </div>
  );
}
