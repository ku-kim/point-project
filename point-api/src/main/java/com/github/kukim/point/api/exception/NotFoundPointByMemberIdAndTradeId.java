package com.github.kukim.point.api.exception;

import static com.github.kukim.point.core.exception.PointApplicationErrorCode.POINT_NOTFOUND_MEMBER_ID_AND_TRADE_ID;

import com.github.kukim.point.core.exception.PointApplicationErrorCode;
import com.github.kukim.point.core.exception.PointApplicationException;

public class NotFoundPointByMemberIdAndTradeId extends PointApplicationException {

	public NotFoundPointByMemberIdAndTradeId() {
		super(POINT_NOTFOUND_MEMBER_ID_AND_TRADE_ID.getCode(),
			POINT_NOTFOUND_MEMBER_ID_AND_TRADE_ID.getMessage());
	}

	@Override
	public PointApplicationErrorCode getErrorCode() {
		return POINT_NOTFOUND_MEMBER_ID_AND_TRADE_ID;
	}
}
