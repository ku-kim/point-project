package com.github.kukim.point.worker.service;

import com.github.kukim.point.clients.aws.config.AwsSqsQueueProperties;
import com.github.kukim.point.core.domain.history.PointHistory;
import com.github.kukim.point.core.domain.history.PointHistoryRepository;
import com.github.kukim.point.core.domain.history.PointRemainHistory;
import com.github.kukim.point.core.domain.message.PointCacheMessage;
import com.github.kukim.point.core.domain.message.PointMessage;
import com.github.kukim.point.core.domain.point.Point;
import com.github.kukim.point.core.domain.point.PointRepository;
import com.github.kukim.point.worker.exception.NotFoundPointReceiptException;
import com.github.kukim.point.worker.infrastructure.message.QueueMessageSender;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class PointRedeemWorkerFacade {

	private final PointHistoryRepository pointHistoryRepository;
	private final PointRepository pointRepository;
	private final QueueMessageSender queueMessageSender;
	private final AwsSqsQueueProperties awsSqsQueueProperties;

	public PointRedeemWorkerFacade(PointHistoryRepository pointHistoryRepository,
		PointRepository pointRepository, QueueMessageSender queueMessageSender,
		AwsSqsQueueProperties awsSqsQueueProperties) {
		this.pointHistoryRepository = pointHistoryRepository;
		this.pointRepository = pointRepository;
		this.queueMessageSender = queueMessageSender;
		this.awsSqsQueueProperties = awsSqsQueueProperties;
	}

	@Transactional
	public void redeem(PointMessage message) {
		Point redeemPoint = pointRepository.findByTradeId(message.getTradeNo())
			.orElseThrow(NotFoundPointReceiptException::new);

		redeemPointHistory(message, redeemPoint);

		sendPointCacheMessage(redeemPoint);
	}

	private void sendPointCacheMessage(Point redeemPoint) {
		PointCacheMessage pointCacheMessage = new PointCacheMessage(redeemPoint.getMemberId(), redeemPoint.getSavePoint());
		queueMessageSender.sendMessage(awsSqsQueueProperties.getPointCache(), pointCacheMessage);
	}

	private void redeemPointHistory(PointMessage message, Point redeemPoint) {
		List<PointHistory> savePointHistory = new ArrayList<>();
		List<PointRemainHistory> pointRemainHistories = pointHistoryRepository.findAllByEarnEventType(
			message.getMemberId());

		for (PointRemainHistory pointRemainHistory : pointRemainHistories) {
			BigDecimal underFlowPoint = redeemPoint.deductPoint(pointRemainHistory.getRemainPoint());

			if (underFlowPoint.signum() == -1) {
				PointHistory redeemPointHistory = PointHistory.createRedeemBy(
					message.getMessageId(),
					message.getEventType(), message.getEventDetailType(),
					pointRemainHistory.getRemainPoint().multiply(new BigDecimal(-1)),
					pointRemainHistory.getEarnPointId(),
					redeemPoint.getSearchId(),
					redeemPoint.getExpirationDate(), redeemPoint.getMemberId());
				savePointHistory.add(redeemPointHistory);
				log.info("[point-worker][point-redeem] pointhistory 사용 업데이트 중: {}", redeemPointHistory);
			} else {
				PointHistory redeemPointHistory = PointHistory.createRedeemBy(message.getMessageId(),
					message.getEventType(), message.getEventDetailType(),
					underFlowPoint.multiply(new BigDecimal(-1)),
					pointRemainHistory.getEarnPointId(), redeemPoint.getSearchId(),
					redeemPoint.getExpirationDate(), redeemPoint.getMemberId());
				savePointHistory.add(redeemPointHistory);
				log.info("[point-worker][point-redeem] pointhistory 사용 업데이트 완료: {}", redeemPointHistory);
				break;
			}
		}

		pointHistoryRepository.saveAll(savePointHistory);
	}
}
