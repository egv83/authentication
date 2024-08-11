package com.ochobits.authentication.infrastructure.adapters.input;

public record LoginPostRequest(
        String email,
        String password
) {
}
