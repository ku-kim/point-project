package com.github.kukim.point.worker.service.history;

import com.github.kukim.point.core.domain.history.PointHistory;
import com.github.kukim.point.core.domain.history.PointHistoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PointHistoryService implements PointHistoryFinder, PointHistoryEditor {

	private final PointHistoryRepository pointHistoryRepository;

	public PointHistoryService(PointHistoryRepository pointHistoryRepository) {
		this.pointHistoryRepository = pointHistoryRepository;
	}

	@Override
	public PointHistory save(PointHistory pointHistory) {
		return pointHistoryRepository.save(pointHistory);
	}
}
