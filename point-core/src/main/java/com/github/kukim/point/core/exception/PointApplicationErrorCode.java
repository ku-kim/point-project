package com.github.kukim.point.core.exception;

public enum PointApplicationErrorCode {
	SUCCESS("000", "정상 처리"),
	FAILURE("444", "시스템 오류 발생"),

	POINT_CAL_EXCEPTION("S100", "포인트 계산 음수 오류 발생"),
	QUEUE_EXCEPTION("Q100", "큐 메세지 전송 중 오류 발생"),
	;

	private final String code;
	private final String message;

	PointApplicationErrorCode(String code, String message) {
		this.code = code;
		this.message = message;
	}

	public String getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}
}
