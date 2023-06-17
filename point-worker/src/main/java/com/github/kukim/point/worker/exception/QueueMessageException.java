package com.github.kukim.point.worker.exception;

import static com.github.kukim.point.core.exception.PointApplicationErrorCode.QUEUE_EXCEPTION;

import com.github.kukim.point.core.exception.PointApplicationErrorCode;
import com.github.kukim.point.core.exception.PointApplicationException;

public class QueueMessageException extends PointApplicationException {

	public QueueMessageException(Throwable cause) {
		super(QUEUE_EXCEPTION.getCode(), QUEUE_EXCEPTION.getMessage(), cause);
	}

	@Override
	public PointApplicationErrorCode getErrorCode() {
		return QUEUE_EXCEPTION;
	}
}
