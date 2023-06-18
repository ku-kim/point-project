package com.github.kukim.point.worker.exception;

import static com.github.kukim.point.core.exception.PointApplicationErrorCode.POINT_NOTFOUND_RECEIPT_EXCEPTION;

import com.github.kukim.point.core.exception.PointApplicationErrorCode;
import com.github.kukim.point.core.exception.PointApplicationException;

public class NotFoundPointReceiptException extends PointApplicationException {


	public NotFoundPointReceiptException() {
		super(POINT_NOTFOUND_RECEIPT_EXCEPTION.getCode(),
			POINT_NOTFOUND_RECEIPT_EXCEPTION.getMessage());
	}

	@Override
	public PointApplicationErrorCode getErrorCode() {
		return POINT_NOTFOUND_RECEIPT_EXCEPTION;
	}
}
