package com.github.kukim.point.core.domain.message;

import com.github.kukim.point.core.domain.point.Point;
import com.github.kukim.point.core.domain.type.EventDetailType;
import com.github.kukim.point.core.domain.type.EventType;
import com.github.kukim.point.core.domain.util.KeyGenerator;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import javax.annotation.Nonnull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.ToString;

@Getter
@NoArgsConstructor
@ToString
public class PointCancelMessage {

	private String messageId;
	private String originSearchId;
	private String tradeNo;
	private EventType eventType;
	private EventDetailType eventDetailType;
	private BigDecimal point;
	private String description;
	private Long memberId;

	private PointCancelMessage(String messageId, String originSearchId, String tradeNo, EventType eventType,
		EventDetailType eventDetailType, BigDecimal point, String description, Long memberId) {
		this.messageId = messageId;
		this.originSearchId = originSearchId;
		this.tradeNo = tradeNo;
		this.eventType = eventType;
		this.eventDetailType = eventDetailType;
		this.point = point;
		this.description = description;
		this.memberId = memberId;
	}

	public static PointCancelMessage create(@NonNull String originSearchId, @Nonnull String tradeNo, @Nonnull EventType eventType,
		@Nonnull EventDetailType eventDetailType,
		@Nonnull BigDecimal point, String description, @Nonnull Long memberId) {
		return
			new PointCancelMessage(KeyGenerator.generateUUID(),
				originSearchId,
				tradeNo,
				eventType,
				eventDetailType,
				point,
				description,
				memberId);
	}

	public Point toPoint() {
		return new Point(tradeNo, messageId, eventType, eventDetailType, point, description,
			LocalDateTime.now().plusYears(1), memberId);
	}

}
