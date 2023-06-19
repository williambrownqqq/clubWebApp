package com.masterswork.process.exhendler.handler;

import com.masterswork.process.api.dto.error.ApiError;
import com.masterswork.process.exhendler.exception.MessageSourcePropertyNotFoundException;
import com.masterswork.process.exhendler.interpolator.SpelMessageInterpolator;
import org.springframework.context.MessageSource;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

public class ConstraintViolationExceptionHandler extends MessageSourceExceptionHandler<ConstraintViolationException> {

    public ConstraintViolationExceptionHandler(MessageSource messageSource, SpelMessageInterpolator interpolator) {
        super(messageSource, interpolator);
    }

    @Override
    protected ApiError createErrorBody(ConstraintViolationException ex) throws MessageSourcePropertyNotFoundException {
        ApiError apiError = super.createErrorBody(ex);
        int excCount = 0;
        StringBuilder detail = new StringBuilder(apiError.getError());
        for (ConstraintViolation<?> violation : ex.getConstraintViolations()) {
            detail.append(String.format(" %d. %s;", ++excCount, violation.getMessage()));
        }
        return apiError.setError(detail.toString());
    }
}
