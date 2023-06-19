package com.masterswork.mail.service.exception;

public class TemplateNotFoundException extends RuntimeException {

	public TemplateNotFoundException(String message) {
		super(message);
	}

	public TemplateNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}
}
