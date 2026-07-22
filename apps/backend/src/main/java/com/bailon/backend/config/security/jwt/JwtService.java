package com.bailon.backend.config.security.jwt;

import com.bailon.backend.config.properties.JwtProperties;
import com.bailon.backend.users.domain.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.util.Base64;
import java.util.Date;

@Service
public class JwtService implements JwtPort {
    private final JwtProperties jwtProperties;

    public JwtService(JwtProperties jwtProperties) {
        this.jwtProperties = jwtProperties;
    }

    @Override
    public boolean validateAccessToken(String token, String email) {
        return validate(token, jwtProperties.getAccessSecret(), email);
    }

    @Override
    public boolean validateRefreshToken(String token, String email) {
        return validate(token, jwtProperties.getRefreshSecret(), email);
    }

    @Override
    public String getEmailFromAccessToken(String token) {
        return extractEmail(token, jwtProperties.getAccessSecret());
    }

    @Override
    public String getEmailFromRefreshToken(String token) {
        return extractEmail(token, jwtProperties.getRefreshSecret());
    }

    @Override
    public boolean validateActivationToken(String token) {
        return validate(token, jwtProperties.getActivationSecret());
    }

    @Override
    public String getEmailFromActivationToken(String token) {
        return extractEmail(token, jwtProperties.getActivationSecret());
    }

    private Claims parseToken(String token, String secret) {
        SecretKey key = getKeyFromSecret(secret);
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private boolean validate(String token, String secret) {
        try {
            parseToken(token, secret);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private boolean validate(String token, String secret, String email) {
        try {
            Claims claims = parseToken(token, secret);
            return claims.getSubject().equals(email);
        } catch (Exception e) {
            return false;
        }
    }

    private String extractEmail(String token, String secret) {
        return parseToken(token, secret).getSubject();
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

    @Override
    public JwtData generateJwtAccess(User user) {
        Instant now = Instant.now();
        Instant expiration = now.plusSeconds(jwtProperties.getAccessExpiration());
        String jwt = generateJwt(user, jwtProperties.getAccessSecret(), now, expiration);
        return new JwtData(jwt, expiration);
    }

    @Override
    public String generateJwtActivation(User user) {
        Instant now = Instant.now();
        Instant expiration = now.plusSeconds(jwtProperties.getRefreshExpiration());
        return generateJwt(user, jwtProperties.getAccessSecret(), now, expiration);
    }

    @Override
    public JwtData generateJwtRefresh(User user) {
        Instant now = Instant.now();
        Instant expiration = now.plusSeconds(jwtProperties.getRefreshExpiration());
        String jwt = generateJwt(user, jwtProperties.getRefreshSecret(), now, expiration);
        return new JwtData(jwt, expiration);
    }

    private SecretKey getKeyFromSecret(String secret) {
        byte[] keyBytes = Base64.getDecoder().decode(secret);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
