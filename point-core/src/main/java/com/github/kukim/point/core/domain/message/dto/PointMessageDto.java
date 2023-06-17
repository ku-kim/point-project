package com.github.kukim.point.core.domain.message.dto;

import com.github.kukim.point.core.domain.message.PointMessage;
import com.github.kukim.point.core.domain.type.EventDetailType;
import com.github.kukim.point.core.domain.type.EventType;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PointMessageDto {

	private String messageId;
	private String tradeNo;
	private EventType eventType;
	private EventDetailType eventDetailType;
	private BigDecimal point;
	private Long memberId;

	private PointMessageDto(String messageId, String tradeNo, EventType eventType,
		EventDetailType eventDetailType, BigDecimal point, Long memberId) {
		this.messageId = messageId;
		this.tradeNo = tradeNo;
		this.eventType = eventType;
		this.eventDetailType = eventDetailType;
		this.point = point;
		this.memberId = memberId;
	}

	public static PointMessageDto of(PointMessage message) {
		return new PointMessageDto(message.getMessageId(),
			message.getTradeNo(), message.getEventType(), message.getEventDetailType(),
			message.getPoint(), message.getMemberId());

	}
}
