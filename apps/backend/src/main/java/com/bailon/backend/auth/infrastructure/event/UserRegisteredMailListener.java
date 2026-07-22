package com.bailon.backend.auth.infrastructure.event;

import com.bailon.backend.auth.domain.event.UserRegisteredEvent;
import com.bailon.backend.config.mail.MailBody;
import com.bailon.backend.config.mail.MailSenderPort;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.Map;

@Component
public class UserRegisteredMailListener {
    private final MailSenderPort senderPort;

    public UserRegisteredMailListener(MailSenderPort senderPort) {
        this.senderPort = senderPort;
    }

    @Async("mailExecutor")
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void onUserRegistered(UserRegisteredEvent event) {
        senderPort.sendEmail(
                new MailBody(
                        event.email(),
                        "Activa tu cuenta",
                        "active-account.html",
                        Map.of("token", event.activationToken())
                )
        );
    }
}
