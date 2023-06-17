package com.github.kukim.point.core.exception;

public class PointCacheNegativeNumberException extends PointApplicationException {

	public PointCacheNegativeNumberException() {
		super(PointApplicationErrorCode.POINT_CAL_EXCEPTION.getCode(),
			PointApplicationErrorCode.POINT_CAL_EXCEPTION.getMessage());
	}

	@Override
	public PointApplicationErrorCode getErrorCode() {
		return PointApplicationErrorCode.POINT_CAL_EXCEPTION;
	}
}
