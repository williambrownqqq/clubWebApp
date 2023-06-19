package com.masterswork.storage.config;

import com.masterswork.storage.exhendler.RestExceptionHandlerResolver;
import com.masterswork.storage.exhendler.handler.ConstraintViolationExceptionHandler;
import com.masterswork.storage.exhendler.handler.MessageSourceExceptionHandler;
import com.masterswork.storage.exhendler.handler.MethodArgumentNotValidExceptionHandler;
import com.masterswork.storage.exhendler.handler.RestExceptionHandler;
import com.masterswork.storage.exhendler.interpolator.SpelMessageInterpolator;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.Ordered;
import org.springframework.web.bind.MethodArgumentNotValidException;

import javax.validation.ConstraintViolationException;
import java.nio.charset.StandardCharsets;

@Configuration
@AutoConfigureOrder(Ordered.HIGHEST_PRECEDENCE)
public class RestExceptionHandlerConfig {

    @Bean
    public RestExceptionHandler<Exception> messageSourceExceptionHandler(
            MessageSource messageSource, SpelMessageInterpolator spelMessageInterpolator) {
        return new MessageSourceExceptionHandler<>(messageSource, spelMessageInterpolator);
    }

    @Bean
    public ConstraintViolationExceptionHandler constraintViolationExceptionHandler(
            MessageSource messageSource, SpelMessageInterpolator spelMessageInterpolator) {
        return new ConstraintViolationExceptionHandler(messageSource, spelMessageInterpolator);
    }

    @Bean
    public MethodArgumentNotValidExceptionHandler methodArgumentNotValidExceptionHandler(
            MessageSource messageSource, SpelMessageInterpolator spelMessageInterpolator) {
        return new MethodArgumentNotValidExceptionHandler(messageSource, spelMessageInterpolator);
    }

    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasenames("classpath:default-exception-messages", "classpath:jwt-exception-messages",
                "classpath:spring-exception-messages", "classpath:exception-messages");
        messageSource.setDefaultEncoding(StandardCharsets.UTF_8.name());
        return messageSource;
    }

    @Bean
    public SpelMessageInterpolator spelMessageInterpolator() {
        return new SpelMessageInterpolator();
    }

    @Bean
    public RestExceptionHandlerResolver<Exception> restExceptionHandlerResolver(
            RestExceptionHandler<Exception> messageSourceExceptionHandler,
            ConstraintViolationExceptionHandler constraintViolationExceptionHandler,
            MethodArgumentNotValidExceptionHandler methodArgumentNotValidExceptionHandler) {

        RestExceptionHandlerResolver<Exception> restExceptionHandlerResolver = new RestExceptionHandlerResolver<>();
        restExceptionHandlerResolver.addHandler(MethodArgumentNotValidException.class,
                methodArgumentNotValidExceptionHandler);
        restExceptionHandlerResolver.addHandler(ConstraintViolationException.class,
                constraintViolationExceptionHandler);
        restExceptionHandlerResolver.addHandler(Exception.class, messageSourceExceptionHandler);
        return restExceptionHandlerResolver;
    }
}
