import { getCourse } from "@/features/courses/action";
import { CourseTopicsPage } from "@/shared/components/course-topics-page";
import { notFound } from "next/navigation";

export default async function AdminCourseDetailPage({
  params,
}: {
  params: Promise<{ slug: string }>;
}) {
  const { slug } = await params;
  const res = await getCourse(slug);

  if (!res.success) {
    notFound();
  }

  return <CourseTopicsPage course={res.data} backHref="/admin/courses" />;
}
