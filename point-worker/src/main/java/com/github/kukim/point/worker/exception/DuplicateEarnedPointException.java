package com.github.kukim.point.worker.exception;

import static com.github.kukim.point.core.exception.PointApplicationErrorCode.POINT_DUPLICATE_EXCEPTION;

import com.github.kukim.point.core.exception.PointApplicationErrorCode;
import com.github.kukim.point.core.exception.PointApplicationException;

public class DuplicateEarnedPointException extends PointApplicationException {

	public DuplicateEarnedPointException() {
		super(POINT_DUPLICATE_EXCEPTION.getCode(), POINT_DUPLICATE_EXCEPTION.getMessage());
	}

	@Override
	public PointApplicationErrorCode getErrorCode() {
		return POINT_DUPLICATE_EXCEPTION;
	}
}
