package com.github.kukim.point.worker.service;

import com.github.kukim.point.clients.aws.config.AwsSqsQueueProperties;
import com.github.kukim.point.core.domain.history.PointHistory;
import com.github.kukim.point.core.domain.history.PointHistoryRepository;
import com.github.kukim.point.core.domain.history.PointRemainHistory;
import com.github.kukim.point.core.domain.message.PointCacheMessage;
import com.github.kukim.point.core.domain.message.PointCancelMessage;
import com.github.kukim.point.core.domain.message.PointMessage;
import com.github.kukim.point.core.domain.point.Point;
import com.github.kukim.point.core.domain.point.PointRepository;
import com.github.kukim.point.core.domain.util.KeyGenerator;
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

		saveRedeemPointHistory(message, redeemPoint);

		sendPointCacheMessage(redeemPoint);
	}

	private void sendPointCacheMessage(Point redeemPoint) {
		PointCacheMessage pointCacheMessage = new PointCacheMessage(redeemPoint.getMemberId(), redeemPoint.getSavePoint());
		queueMessageSender.sendMessage(awsSqsQueueProperties.getPointCache(), pointCacheMessage);
	}

	private void saveRedeemPointHistory(PointMessage message, Point redeemPoint) {
		List<PointHistory> savePointHistoryList = new ArrayList<>();
		List<PointRemainHistory> remainPointHistoryList = pointHistoryRepository.findAllByEarnEventType(
			message.getMemberId());

		for (PointRemainHistory pointRemainHistory : remainPointHistoryList) {
			BigDecimal underFlowPoint = redeemPoint.deductPoint(pointRemainHistory.getRemainPoint());

			if (underFlowPoint.signum() == -1) {
				PointHistory redeemPointHistory = PointHistory.createRedeemHistoryBy(
					message.getMessageId(),
					message.getEventType(), message.getEventDetailType(),
					pointRemainHistory.getRemainPoint().multiply(new BigDecimal(-1)),
					pointRemainHistory.getEarnPointId(),
					redeemPoint.getSearchId(),
					redeemPoint.getExpirationDate(), redeemPoint.getMemberId());
				savePointHistoryList.add(redeemPointHistory);
				log.info("[point-worker][point-redeem] pointhistory 사용 업데이트 중: {}", redeemPointHistory);
			} else {
				PointHistory redeemPointHistory = PointHistory.createRedeemHistoryBy(message.getMessageId(),
					message.getEventType(), message.getEventDetailType(),
					underFlowPoint.multiply(new BigDecimal(-1)),
					pointRemainHistory.getEarnPointId(), redeemPoint.getSearchId(),
					redeemPoint.getExpirationDate(), redeemPoint.getMemberId());
				savePointHistoryList.add(redeemPointHistory);
				log.info("[point-worker][point-redeem] pointhistory 사용 업데이트 완료: {}", redeemPointHistory);
				break;
			}
		}

		pointHistoryRepository.saveAll(savePointHistoryList);
	}

	@Transactional
	public void redeemCancel(PointCancelMessage message) {
		saveRedeemCancelPointHistory(message);

		sendPointCacheMessage(message.toPoint());
	}

	private void saveRedeemCancelPointHistory(PointCancelMessage message) {
		List<PointHistory> redeemPointHistoryList = pointHistoryRepository.findAllByMemberIdAndOriginPointIdAndUseType(
			message.getMemberId(),
			message.getOriginEarnPointSearchId());

		List<PointHistory> redeemCancelPointHistoryList = new ArrayList<>();
		String cancelKey = "h-" + KeyGenerator.generateUUID();
		for (PointHistory pointHistory : redeemPointHistoryList) {

			PointHistory cancelPointHistory = new PointHistory(
				message.getMessageId(),
				message.getEventType(),
				message.getEventDetailType(),
				pointHistory.getSavePoint().multiply(new BigDecimal(-1)),
				cancelKey,
				pointHistory.getEarnPointId(),
				message.getOriginEarnPointSearchId(),
				pointHistory.getExpirationDate(),
				message.getMemberId());
			log.info("[point-worker][point-redeem-cancel] pointHistory 사용취소 업데이트 중: {}", cancelPointHistory);
			redeemCancelPointHistoryList.add(cancelPointHistory);
		}

		pointHistoryRepository.saveAll(redeemCancelPointHistoryList);
	}

}
