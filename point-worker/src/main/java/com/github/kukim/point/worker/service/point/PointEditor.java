package com.github.kukim.point.worker.service.point;

import com.github.kukim.point.core.domain.point.Point;

public interface PointEditor {

	/**
	 * Point 엔티티 저장
	 * @param point 저장할 객체
	 * @return 저장된 객체
	 */
	Point save(Point point);
}
