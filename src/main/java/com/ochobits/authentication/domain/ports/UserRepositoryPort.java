package com.ochobits.authentication.domain.ports;

import com.ochobits.authentication.domain.model.User;

import java.util.Optional;
import java.util.UUID;

public interface UserRepositoryPort {
    Optional<User> findById(UUID id);
    Optional<User> findUserByEmail(String email);
    void save(User user);
}
