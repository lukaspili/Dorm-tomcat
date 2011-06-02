package com.zenika.dorm.core.exception;

@SuppressWarnings("serial")
public class CoreException extends RuntimeException {

	public CoreException(String message) {
		super(message);
	}

    public CoreException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
