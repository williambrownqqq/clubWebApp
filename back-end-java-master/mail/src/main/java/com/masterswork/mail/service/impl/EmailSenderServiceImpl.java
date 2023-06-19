package com.masterswork.mail.service.impl;

import com.masterswork.mail.service.EmailSenderService;
import com.masterswork.mail.service.exception.FailedToSendEmailException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.nio.charset.StandardCharsets;

@Service
@Slf4j
@RequiredArgsConstructor
public class EmailSenderServiceImpl implements EmailSenderService {
    private final JavaMailSender mailSender;

    @Override
    public void sendEmail(String from, String to, String subject, String body, Resource[] attachments) {
        MimeMessage message = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(
                    message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());

            helper.setTo(to);
            helper.setFrom(from);
            helper.setSubject(subject);
            helper.setText(body, true);
            for (Resource attachment : attachments) {
                helper.addAttachment(attachment.getFilename(), attachment);
            }

            mailSender.send(message);
        } catch (MessagingException e) {
            throw new FailedToSendEmailException("Unable to send email", e);
        }
    }

}