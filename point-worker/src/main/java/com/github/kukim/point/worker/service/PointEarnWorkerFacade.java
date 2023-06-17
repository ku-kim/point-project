package com.github.kukim.point.worker.service;

import com.github.kukim.point.core.domain.history.PointHistory;
import com.github.kukim.point.core.domain.message.PointMessage;
import com.github.kukim.point.core.domain.point.Point;
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

	public PointEarnWorkerFacade(PointFinder pointFinder, PointEditor pointEditor,
		PointHistoryFinder pointHistoryFinder, PointHistoryEditor pointHistoryEditor) {
		this.pointFinder = pointFinder;
		this.pointEditor = pointEditor;
		this.pointHistoryFinder = pointHistoryFinder;
		this.pointHistoryEditor = pointHistoryEditor;
	}

	@Transactional
	public void startEarn(PointMessage pointMessage) {
		Point point = pointMessage.toPoint();
		PointHistory pointHistory = point.toEarnPointHistory();

		if (pointFinder.existsByMessageId(point.getMessageId())) {
			log.info("[point-worker][point-command] 중복 포인트 적립: {}", point);
			return;
		}

		pointEditor.save(point);
		pointHistoryEditor.save(pointHistory);
		log.info("[point-worker][point-command] 포인트 적립 저장 성공 id: {}, searchId: {}", point.getId(), point.getSearchId());
		// TODO: Redis queue 전송
	}
}
