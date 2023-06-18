package com.github.kukim.point.core.domain.history;

import java.math.BigDecimal;
import javax.validation.constraints.NotEmpty;
import lombok.Getter;

@Getter
@NotEmpty
public class PointRemainHistory {

	private String earnPointId;
	private BigDecimal remainPoint;

	public PointRemainHistory(String earnPointId, BigDecimal remainPoint) {
		this.earnPointId = earnPointId;
		this.remainPoint = remainPoint;
	}
}
