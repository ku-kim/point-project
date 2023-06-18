package com.github.kukim.point.api.domain.member.controller.dto;

import com.github.kukim.point.core.domain.point.Point;
import com.github.kukim.point.core.domain.type.EventDetailType;
import com.github.kukim.point.core.domain.type.EventType;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@ToString
public class MemberPointHistoryDto {

	private String searchId;
	private String tradeId;
	private String messageId;
	private EventType eventType;
	private EventDetailType eventDetailType;
	private BigDecimal point;
	private String description;
	private Long memberId;
	private LocalDateTime expirationDate;
	private LocalDateTime createdDate;

	private MemberPointHistoryDto(String searchId, String tradeId, String messageId,
		EventType eventType,
		EventDetailType eventDetailType, BigDecimal point, String description, Long memberId,
		LocalDateTime expirationDate, LocalDateTime createdDate) {
		this.searchId = searchId;
		this.tradeId = tradeId;
		this.messageId = messageId;
		this.eventType = eventType;
		this.eventDetailType = eventDetailType;
		this.point = point;
		this.description = description;
		this.memberId = memberId;
		this.expirationDate = expirationDate;
		this.createdDate = createdDate;
	}

	public static MemberPointHistoryDto of(Point point) {
		return new MemberPointHistoryDto(point.getSearchId(), point.getTradeId(),
			point.getMessageId(),
			point.getEventType(), point.getEventDetailType(), point.getSavePoint(),
			point.getDescription(),
			point.getMemberId(), point.getExpirationDate(), point.getCreatedDate());
	}
}
