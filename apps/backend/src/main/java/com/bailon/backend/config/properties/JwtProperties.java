package com.bailon.backend.config.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "jwt")
public class JwtProperties {
    private String accessSecret;
    private String refreshSecret;
    private String activationSecret;
    private long accessExpiration;
    private long refreshExpiration;
    private long activationExpiration;
}
