package com.bailon.backend.auth.application.usecase;

public interface ResendActivationUseCase {
    void execute(String email);
}
