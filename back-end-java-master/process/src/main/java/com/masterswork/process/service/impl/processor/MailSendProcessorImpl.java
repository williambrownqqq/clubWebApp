package com.masterswork.process.service.impl.processor;

import com.masterswork.process.api.dto.schema.stage.StageDTO;
import com.masterswork.process.jms.message.MailSendMessage;
import com.masterswork.process.model.relational.ProcessInstance;
import com.masterswork.process.service.StageProcessor;
import lombok.RequiredArgsConstructor;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MailSendProcessorImpl implements StageProcessor {

    private final static String MAIL_DELIVER_REQUEST_TOPIC = "mail-deliver-request";

    private final JmsTemplate jmsTemplate;

    @Override
    public void process(StageDTO stageDTO, ProcessInstance processInstance) {
        MailSendMessage sendMessage = MailSendMessage.builder()
                .toId(getIdByType(processInstance, (String) stageDTO.getProperties().get("to")))
                .fromId(getIdByType(processInstance, (String) stageDTO.getProperties().get("from")))
                .fromUsername(processInstance.getOwnerUsername())
                .templateId(Long.valueOf((Integer) stageDTO.getProperties().get("templateId")))
                .parameters((Map<String, Object>) stageDTO.getProperties().get("emailParameters"))
                .build();

        Object attachmentsIds = processInstance.getPropertyForStage(
                processInstance.getCurrentStage(), "attachmentsIds");
        if (attachmentsIds != null) {
            sendMessage.setAttachmentsIds(mapIntToLongList((List<Integer>) attachmentsIds));
        }

        jmsTemplate.convertAndSend(MAIL_DELIVER_REQUEST_TOPIC, sendMessage, m -> {
            m.setLongProperty("process-instance-id", processInstance.getId());
            m.setLongProperty("stage-id", processInstance.getCurrentStage());
            return m;
        });
    }

    private Long getIdByType(ProcessInstance processInstance, String type) {
        if ("subordinate".equals(type)) {
            return processInstance.getSubordinateId();
        } else if ("owner".equals(type)) {
            return processInstance.getOwnerId();
        }
        throw new IllegalArgumentException("Unsupported person type");
    }

    private List<Long> mapIntToLongList(List<Integer> input) {
        if (input == null) return new ArrayList<>();
        else return input.stream().map(Integer::longValue).collect(Collectors.toList());
    }
}
