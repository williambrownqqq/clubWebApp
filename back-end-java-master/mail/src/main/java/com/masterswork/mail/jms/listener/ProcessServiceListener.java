package com.masterswork.mail.jms.listener;

import com.masterswork.mail.jms.message.MailEventResultMessage;
import com.masterswork.mail.jms.message.MailSendMessage;
import com.masterswork.mail.service.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import javax.jms.Session;

@Slf4j
@Component
@RequiredArgsConstructor
public class ProcessServiceListener {

    private final static String EMAIL_DELIVERED_TOPIC_NAME = "email-event";

    private final EmailService emailService;
    private final JmsTemplate jmsTemplate;

    @JmsListener(destination = "mail-deliver-request", containerFactory = "topicListenerFactory")
    public void receiveTopicMessage(@Payload MailSendMessage mailSendMessage,
                                    @Headers MessageHeaders headers,
                                    Message message,
                                    Session session) {
        emailService.prepareAndSendFromTemplateAndJmsMessage(mailSendMessage);
        jmsTemplate.convertAndSend(EMAIL_DELIVERED_TOPIC_NAME,
                MailEventResultMessage.of(
                        "DEFAULT",
                        headers.get("process-instance-id", Long.class),
                        headers.get("stage-id", Long.class)
                )
        );
    }
}
