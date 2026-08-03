package com.bailon.backend.token.infrastructure.jwt;

import com.bailon.backend.config.properties.JwtProperties;
import com.bailon.backend.token.application.port.JwtPort;
import com.bailon.backend.token.domain.model.JwtData;
import com.bailon.backend.users.domain.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.util.Base64;
import java.util.Date;
import java.util.Optional;

@Service
public class JwtService implements JwtPort {
    private final JwtProperties jwtProperties;

    public JwtService(JwtProperties jwtProperties) {
        this.jwtProperties = jwtProperties;
    }

    @Override
    public JwtData generateJwtAccess(User user) {
        Instant now = Instant.now();
        Instant expiration = now.plusSeconds(jwtProperties.getAccessExpiration());
        String jwt = generateJwt(user, jwtProperties.getAccessSecret(), now, expiration);
        return new JwtData(jwt, expiration);
    }

    @Override
    public JwtData generateJwtRefresh(User user) {
        Instant now = Instant.now();
        Instant expiration = now.plusSeconds(jwtProperties.getRefreshExpiration());
        String jwt = generateJwt(user, jwtProperties.getRefreshSecret(), now, expiration);
        return new JwtData(jwt, expiration);
    }

    @Override
    public Optional<String> subjectFromValidAccessToken(String token) {
        return subjectFromValidToken(token, jwtProperties.getAccessSecret());
    }

    @Override
    public Optional<String> subjectFromValidRefreshToken(String token) {
        return subjectFromValidToken(token, jwtProperties.getRefreshSecret());
    }

    private Optional<String> subjectFromValidToken(String token, String secret) {
        try {
            return Optional.ofNullable(parseToken(token, secret).getSubject());
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    private Claims parseToken(String token, String secret) {
        SecretKey key = getKeyFromSecret(secret);
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private String generateJwt(User user, String secret, Instant now, Instant expiration) {
        return Jwts.builder()
                .subject(user.getEmail())
                .claim("role", user.getRole().name())
                .issuedAt(Date.from(now))
                .expiration(Date.from(expiration))
                .signWith(getKeyFromSecret(secret))
                .compact();
    }

    private SecretKey getKeyFromSecret(String secret) {
        byte[] keyBytes = Base64.getDecoder().decode(secret);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
