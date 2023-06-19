package com.masterswork.mail.service;

import com.masterswork.mail.api.dto.mail.EmailResponseDTO;
import com.masterswork.mail.api.dto.mail.EmailSendDTO;
import com.masterswork.mail.api.dto.mail.EmailSendFromTemplateDTO;
import com.masterswork.mail.jms.message.MailSendMessage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface EmailService {

    EmailResponseDTO prepareAndSend(EmailSendDTO emailSendDTO);

    EmailResponseDTO prepareAndSendFromTemplate(Long templateId, EmailSendFromTemplateDTO emailSendDTO);

    EmailResponseDTO prepareAndSendFromTemplateAndJmsMessage(MailSendMessage mailSendMessage);

    Page<EmailResponseDTO> getAllEmailsByUser(String username, Pageable pageable);

    Page<EmailResponseDTO> getAllEmails(Pageable pageable);
}
