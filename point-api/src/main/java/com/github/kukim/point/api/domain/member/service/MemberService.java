package com.github.kukim.point.api.domain.member.service;

import com.github.kukim.point.core.domain.point.PointBalance;
import com.github.kukim.point.core.domain.point.PointCacheRepository;
import com.github.kukim.point.core.domain.point.PointRepository;
import com.github.kukim.point.core.domain.point.dto.PointBalanceDto;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class MemberService implements MemberFinder {

	private final PointRepository pointRepository;
	private final PointCacheRepository pointCacheRepository;

	public MemberService(PointRepository pointRepository, PointCacheRepository pointCacheRepository) {
		this.pointRepository = pointRepository;
		this.pointCacheRepository = pointCacheRepository;
	}

	public PointBalanceDto readBalance(Long memberId) {
		PointBalance cache;

		Optional<PointBalance> pointCache = pointCacheRepository.findById(memberId);
		if (pointCache.isPresent()) {
			cache = pointCache.get();
			log.info("[point-api] pointCache cache Hit: {}", cache);
		}

		BigDecimal sumPoint = pointRepository.sumPointByMemberId(memberId);
		cache = new PointBalance(memberId, sumPoint, LocalDateTime.now());
		log.info("[point-api] pointCache cache MISS: {}", cache);

		return PointBalanceDto.of(cache);
	}
}
