package com.bailon.backend.profile.infrastructure.graphql;

import com.bailon.backend.profile.application.port.ProfileRepository;
import com.bailon.backend.profile.domain.model.Profile;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class ProfileBatchLoader {
    private final ProfileRepository profileRepository;

    public ProfileBatchLoader(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    public Mono<Map<UUID, Profile>> load(Set<UUID> userIds) {
        return Mono.fromCallable(() -> profileRepository.findByUserIds(userIds))
                .map(profiles -> profiles.stream()
                        .collect(Collectors.toMap(profile -> profile.getUser().getId(), Function.identity())))
                .map(profilesByUser -> userIds.stream()
                        .collect(Collectors.toMap(Function.identity(), profilesByUser::get)));
    }
}