package com.github.kukim.point.core.exception;

import static com.github.kukim.point.core.exception.PointApplicationErrorCode.POINT_UNSUPPORTED_EVENT_TYPE_REDEEM;

public class UnsupportedRedeemPointEventTypeException extends PointApplicationException {

	public UnsupportedRedeemPointEventTypeException() {
		super(POINT_UNSUPPORTED_EVENT_TYPE_REDEEM.getCode(),
			POINT_UNSUPPORTED_EVENT_TYPE_REDEEM.getMessage());
	}

	@Override
	public PointApplicationErrorCode getErrorCode() {
		return POINT_UNSUPPORTED_EVENT_TYPE_REDEEM;
	}
}
