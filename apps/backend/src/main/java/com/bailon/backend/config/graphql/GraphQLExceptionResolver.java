package com.bailon.backend.config.graphql;

import graphql.GraphQLError;
import graphql.schema.DataFetchingEnvironment;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.graphql.execution.DataFetcherExceptionResolverAdapter;
import org.springframework.graphql.execution.ErrorType;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class GraphQLExceptionResolver extends DataFetcherExceptionResolverAdapter {
    private static final Logger log = LoggerFactory.getLogger(GraphQLExceptionResolver.class);

    @Override
    protected @Nullable GraphQLError resolveToSingleError(@NonNull Throwable ex, @NonNull DataFetchingEnvironment env) {
        return super.resolveToSingleError(ex, env);
    }

    private ErrorType mapErrorType(HttpStatus status) {
        return switch (status) {
            case NOT_FOUND -> ErrorType.NOT_FOUND;
            case BAD_REQUEST -> ErrorType.BAD_REQUEST;
            case UNAUTHORIZED -> ErrorType.UNAUTHORIZED;
            case FORBIDDEN -> ErrorType.FORBIDDEN;
            default -> ErrorType.INTERNAL_ERROR;
        };
    }
}
