import { ChatHistory } from "@/features/chat/components/chat-history";
import { ChatHistorySkeleton } from "@/features/chat/components/chat-history-skeleton";
import { ChatLayoutSkeleton } from "@/features/chat/components/chat-layout-skeleton";
import { InputSendMessage } from "@/features/chat/components/input-send-message";
import { ListResourcesServer } from "@/features/chat/components/list-resources-server";
import { ArrowLeft } from "lucide-react";
import Link from "next/link";
import { Suspense } from "react";

export default async function StudentTopicDetailPage({
  params,
}: {
  params: Promise<{ slug: string; topicId: string }>;
}) {
  const { slug, topicId } = await params;

  return (
    <div className="flex h-[calc(100svh-3.5rem)] flex-col overflow-hidden">
      <header className="shrink-0 border-b border-border px-4 py-4">
        <Link
          href={`/student/courses/${slug}`}
          className="inline-flex items-center gap-1.5 text-sm text-muted-foreground hover:text-foreground transition-colors"
        >
          <ArrowLeft className="size-4" />
          Volver al curso
        </Link>
      </header>

      <div className="flex min-h-0 flex-1">
        <Suspense fallback={<ChatLayoutSkeleton />}>
          <ListResourcesServer topicId={topicId}>
            <div className="flex min-h-0 flex-1 flex-col">
              <main className="min-h-0 flex-1 overflow-y-auto px-4 py-4">
                <Suspense fallback={<ChatHistorySkeleton />}>
                  <ChatHistory topicId={topicId} />
                </Suspense>
              </main>
              <InputSendMessage topicId={topicId} />
            </div>
          </ListResourcesServer>
        </Suspense>
      </div>
    </div>
  );
}
