package com.github.kukim.point.core.domain.point;

import com.github.kukim.point.core.exception.PointBalanceNegativeNumberException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import javax.persistence.Id;
import lombok.Getter;
import org.springframework.data.redis.core.RedisHash;

@Getter
@RedisHash(value = "member:point:balance", timeToLive = 86400)
public class PointBalance {

	@Id
	private Long id;
	private BigDecimal point;
	private LocalDateTime updatedAt;

	public PointBalance(Long id, BigDecimal point, LocalDateTime updatedAt) {
		this.id = id;
		checkNegativePoint(point);
		this.point = point;
		this.updatedAt = updatedAt;
	}

	public void plus(BigDecimal rightValue) {
		BigDecimal result = this.point.add(rightValue);

		checkNegativePoint(result);

		this.point = result;
		this.updatedAt = LocalDateTime.now();
	}

	private static void checkNegativePoint(BigDecimal point) {
		if (point.signum() == -1) {
			throw new PointBalanceNegativeNumberException();
		}
	}

	@Override
	public String toString() {
		return "PointCache{" +
			"id=" + id +
			", point=" + point +
			", updatedAt=" + updatedAt +
			'}';
	}
}
