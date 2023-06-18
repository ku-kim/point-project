package com.github.kukim.point.api.domain.member.controller.dto;

import com.github.kukim.point.core.domain.point.PointBalance;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@ToString
public class MemberPointBalanceDto {

	private Long memberId;
	private BigDecimal point;

	private MemberPointBalanceDto(Long memberId, BigDecimal point) {
		this.memberId = memberId;
		this.point = point;
	}

	public static MemberPointBalanceDto of(PointBalance pointBalance) {
		return new MemberPointBalanceDto(pointBalance.getId(), pointBalance.getPoint());
	}

	public PointBalance toEntity() {
		return new PointBalance(this.memberId, this.point, LocalDateTime.now());
	}
}
