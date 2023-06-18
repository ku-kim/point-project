package com.github.kukim.point.api.exception;

import com.github.kukim.point.core.exception.PointApplicationErrorCode;
import com.github.kukim.point.core.exception.PointApplicationException;

public class SortUnsupportedOperationException extends PointApplicationException {

	public SortUnsupportedOperationException() {
		super(PointApplicationErrorCode.SORT_UNSUPPORTED_OPERATION_Exception.getCode(),
			PointApplicationErrorCode.SORT_UNSUPPORTED_OPERATION_Exception.getMessage());
	}

	@Override
	public PointApplicationErrorCode getErrorCode() {
		return PointApplicationErrorCode.SORT_UNSUPPORTED_OPERATION_Exception;

	}
}
