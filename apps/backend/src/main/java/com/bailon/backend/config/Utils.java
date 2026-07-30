package com.bailon.backend.config;

import org.springframework.stereotype.Component;

import java.text.Normalizer;

@Component
public final class Utils {
    public static String generateSlug(String name) {
        String normalized = Normalizer.normalize(name, Normalizer.Form.NFD);
        return normalized
                .toLowerCase()
                .replaceAll("\\p{M}", "")
                .replaceAll("[^a-z0-9]+", "-")
                .replaceAll("^-+|-+$", "");
    }
}
