"use server";

import { dashboard } from "@/graphql/queries/dashboard";
import { graphql } from "@/lib/graphql";
import { Dashboard } from "./types";

async function getDashboard() {
  const data = await graphql.query<{ dashboard: Dashboard }>(dashboard);
  return data.dashboard;
}

export { getDashboard };
