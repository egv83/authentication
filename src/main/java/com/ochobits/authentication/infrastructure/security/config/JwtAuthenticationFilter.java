package com.ochobits.authentication.infrastructure.security.config;

import com.ochobits.authentication.domain.ports.UserRepositoryPort;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.security.Key;
import java.util.Base64;
import java.util.Objects;
import java.util.UUID;
import java.util.function.Function;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final String jwtSecret;
    private final UserRepositoryPort userRepositoryPort;
    private final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    /*ASIGNO EN SECRET DEL PROPIERTIES A LA VARIABLE jwtSecret*/
    public JwtAuthenticationFilter(
            @Value("${app.security.jwt.secret}") String jwtSecret
            , UserRepositoryPort userRepositoryPort) {

        this.jwtSecret = jwtSecret;
        this.userRepositoryPort = userRepositoryPort;

    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain ) throws ServletException, IOException {
        /**
         * 1. Obtener la cabecera http
         * 2. si no esta presente, terminamos.
         * 3. Si el token no tiene el formato adecaduo, terminamos
         * 4. Obtener el token
         * 5. Extraer datos del token
         * 6. Crear UserDetails - necesitamos información del token
         * 7. Buscar al usuario para crear el userDetails
         * 8. Agregar el userDetails al contexto de Spring Security
         * */
        String tokenHeader = request.getHeader("Authorization");
        if(Objects.isNull(tokenHeader) || !tokenHeader.startsWith("Bearer ")){
            logger.warn("Token Authorization don't exists");
            filterChain.doFilter(request,response);
            return;
        }

        String jwtToken = tokenHeader.substring(7);
        Claims claims = Jwts
                .parserBuilder()
                .setSigningKey(getSigninKey())
                .build()
                .parseClaimsJws(jwtToken)
                .getBody();

        var userId = extractUserId(claims, Claims::getSubject);
        var userOpt = userRepositoryPort.findById(UUID.fromString(userId));
        if(userOpt.isEmpty()){
            logger.error("No existe el usuario propietario del token");
            filterChain.doFilter(request,response);
            return;
        }

        var user = userOpt.get();
        var userDetails = AppUserDetails.builder()
                .id(user.getId())
                .email(user.getEmail())
                .password(user.getPassword())
                .build();

        UsernamePasswordAuthenticationToken passwordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                userDetails,
                null,
                userDetails.getAuthorities()
        );

        SecurityContextHolder.getContext().setAuthentication(passwordAuthenticationToken);
        filterChain.doFilter(request,response);

    }

    private Key getSigninKey(){
        byte[] secretByte = Decoders.BASE64URL.decode(jwtSecret);
        return Keys.hmacShaKeyFor(secretByte);
    }

    private String extractUserId(Claims claims, Function<Claims,String> claimsResolved){
        return claimsResolved.apply(claims);
    }

}
