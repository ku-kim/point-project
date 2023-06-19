package com.github.kukim.point.core.exception;

public enum PointApplicationErrorCode {
	SUCCESS("000", "정상 처리"),
	FAILURE("444", "시스템 오류 발생"),

	POINT_CAL_EXCEPTION("S100", "포인트 계산 음수 오류 발생"),
	SORT_UNSUPPORTED_OPERATION_Exception("S101", "정렬을 허용하지 않습니다."),
	POINT_UNSUPPORTED_EVENT_TYPE_REDEEM("S102", "사용할 수 없는 포인트입니다."),
	POINT_UNSUPPORTED_EVENT_TYPE_CANCEL("S103", "취소할 수 없는 포인트입니다."),

	POINT_INSUFFICIENT_POINT_BALANCE_EXCEPTION("P100", "잔액이 부족합니다."),
	POINT_NOTFOUND_RECEIPT_EXCEPTION("P101", "Point Receipt를 발견하지 못했습니다."),
	POINT_DUPLICATE_EXCEPTION("P102", "이미 Point가 적립되었습니다."),
	POINT_NOTFOUND_MEMBER_ID_AND_TRADE_ID("P103", "trade id에 대한 Point Receipt를 발견하지 못했습니다."),

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
