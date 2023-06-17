package com.github.kukim.point.api.common;

import com.github.kukim.point.core.exception.PointApplicationErrorCode;

public class CustomApiResponseGenerator {
	public static final CustomApiResponse<Void> SUCCESS = new CustomApiResponse<>(
		PointApplicationErrorCode.SUCCESS);
	public static final CustomApiResponse<Void> FAILURE = new CustomApiResponse<>(PointApplicationErrorCode.FAILURE);

	private CustomApiResponseGenerator() {
		throw new IllegalStateException("Utility class");
	}


	public static <D> CustomApiResponse of(String code, String message) {
		return new CustomApiResponse(code, message, null);
	}

	public static <D> CustomApiResponse success(D data) {
		return new CustomApiResponse(PointApplicationErrorCode.SUCCESS, data);
	}
}
