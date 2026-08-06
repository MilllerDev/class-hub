import { Course } from "../courses/types";
import { Profile } from "../profiles/types";

export type Dashboard = {
  courseCount: number;
  userCount: number;
  latestProfiles: Profile[];
  latestCourses: Course[];
};
