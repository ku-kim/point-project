package com.github.kukim.point.worker.service;

import com.github.kukim.point.clients.aws.config.AwsSqsQueueProperties;
import com.github.kukim.point.core.domain.history.PointHistory;
import com.github.kukim.point.core.domain.message.PointCacheMessage;
import com.github.kukim.point.core.domain.message.PointMessage;
import com.github.kukim.point.core.domain.point.Point;
import com.github.kukim.point.worker.infrastructure.message.QueueMessageSender;
import com.github.kukim.point.worker.service.history.PointHistoryEditor;
import com.github.kukim.point.worker.service.history.PointHistoryFinder;
import com.github.kukim.point.worker.service.point.PointEditor;
import com.github.kukim.point.worker.service.point.PointFinder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
public class PointEarnWorkerFacade {

	private final PointFinder pointFinder;
	private final PointEditor pointEditor;
	private final PointHistoryFinder pointHistoryFinder;
	private final PointHistoryEditor pointHistoryEditor;
	private final QueueMessageSender queueMessageSender;
	private final AwsSqsQueueProperties awsSqsQueueProperties;

	public PointEarnWorkerFacade(PointFinder pointFinder, PointEditor pointEditor,
		PointHistoryFinder pointHistoryFinder, PointHistoryEditor pointHistoryEditor,
		QueueMessageSender queueMessageSender, AwsSqsQueueProperties awsSqsQueueProperties) {
		this.pointFinder = pointFinder;
		this.pointEditor = pointEditor;
		this.pointHistoryFinder = pointHistoryFinder;
		this.pointHistoryEditor = pointHistoryEditor;
		this.queueMessageSender = queueMessageSender;
		this.awsSqsQueueProperties = awsSqsQueueProperties;
	}

	/**
	 * 포인트 적립 시작
	 * @param pointMessage 포인트 적립 상세 내용
	 */
	@Transactional
	public void startEarn(PointMessage pointMessage) {
		Point point = pointMessage.toPoint();
		PointHistory pointHistory = point.toEarnPointHistory();

		if (pointFinder.existsByMessageId(point.getMessageId())) {
			log.info("[point-worker][point-earn] 중복 포인트 적립: {}", point);
			return;
		}

		pointEditor.save(point);
		pointHistoryEditor.save(pointHistory);
		log.info("[point-worker][point-earn] 포인트 적립 저장 성공 id: {}, searchId: {}", point.getId(), point.getSearchId());

		// TODO: Redis queue 전송
		PointCacheMessage message = new PointCacheMessage(point.getMemberId(), point.getSavePoint());
		queueMessageSender.sendMessage(awsSqsQueueProperties.getPointCache(), message);

	}
}
