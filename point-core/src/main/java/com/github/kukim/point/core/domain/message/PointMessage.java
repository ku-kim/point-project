package com.github.kukim.point.core.domain.message;

import com.github.kukim.point.core.domain.type.EventDetailType;
import com.github.kukim.point.core.domain.type.EventType;
import com.github.kukim.point.core.domain.util.KeyGenerator;
import javax.annotation.Nonnull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PointMessage {

	private String messageId;
	private String tradeNo;
	private EventType eventType;
	private EventDetailType eventDetailType;
	private Long savePoint;
	private String description;
	private Long memberId;

	private PointMessage(String messageId, String tradeNo, EventType eventType,
		EventDetailType eventDetailType, Long savePoint, String description, Long memberId) {
		this.messageId = messageId;
		this.tradeNo = tradeNo;
		this.eventType = eventType;
		this.eventDetailType = eventDetailType;
		this.savePoint = savePoint;
		this.description = description;
		this.memberId = memberId;
	}

	public static PointMessage create(@Nonnull String tradeNo, @Nonnull EventType eventType, @Nonnull EventDetailType eventDetailType,
		@Nonnull Long point, String description, @Nonnull Long memberId) {
		return
			new PointMessage(KeyGenerator.generateUuid(),
				tradeNo,
				eventType,
				eventDetailType,
				point,
				description,
				memberId);
	}


}
