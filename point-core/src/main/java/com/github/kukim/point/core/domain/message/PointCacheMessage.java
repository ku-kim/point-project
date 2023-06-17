package com.github.kukim.point.core.domain.message;

import java.math.BigDecimal;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@ToString
public class PointCacheMessage {

	private Long memberId;
	private BigDecimal point;

	public PointCacheMessage(Long memberId, BigDecimal point) {
		this.memberId = memberId;
		this.point = point;
	}
}
