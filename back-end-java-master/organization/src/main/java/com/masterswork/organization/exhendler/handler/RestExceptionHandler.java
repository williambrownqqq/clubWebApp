package com.masterswork.organization.exhendler.handler;

import com.masterswork.organization.api.dto.error.ApiError;
import com.masterswork.organization.exhendler.exception.ErrorHandlerException;
import org.springframework.http.ResponseEntity;

@FunctionalInterface
public interface RestExceptionHandler<E extends Exception> {

    ResponseEntity<ApiError> handleException(E exception) throws ErrorHandlerException;
}
