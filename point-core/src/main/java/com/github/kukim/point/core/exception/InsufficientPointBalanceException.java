package com.github.kukim.point.core.exception;

import static com.github.kukim.point.core.exception.PointApplicationErrorCode.POINT_INSUFFICIENT_POINT_BALANCE_EXCEPTION;

public class InsufficientPointBalanceException extends PointApplicationException{

	public InsufficientPointBalanceException() {
		super(POINT_INSUFFICIENT_POINT_BALANCE_EXCEPTION.getCode(),
			POINT_INSUFFICIENT_POINT_BALANCE_EXCEPTION.getMessage());
	}

	@Override
	public PointApplicationErrorCode getErrorCode() {
		return POINT_INSUFFICIENT_POINT_BALANCE_EXCEPTION;
	}
}
