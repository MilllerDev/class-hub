package com.bailon.backend.dashboard.domain.model;

import com.bailon.backend.courses.domain.model.Course;
import com.bailon.backend.profile.domain.model.Profile;

import java.util.List;

public record Dashboard(
        long userCount,
        long courseCount,
        List<Profile> latestProfiles,
        List<Course> latestCourses
) {
}
