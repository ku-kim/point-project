package com.github.kukim.point.core.exception;

import static com.github.kukim.point.core.exception.PointApplicationErrorCode.POINT_UNSUPPORTED_EVENT_TYPE_CANCEL;

public class UnsupportedCancelPointEventTypeException extends PointApplicationException{

	public UnsupportedCancelPointEventTypeException() {
		super(POINT_UNSUPPORTED_EVENT_TYPE_CANCEL.getCode(),
			POINT_UNSUPPORTED_EVENT_TYPE_CANCEL.getMessage());
	}

	@Override
	public PointApplicationErrorCode getErrorCode() {
		return POINT_UNSUPPORTED_EVENT_TYPE_CANCEL;
	}
}
