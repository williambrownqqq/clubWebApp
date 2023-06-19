package com.masterswork.process.exhendler.messages;

import com.masterswork.process.api.dto.error.ApiError;
import com.masterswork.process.exhendler.exception.MessageSourcePropertyNotFoundException;
import com.masterswork.process.exhendler.interpolator.SpelMessageInterpolator;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Setter
@Accessors(fluent = true)
public class ApiErrorBuilder <E extends Exception> {

    private static final Integer DEFAULT_HTTP_STATUS = HttpStatus.INTERNAL_SERVER_ERROR.value();
    private static final String ERROR = "error";
    private static final String STATUS = "status";

    private MessageSource messageSource;
    private SpelMessageInterpolator interpolator;

    public ApiError build(E exception) throws MessageSourcePropertyNotFoundException {
        String prefix = resolveMessagePrefix(exception.getClass());
        return ApiError.builder().status(resolveStatus(prefix))
                .error(resolveMessage(prefix, ERROR, exception))
                .timestamp(Instant.now())
                .build();
    }

    private String resolveMessagePrefix(Class<? extends Exception> exceptionClass)
        throws MessageSourcePropertyNotFoundException {
        for (Class<?> clazz = exceptionClass; clazz != Throwable.class; clazz = clazz.getSuperclass()) {
            String prefix = getMessage(clazz.getName(), ERROR);
            if (StringUtils.hasText(prefix)) {
                return clazz.getName();
            }
        }
        throw new MessageSourcePropertyNotFoundException(exceptionClass.getName() + "." + ERROR);
    }

    private Integer resolveStatus(String prefix) {
        String status = getMessage(prefix, STATUS);
        if (ObjectUtils.isEmpty(status)) {
            log.warn("Http status was set as default '{}' for exception '{}' class.", DEFAULT_HTTP_STATUS, prefix);
            return DEFAULT_HTTP_STATUS;
        }
        return HttpStatus.valueOf(Integer.parseInt(status)).value();
    }

    private String resolveMessage(String prefix, String key, E exception) {
        String messageTemplate = getMessage(prefix, key);
        Map<String, Object> variables = new HashMap<>(1);
        variables.put("ex", exception);
        return interpolator.interpolate(messageTemplate, variables);
    }

    private String getMessage(String prefix, String key) {
        String message = messageSource.getMessage(prefix + "." + key, null, null, LocaleContextHolder.getLocale());
        if (message == null) {
            log.debug("No message found for '{}.{}'.", prefix, key);
        }
        return message;
    }
}
