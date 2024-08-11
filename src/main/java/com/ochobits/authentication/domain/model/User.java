package com.ochobits.authentication.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder(toBuilder = true)
public class User {

    private UUID id;
    private String name;
    private String email;
    private String password;
    private String status;

    /* SE CREA UNA LOGICA */
}
