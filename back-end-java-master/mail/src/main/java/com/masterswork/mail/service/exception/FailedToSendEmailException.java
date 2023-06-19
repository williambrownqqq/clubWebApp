package com.masterswork.mail.service.exception;

public class FailedToSendEmailException extends RuntimeException {

	public FailedToSendEmailException(String message) {
		super(message);
	}

	public FailedToSendEmailException(String message, Throwable cause) {
		super(message, cause);
	}
}
