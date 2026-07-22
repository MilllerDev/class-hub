package com.bailon.backend.config.mail;


public interface MailSenderPort {
    void sendEmail(MailBody body);
}
