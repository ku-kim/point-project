package com.github.kukim.point.worker.service.point;

import com.github.kukim.point.core.domain.point.Point;
import com.github.kukim.point.core.domain.point.PointRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PointService implements PointFinder, PointEditor {

	private final PointRepository pointRepository;

	public PointService(PointRepository pointRepository) {
		this.pointRepository = pointRepository;
	}


	@Override
	public Point save(Point point) {
		return pointRepository.save(point);
	}

	@Override
	public boolean existsByMessageId(String messageId) {
		return pointRepository.existsByMessageId(messageId);
	}
}
