package com.ochobits.authentication.domain.ports;

import com.ochobits.authentication.domain.model.User;

public interface TokenGenerator {
    String generate(User user);
}
