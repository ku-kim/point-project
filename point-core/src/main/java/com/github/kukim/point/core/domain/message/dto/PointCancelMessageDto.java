package com.github.kukim.point.core.domain.message.dto;

import com.github.kukim.point.core.domain.message.PointCancelMessage;
import com.github.kukim.point.core.domain.type.EventDetailType;
import com.github.kukim.point.core.domain.type.EventType;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PointCancelMessageDto {

	private String messageId;
	private String originSearchId;
	private String tradeNo;
	private EventType eventType;
	private EventDetailType eventDetailType;
	private BigDecimal point;
	private Long memberId;

	private PointCancelMessageDto(String messageId, String originSearchId, String tradeNo, EventType eventType,
		EventDetailType eventDetailType, BigDecimal point, Long memberId) {
		this.messageId = messageId;
		this.originSearchId = originSearchId;
		this.tradeNo = tradeNo;
		this.eventType = eventType;
		this.eventDetailType = eventDetailType;
		this.point = point;
		this.memberId = memberId;
	}

	public static PointCancelMessageDto of(PointCancelMessage message) {
		return new PointCancelMessageDto(message.getMessageId(),
			message.getOriginEarnPointSearchId(),
			message.getTradeNo(), message.getEventType(), message.getEventDetailType(),
			message.getPoint(), message.getMemberId());
	}
}
