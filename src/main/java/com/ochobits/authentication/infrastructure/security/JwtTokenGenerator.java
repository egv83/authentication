package com.ochobits.authentication.infrastructure.security;

import com.ochobits.authentication.domain.model.User;
import com.ochobits.authentication.domain.ports.TokenGenerator;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;

@Service
public class JwtTokenGenerator implements TokenGenerator {

    private final String jwtSecret;
    private final Long jwtExpiration;

    /*
    * SE ASIGNAN LOS VALORES DE LA VARIABLES DEFINIDAS EN EL PROPERTIES
    * */
    public JwtTokenGenerator(@Value("${app.security.jwt.secret}") String jwtSecret,
                             @Value("${app.security.jwt.expiration}") Long jwtExpiration) {
        this.jwtSecret = jwtSecret;
        this.jwtExpiration = jwtExpiration;
    }

    @Override
    public String generate(User user) {
        return Jwts.builder()
                .setSubject(user.getId().toString())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+jwtExpiration))
                .signWith(getSigninKey())
                .compact();
    }

    private Key getSigninKey(){
        byte[] secretBytes = Decoders.BASE64.decode(jwtSecret);
        return Keys.hmacShaKeyFor(secretBytes);
    }
}
