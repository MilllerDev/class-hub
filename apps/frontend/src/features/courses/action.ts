"use server";

import { getCoursesForAdmin, getCoursesForStudent as getCoursesForStudentQuery } from "@/graphql/queries/courses";
import { Course as CourseQl } from "@/graphql/types";
import { api } from "@/lib/api";
import { graphql } from "@/lib/graphql";
import { revalidatePath } from "next/cache";
import { Course, CourseRequest } from "./types";

async function getCourses() {
  return await graphql.query<{ courses: CourseQl[] }>(getCoursesForAdmin);
}

async function getCoursesForStudent() {
  return await graphql.query<{ courses: CourseQl[] }>(getCoursesForStudentQuery);
}

async function getCourse(idOrSlug: string) {
  const res = await api.get<Course>(`/courses/${idOrSlug}`);
  return res;
}

async function createCourse(request: CourseRequest) {
  const res = await api.post<Course>("/courses", request);
  if (res.success) {
    revalidatePath("/courses");
  }
  return res;
}

async function updateCourse(id: string, request: CourseRequest) {
  const res = await api.patch<Course>(`/courses/${id}`, request);
  if (res.success) {
    revalidatePath("/courses");
  }
  return res;
}

async function upsertCourse(request: CourseRequest, id?: string) {
  return id ? await updateCourse(id, request) : await createCourse(request);
}

async function deleteCourse(id: string) {
  const res = await api.delete<Course>(`/courses/${id}`);
  if (res.success) {
    revalidatePath("/courses");
  }
  return res;
}

export {
  createCourse,
  deleteCourse,
  getCourse,
  getCourses,
  getCoursesForStudent,
  updateCourse,
  upsertCourse
};

