import { BookOpen } from "lucide-react";
import Link from "next/link";
import { Suspense } from "react";
import {
  LastChatsList,
  LastChatsListSkeleton,
} from "@/features/dashboard/components/last-chats-list";

export default function StudentPage() {
  return (
    <div className="mx-auto w-full max-w-5xl space-y-8 px-4 py-8">
      <section>
        <h1 className="text-2xl font-bold tracking-tight text-foreground">
          Hola, bienvenido/a
        </h1>
        <p className="text-sm text-muted-foreground">
          Bienvenido de vuelta. Este es tu resumen del día.
        </p>
      </section>

      <section>
        <Link
          href="/student/courses"
          className="inline-flex items-center gap-2 rounded-xl border border-border bg-card px-4 py-3 text-sm font-medium text-card-foreground transition-colors hover:bg-accent"
        >
          <BookOpen className="size-4" />
          Ver todos mis cursos
        </Link>
      </section>

      <section className="space-y-4">
        <h2 className="text-lg font-semibold text-foreground">
          Últimos chats
        </h2>
        <Suspense fallback={<LastChatsListSkeleton />}>
          <LastChatsList />
        </Suspense>
      </section>
    </div>
  );
}
