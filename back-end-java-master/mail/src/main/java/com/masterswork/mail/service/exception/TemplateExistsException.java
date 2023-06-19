package com.masterswork.mail.service.exception;

public class TemplateExistsException extends RuntimeException {

	public TemplateExistsException(String message) {
		super(message);
	}

	public TemplateExistsException(String message, Throwable cause) {
		super(message, cause);
	}
}
