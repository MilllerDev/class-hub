package com.bailon.backend.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@EnableAsync
@Configuration
public class AsyncConfig {
    private static final Logger log = LoggerFactory.getLogger(AsyncConfig.class);

    @Bean(name = "mailExecutor")
    public Executor mailExecutor() {
        return Executors.newVirtualThreadPerTaskExecutor();
    }

    @Bean(name = "aiService")
    public Executor aiService() {
        return Executors.newVirtualThreadPerTaskExecutor();
    }

    @Bean
    public AsyncUncaughtExceptionHandler asyncUncaughtExceptionHandler() {
        return (throwable, method, params) ->
                log.error("Error en método async {}: {}", method.getName(), throwable.getMessage(), throwable);
    }
}
