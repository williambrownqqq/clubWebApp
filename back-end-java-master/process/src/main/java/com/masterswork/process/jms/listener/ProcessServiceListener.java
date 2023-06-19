package com.masterswork.process.jms.listener;

import com.masterswork.process.jms.message.MailEventResultMessage;
import com.masterswork.process.service.StageManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
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

    private final StageManager stageManager;

    @JmsListener(destination = "email-event", containerFactory = "topicListenerFactory")
    public void receiveMailTopicMessage(@Payload MailEventResultMessage mailEventResultMessage,
                                    @Headers MessageHeaders headers,
                                    Message message,
                                    Session session) {

        stageManager.runNextStage(mailEventResultMessage.getProcessInstanceId(), mailEventResultMessage.getStatus());
    }
}
