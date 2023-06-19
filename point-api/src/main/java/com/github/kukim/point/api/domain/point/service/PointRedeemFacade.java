package com.github.kukim.point.api.domain.point.service;

import com.github.kukim.point.api.domain.member.service.MemberFinder;
import com.github.kukim.point.api.domain.point.controller.dto.PointCancelCommand;
import com.github.kukim.point.api.domain.point.controller.dto.PointRedeemCommand;
import com.github.kukim.point.api.exception.NotFoundPointByMemberIdAndTradeId;
import com.github.kukim.point.api.infrastructure.message.QueueMessageSender;
import com.github.kukim.point.clients.aws.config.AwsSqsQueueProperties;
import com.github.kukim.point.core.domain.message.PointCancelMessage;
import com.github.kukim.point.core.domain.message.PointMessage;
import com.github.kukim.point.core.domain.message.dto.PointCancelMessageDto;
import com.github.kukim.point.core.domain.message.dto.PointMessageDto;
import com.github.kukim.point.core.domain.point.Point;
import com.github.kukim.point.core.domain.point.PointBalance;
import com.github.kukim.point.core.domain.point.PointRepository;
import com.github.kukim.point.core.domain.type.EventDetailType;
import com.github.kukim.point.core.domain.type.EventType;
import java.math.BigDecimal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
public class PointRedeemFacade {

	private final MemberFinder memberFinder;
	private final PointRepository pointRepository;
	private final QueueMessageSender queueMessageSender;
	private final AwsSqsQueueProperties awsSqsQueueProperties;

	public PointRedeemFacade(MemberFinder memberFinder, PointRepository pointRepository,
		QueueMessageSender queueMessageSender, AwsSqsQueueProperties awsSqsQueueProperties) {
		this.memberFinder = memberFinder;
		this.pointRepository = pointRepository;
		this.queueMessageSender = queueMessageSender;
		this.awsSqsQueueProperties = awsSqsQueueProperties;
	}

	@Transactional
	public PointCancelMessageDto redeemCancel(Long memberId, PointCancelCommand command) {
		log.info("memberId: {}, tradeId: {}", memberId, command);
		Point redeemPoint = pointRepository.findRedeemPointByMemberIdAndTradeId(memberId, command.getTradeId())
			.orElseThrow(NotFoundPointByMemberIdAndTradeId::new);

		PointCancelMessage message = PointCancelMessage.create(
			redeemPoint.getSearchId(),
			redeemPoint.getTradeId(),
			EventType.CANCEL,
			EventDetailType.USE_CANCEL,
			redeemPoint.getSavePoint().multiply(new BigDecimal(-1)),
			redeemPoint.getDescription(),
			redeemPoint.getMemberId());

		redeemPoint.cancel();
		Point cancelPoint = message.toPoint();
		pointRepository.save(cancelPoint);
		pointRepository.save(redeemPoint);
		log.info("[point-api][cancel] 포인트 사용취소 DB 업데이트 - 취소 포인트 {}", cancelPoint);

		queueMessageSender.sendMessage(awsSqsQueueProperties.getPointCancel(), message);

		return PointCancelMessageDto.of(message);
	}

	@Transactional
	public PointMessageDto redeem(Long memberId, PointRedeemCommand command) {
		// 1. 포인트 사용 유효성 검증 (실제 DB 확인)
		PointBalance memberPointBalance = memberFinder.readBalanceNoCache(memberId).toEntity();
		memberPointBalance.checkPointAvailability(command.getRedeemPoint());

		// 2. 포인트 사용 DB 업데이트 (worker 에서는 실제 point 순서대로 사용, 먼저 point에 사용 적용)
		PointMessage message = PointMessage.create(command.getTradeId(), command.getEventType(),
			command.getEventDetailType(), command.getRedeemPoint(), command.getDescription(),
			memberId);
		Point point = message.toPoint();
		pointRepository.save(point);
		log.info("[point-api][redeem] 포인트 사용 DB 업데이트 - 현재 자산:{}, 사용포인트 {}", memberPointBalance, point);

		// 3. 포인트 사용 워커에 전달 (포인트 워커에서 히스토리 로직)
		queueMessageSender.sendMessage(awsSqsQueueProperties.getPointRedeem(), message);

		return PointMessageDto.of(message);
	}
}
