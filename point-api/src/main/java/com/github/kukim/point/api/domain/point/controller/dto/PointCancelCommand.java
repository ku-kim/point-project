package com.github.kukim.point.api.domain.point.controller.dto;

import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.validation.annotation.Validated;

@Validated
@Getter
@ToString
@NoArgsConstructor
public class PointCancelCommand {
	@NotNull
	private String tradeId;
}
