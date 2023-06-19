package com.masterswork.organization.exhendler;

import com.masterswork.organization.api.dto.error.ApiError;
import com.masterswork.organization.exhendler.exception.ErrorHandlerException;
import com.masterswork.organization.exhendler.handler.RestExceptionHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class RestExceptionHandlerResolver<T extends Exception> {

    public static final String DEFAULT_ERROR =
            "The server encountered an internal error or misconfiguration and was unable to complete your request";
    public static final Integer DEFAULT_HTTP_STATUS = HttpStatus.INTERNAL_SERVER_ERROR.value();

    private final Map<Class<?>, RestExceptionHandler<?>> exceptionHandlers;

    public RestExceptionHandlerResolver() {
        this.exceptionHandlers = new HashMap<>();
    }

    public ResponseEntity<ApiError> handleException(T ex) {
        log.debug("An attempt to handle the exception that was thrown", ex);
        RestExceptionHandler<T> handler = resolveExceptionHandler(ex.getClass());
        ResponseEntity<ApiError> response;
        try {
            response = handler.handleException(ex);
        } catch (ErrorHandlerException e) {
            log.warn(
                    "Handler for '{}' cannot process exception. Default error response will be returned.",
                    ex.getClass().getName(), e);
            response = defaultResponse();
        }
        return response;
    }

    public void addHandler(Class<? extends Exception> exceptionClass,
                           RestExceptionHandler<? extends Exception> exceptionHandler) {
        exceptionHandlers.put(exceptionClass, exceptionHandler);
    }

    protected RestExceptionHandler<T> resolveExceptionHandler(Class<? extends Exception> exceptionClass) {
        for (Class<?> clazz = exceptionClass; clazz != Throwable.class; clazz = clazz.getSuperclass()) {
            if (exceptionHandlers.containsKey(clazz)) {
                return (RestExceptionHandler<T>) exceptionHandlers.get(clazz);
            }
        }
        log.warn("Handler for '{}' cannot be found. Default error response will be returned.",
                exceptionClass.getName());
        return exception -> defaultResponse();
    }

    protected ResponseEntity<ApiError> defaultResponse() {
        return ResponseEntity.status(DEFAULT_HTTP_STATUS)
                .body(ApiError.builder()
                        .status(DEFAULT_HTTP_STATUS)
                        .error(DEFAULT_ERROR)
                        .timestamp(Instant.now())
                        .build());
    };
}
