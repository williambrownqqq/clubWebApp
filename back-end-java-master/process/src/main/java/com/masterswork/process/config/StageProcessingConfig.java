package com.masterswork.process.config;

import com.masterswork.process.model.enumeration.StageType;
import com.masterswork.process.service.StageProcessor;
import com.masterswork.process.service.impl.processor.DocumentReviewProcessorImpl;
import com.masterswork.process.service.impl.processor.MailSendProcessorImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
public class StageProcessingConfig {

    @Bean("stageProcessors")
    public Map<StageType, StageProcessor> stageProcessors(MailSendProcessorImpl mailSSendProcessor,
                                                          DocumentReviewProcessorImpl documentReviewProcessor) {
        return Map.of(
                StageType.SEND_MAIL, mailSSendProcessor,
                StageType.REVIEW_DOCUMENT, documentReviewProcessor
        );
    }
}
