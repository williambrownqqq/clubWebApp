package com.masterswork.storage.exhendler.handler;


import com.masterswork.storage.api.dto.error.ApiError;
import com.masterswork.storage.exhendler.exception.ErrorHandlerException;
import org.springframework.http.ResponseEntity;

@FunctionalInterface
public interface RestExceptionHandler<E extends Exception> {

    ResponseEntity<ApiError> handleException(E exception) throws ErrorHandlerException;
}
