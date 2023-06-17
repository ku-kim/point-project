package com.github.kukim.point.core.domain.point;

import com.github.kukim.point.core.exception.PointCacheNegativeNumberException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import javax.persistence.Id;
import lombok.Getter;
import org.springframework.data.redis.core.RedisHash;

@Getter
@RedisHash(value = "member:point:balance", timeToLive = 86400)
public class PointCache {

	@Id
	private Long id;
	private BigDecimal point;
	private LocalDateTime updatedAt;

	public PointCache(Long id, BigDecimal point, LocalDateTime updatedAt) {
		this.id = id;
		this.point = point;
		this.updatedAt = updatedAt;
	}

	public void plus(BigDecimal rightValue) {
		BigDecimal result = this.point.add(rightValue);

		if (result.signum() == -1) {
			throw new PointCacheNegativeNumberException();
		}

		this.point = result;
		this.updatedAt = LocalDateTime.now();
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
