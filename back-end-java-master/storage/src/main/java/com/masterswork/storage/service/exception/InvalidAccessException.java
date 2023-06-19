package com.masterswork.storage.service.exception;

public class InvalidAccessException extends StorageException {

	public InvalidAccessException(String message) {
		super(message);
	}

	public InvalidAccessException(String message, Throwable cause) {
		super(message, cause);
	}
}
