package com.bailon.backend.auth.infrastructure.listener;

import com.bailon.backend.auth.domain.event.UserRegisteredEvent;
import com.bailon.backend.config.mail.MailBody;
import com.bailon.backend.config.mail.MailSenderPort;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.Map;

@Component
public class UserRegisteredListener {
    private final MailSenderPort senderPort;
    @Value("${app.frontend-url}")
    private String frontendUrl;

    public UserRegisteredListener(MailSenderPort senderPort) {
        this.senderPort = senderPort;
    }

    @Async("mailExecutor")
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void onUserRegistered(UserRegisteredEvent event) {
        String verificationUrl = frontendUrl + "/activate-account?token=" + event.activationToken();
        senderPort.sendEmail(
                new MailBody(
                        event.email(),
                        "Activa tu cuenta",
                        "active-account.html",
                        Map.of("verificationUrl", verificationUrl, "name", event.name())
                )
        );
    }
}
