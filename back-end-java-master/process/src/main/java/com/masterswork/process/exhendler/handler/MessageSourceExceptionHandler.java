package com.masterswork.process.exhendler.handler;

import com.masterswork.process.api.dto.error.ApiError;
import com.masterswork.process.exhendler.exception.ErrorHandlerException;
import com.masterswork.process.exhendler.exception.MessageSourcePropertyNotFoundException;
import com.masterswork.process.exhendler.interpolator.SpelMessageInterpolator;
import com.masterswork.process.exhendler.messages.ApiErrorBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@RequiredArgsConstructor
public class MessageSourceExceptionHandler<E extends Exception> implements RestExceptionHandler<E> {

    private final MessageSource messageSource;
    private final SpelMessageInterpolator interpolator;

    @Override
    public ResponseEntity<ApiError> handleException(E exception) throws ErrorHandlerException {
        ApiError errorBody = createErrorBody(exception);
        return new ResponseEntity<>(errorBody, HttpStatus.valueOf(errorBody.getStatus()));
    }

    protected ApiError createErrorBody(E ex) throws MessageSourcePropertyNotFoundException {
        ApiErrorBuilder<E> messageBuilder = new ApiErrorBuilder<>();
        return messageBuilder.messageSource(messageSource)
                .interpolator(interpolator)
                .build(ex);
    }
}
