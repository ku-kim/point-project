package com.github.kukim.point.core.domain.point.dto;

import com.github.kukim.point.core.domain.point.PointBalance;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@ToString
public class PointBalanceDto {

	private Long memberId;
	private BigDecimal point;

	private PointBalanceDto(Long memberId, BigDecimal point) {
		this.memberId = memberId;
		this.point = point;
	}

	public static PointBalanceDto of(PointBalance pointBalance) {
		return new PointBalanceDto(pointBalance.getId(), pointBalance.getPoint());
	}
}
