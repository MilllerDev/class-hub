package com.bailon.backend.dashboard.application.service;

import com.bailon.backend.courses.application.port.CourseRepository;
import com.bailon.backend.dashboard.application.usecase.DashboardUseCase;
import com.bailon.backend.dashboard.domain.model.Dashboard;
import com.bailon.backend.profile.application.port.ProfileRepository;
import com.bailon.backend.users.application.port.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class DashboardService implements DashboardUseCase {
    private final UserRepository userRepository;
    private final CourseRepository courseRepository;
    private final ProfileRepository profileRepository;

    public DashboardService(UserRepository userRepository, CourseRepository courseRepository, ProfileRepository profileRepository) {
        this.userRepository = userRepository;
        this.courseRepository = courseRepository;
        this.profileRepository = profileRepository;
    }

    @Override
    public Dashboard execute() {
        return new Dashboard(
                userRepository.count(),
                courseRepository.count(),
                profileRepository.findLatest(),
                courseRepository.findLatest()
        );
    }
}
