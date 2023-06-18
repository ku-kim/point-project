package com.github.kukim.point.api.domain.member.service;

import com.github.kukim.point.api.domain.member.controller.dto.MemberPointBalanceDto;
import com.github.kukim.point.api.domain.member.controller.dto.MemberPointHistoryDto;
import com.github.kukim.point.api.exception.SortUnsupportedOperationException;
import com.github.kukim.point.core.domain.point.Point;
import com.github.kukim.point.core.domain.point.PointBalance;
import com.github.kukim.point.core.domain.point.PointCacheRepository;
import com.github.kukim.point.core.domain.point.PointRepository;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class MemberService implements MemberFinder {

	private final PointRepository pointRepository;
	private final PointCacheRepository pointCacheRepository;

	public MemberService(PointRepository pointRepository,
		PointCacheRepository pointCacheRepository) {
		this.pointRepository = pointRepository;
		this.pointCacheRepository = pointCacheRepository;
	}

	@Override
	@Transactional(readOnly = true)
	public MemberPointBalanceDto readBalance(Long memberId) {
		PointBalance pointBalance;

		Optional<PointBalance> curPointBalance = pointCacheRepository.findById(memberId);
		if (curPointBalance.isPresent()) {
			pointBalance = curPointBalance.get();
			log.info("[point-api] curPointBalance cache Hit: {}", pointBalance);
		}

		BigDecimal sumPoint = pointRepository.sumPointByMemberId(memberId);
		pointBalance = new PointBalance(memberId, sumPoint, LocalDateTime.now());
		log.info("[point-api] curPointBalance cache MISS: {}", pointBalance);

		return MemberPointBalanceDto.of(pointBalance);
	}

	@Override
	@Transactional(readOnly = true)
	public MemberPointBalanceDto readBalanceNoCache(Long memberId) {
		PointBalance pointBalance;

		BigDecimal sumPoint = pointRepository.sumPointByMemberId(memberId);
		pointBalance = new PointBalance(memberId, sumPoint, LocalDateTime.now());
		log.info("[point-api] curPointBalance real DB: {}", pointBalance);

		return MemberPointBalanceDto.of(pointBalance);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<MemberPointHistoryDto> readAllPointHistory(Long memberId, Pageable pageable) {
		if (pageable.getSort().isSorted()) {
			throw new SortUnsupportedOperationException();
		}
		Page<Point> points = pointRepository.findAllByMemberIdEventTypeNotCancel(memberId,
			pageable);

		return points.map(MemberPointHistoryDto::of);
	}
}
