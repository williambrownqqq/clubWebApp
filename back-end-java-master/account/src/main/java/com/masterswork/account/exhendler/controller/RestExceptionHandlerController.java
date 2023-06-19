package com.masterswork.account.exhendler.controller;

import com.masterswork.account.api.dto.error.ApiError;
import com.masterswork.account.exhendler.RestExceptionHandlerResolver;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class RestExceptionHandlerController {

    private final RestExceptionHandlerResolver<Exception> exceptionResolver;

    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<ApiError> handleException(Exception ex, HttpServletRequest request) {
        log.warn("HTTP request is invalid - [method={}, path={}]. Error: {}",
                request.getMethod(), request.getServletPath(), ex.getMessage(), ex);
        ResponseEntity<ApiError> responseEntity = exceptionResolver.handleException(ex);
        Optional.ofNullable(responseEntity.getBody())
                .ifPresent(apiError -> apiError.setPath(request.getServletPath()));
        return responseEntity;
    }
}
