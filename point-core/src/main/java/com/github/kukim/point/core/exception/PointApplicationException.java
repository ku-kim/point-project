package com.github.kukim.point.core.exception;

public abstract class PointApplicationException extends RuntimeException {

	private String code;

	protected PointApplicationException() {

	}

	protected PointApplicationException(String code, String message) {
		super(message);
		this.code = code;
	}

	protected PointApplicationException(String code, String message, Throwable throwable) {
		super(message, throwable);
		this.code = code;
	}

	protected PointApplicationException(Throwable throwable) {
		super(throwable);
	}

	public abstract PointApplicationErrorCode getErrorCode();

	public String getCode() {
		return code;
	}
}
