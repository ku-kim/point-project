package com.github.kukim.point.worker.service;

import com.github.kukim.point.core.domain.message.PointCacheMessage;
import com.github.kukim.point.core.domain.point.PointBalance;
import com.github.kukim.point.core.domain.point.PointCacheRepository;
import com.github.kukim.point.core.domain.point.PointRepository;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class PointCacheRedisService implements PointCacheService{

	private final PointCacheRepository pointCacheRepository;
	private final PointRepository pointRepository;

	public PointCacheRedisService(PointCacheRepository pointCacheRepository,
		PointRepository pointRepository) {
		this.pointCacheRepository = pointCacheRepository;
		this.pointRepository = pointRepository;
	}


	@Override
	public void update(PointCacheMessage pointMessage) {
		Long memberId = pointMessage.getMemberId();

		PointBalance pointBalance = getPointCache(pointMessage, memberId);

		pointCacheRepository.save(pointBalance);
	}

	private PointBalance getPointCache(PointCacheMessage pointMessage, Long memberId) {
		Optional<PointBalance> pointCache = pointCacheRepository.findById(memberId);
		if (pointCache.isPresent()) {
			PointBalance cache = pointCache.get();
			cache.plus(pointMessage.getPoint());
			log.info("[point-worker][point-cache] cache Hit: {}", cache);
			return cache;
		}

		BigDecimal sumPoint = pointRepository.sumPointByMemberId(memberId);
		log.info("[point-worker][point-cache] cache miss: {}", sumPoint);
		return new PointBalance(memberId, sumPoint, LocalDateTime.now());
	}

}
