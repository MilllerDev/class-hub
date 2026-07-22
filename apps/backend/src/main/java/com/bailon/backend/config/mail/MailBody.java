package com.bailon.backend.config.mail;

import java.util.Map;

public record MailBody(
        String to,
        String subject,
        String template,
        Map<String, Object> variables
) {
}
