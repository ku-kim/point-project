package com.github.kukim.point.core.domain.type;

import lombok.Getter;

@Getter
public enum EventDetailType {
	SAVE_BUY("제품 구매"),
	SAVE_COUPON("쿠폰 등록"),
	SAVE_REVIEW("리뷰 등록"),
	SAVE_EVENT("이벤트 참여"),
	SAVE_REWARD("특별 보상"),

	USE_BUY("제품 구매"),
	USE_CANCEL("사용 취소"),

	DISAPPEAR_EXPIRATION("포인트 기간 만료")
	;


	private final String title;

	EventDetailType(String title) {
		this.title = title;
	}
}
