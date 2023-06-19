package com.masterswork.account.exhendler.handler;

import com.masterswork.account.api.dto.error.ApiError;
import com.masterswork.account.exhendler.exception.ErrorHandlerException;
import org.springframework.http.ResponseEntity;

@FunctionalInterface
public interface RestExceptionHandler<E extends Exception> {

    ResponseEntity<ApiError> handleException(E exception) throws ErrorHandlerException;
}
