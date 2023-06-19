package com.masterswork.organization.exhendler.exception;

import lombok.Getter;

@Getter
public class MessageSourcePropertyNotFoundException extends ErrorHandlerException {

    private final String property;

    public MessageSourcePropertyNotFoundException(String property) {
        super(String.format("Property '%s' cannot be found. Verify your message source.", property));
        this.property = property;
    }
}
