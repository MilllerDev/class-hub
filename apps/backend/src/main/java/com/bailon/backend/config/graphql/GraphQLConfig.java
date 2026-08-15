package com.bailon.backend.config.graphql;

import com.bailon.backend.profile.domain.model.Profile;
import com.bailon.backend.profile.infrastructure.graphql.ProfileBatchLoader;
import org.springframework.context.annotation.Configuration;
import org.springframework.graphql.execution.BatchLoaderRegistry;

import java.util.UUID;

@Configuration
public class GraphQLConfig {

    public GraphQLConfig(BatchLoaderRegistry registry, ProfileBatchLoader profileDataLoader) {
        registry.forTypePair(UUID.class, Profile.class)
                .registerMappedBatchLoader(
                        (userIds, environment) -> profileDataLoader.load(userIds)
                );
    }
}