package com.github.kukim.point.worker.service.history;

import com.github.kukim.point.core.domain.history.PointHistory;

public interface PointHistoryEditor {


	/**
	 * PointHistory 엔티티를 저장합니다.
	 *
	 * @param pointHistory 저장할 객체
	 * @return 저장된 객체
	 */
	PointHistory save(PointHistory pointHistory);
}
