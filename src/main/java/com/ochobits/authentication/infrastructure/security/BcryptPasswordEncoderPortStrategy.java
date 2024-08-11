package com.ochobits.authentication.infrastructure.security;

import com.ochobits.authentication.domain.ports.PasswordEncoderPort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class BcryptPasswordEncoderPortStrategy implements PasswordEncoderPort {

    private final BCryptPasswordEncoder encoder;

    public BcryptPasswordEncoderPortStrategy() {
        this.encoder = new BCryptPasswordEncoder();
    }

    @Override
    public String encode(String password) {
        return encoder.encode(password);
    }

    @Override
    public boolean match(String requesPassword, String responsePassword) {
        return encoder.matches(requesPassword,responsePassword);
    }
}
