import { getTopicResources } from "@/features/topics/action";
import { notFound } from "next/navigation";
import { ReactNode } from "react";
import { ListResources } from "./list-resources";
import { ResourcesProvider } from "./resources-provider";

async function ListResourcesServer({
  topicId,
  children,
}: {
  topicId: string;
  children: ReactNode;
}) {
  const { data, success } = await getTopicResources(topicId);
  if (!success) {
    notFound();
  }
  return (
    <ResourcesProvider resources={data}>
      <ListResources />
      {children}
    </ResourcesProvider>
  );
}

export { ListResourcesServer };