package com.ochobits.authentication.infrastructure.adapters.input;

public record SignUpPostRequest(
        String name,
        String email,
        String password
) {
}
