package com.ochobits.authentication.domain.ports;

public interface PasswordEncoderPort {
    String encode(String password);
    boolean match(String requesPassword, String responsePassword);
}
