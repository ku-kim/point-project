package com.github.kukim.point.core.exception;

public class PointBalanceNegativeNumberException extends PointApplicationException {

	public PointBalanceNegativeNumberException() {
		super(PointApplicationErrorCode.POINT_CAL_EXCEPTION.getCode(),
			PointApplicationErrorCode.POINT_CAL_EXCEPTION.getMessage());
	}

	@Override
	public PointApplicationErrorCode getErrorCode() {
		return PointApplicationErrorCode.POINT_CAL_EXCEPTION;
	}
}
