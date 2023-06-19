package com.masterswork.mail.exhendler.handler;


import com.masterswork.mail.api.dto.error.ApiError;
import com.masterswork.mail.exhendler.exception.ErrorHandlerException;
import org.springframework.http.ResponseEntity;

@FunctionalInterface
public interface RestExceptionHandler<E extends Exception> {

    ResponseEntity<ApiError> handleException(E exception) throws ErrorHandlerException;
}
