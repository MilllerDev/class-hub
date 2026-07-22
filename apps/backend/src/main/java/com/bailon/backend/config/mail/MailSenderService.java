package com.bailon.backend.config.mail;

import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.MailSendException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
public class MailSenderService implements MailSenderPort {
    private final JavaMailSender mailSender;
    private final TemplateEngine templateEngine;

    public MailSenderService(JavaMailSender mailSender, TemplateEngine templateEngine) {
        this.mailSender = mailSender;
        this.templateEngine = templateEngine;
    }

    @Async("mailExecutor")
    @Override
    public void sendEmail(MailBody body) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setFrom("bailonalvarezmiller12@gmail.com");
            helper.setTo(body.to());
            helper.setSubject(body.subject());

            Context context = new Context();
            body.variables().forEach(context::setVariable);
            String html = templateEngine.process(body.template(), context);
            helper.setText(html, true);
            mailSender.send(message);
        } catch (Exception E) {
            throw new MailSendException("Error al enviar el mail", E);
        }
    }
}
