package com.masterswork.process.exhendler.handler;

import com.masterswork.process.api.dto.error.ApiError;
import com.masterswork.process.exhendler.exception.ErrorHandlerException;
import org.springframework.http.ResponseEntity;

@FunctionalInterface
public interface RestExceptionHandler<E extends Exception> {

    ResponseEntity<ApiError> handleException(E exception) throws ErrorHandlerException;
}
