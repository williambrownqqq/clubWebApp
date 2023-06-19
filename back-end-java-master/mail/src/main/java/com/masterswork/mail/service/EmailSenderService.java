package com.masterswork.mail.service;

import org.springframework.core.io.Resource;

public interface EmailSenderService {

    void sendEmail(String from, String to, String subject, String body, Resource[] attachments);
}
