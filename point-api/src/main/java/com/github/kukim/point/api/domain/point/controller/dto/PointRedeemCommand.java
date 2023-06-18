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
public class PointRedeemCommand {

	@NotNull
	private String tradeId;

	@NotNull
	private EventType eventType;

	@NotNull
	private EventDetailType eventDetailType;

	@NotNull
	@Min(-100000)
	@Max(0)
	private BigDecimal redeemPoint;

	private String description;
}
