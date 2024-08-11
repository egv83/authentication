package com.ochobits.authentication.application.input;

import jakarta.persistence.Column;

public record SignUpUserRequest(
        String name,
        String email,
        String password
) {
}
