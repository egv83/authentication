package com.ochobits.authentication.application.input;

public record LoginUserRequest(
        String email,
        String password
) {
}
