package com.github.kukim.point.api.domain.point.controller.dto;

import com.github.kukim.point.core.domain.type.EventDetailType;
import com.github.kukim.point.core.domain.type.EventType;
import java.math.BigDecimal;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.validation.annotation.Validated;

@Validated
@Getter
@ToString
@NoArgsConstructor
public class PointEarnCommand {

	@NotNull
	private String tradeId;

	@NotNull
	private EventType eventType;

	@NotNull
	private EventDetailType eventDetailType;

	@NotNull
	@Min(0)
	@Max(100000)
	private BigDecimal earnPoint;

	private String description;

	public PointEarnCommand(@NotNull String tradeId, EventType eventType, EventDetailType eventDetailType,
		BigDecimal earnPoint, String description) {
		this.tradeId = tradeId;
		this.eventType = eventType;
		this.eventDetailType = eventDetailType;
		this.earnPoint = earnPoint;
		this.description = description;
	}
}
