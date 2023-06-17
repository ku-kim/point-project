package com.github.kukim.point.api.common;

import com.github.kukim.point.core.exception.PointApplicationErrorCode;

public class CustomApiResponse<T> {
	private String code;
	private String message;
	private T data;

	public CustomApiResponse(String code, String message, T data) {
		this.code = code;
		this.message = message;
		this.data = data;
	}

	public CustomApiResponse(PointApplicationErrorCode applicationErrorCode) {
		this.code = applicationErrorCode.getCode();
		this.message = applicationErrorCode.getMessage();
	}

	public CustomApiResponse(PointApplicationErrorCode applicationErrorCode, T data) {
		this.code = applicationErrorCode.getCode();
		this.message = applicationErrorCode.getMessage();
		this.data = data;
	}

	public String getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}

	public T getData() {
		return data;
	}

	@Override
	public String toString() {
		return "CustomApiResponse{" +
			"code='" + code + '\'' +
			", message='" + message + '\'' +
			", data=" + data +
			'}';
	}
}
