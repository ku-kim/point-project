package com.github.kukim.point.worker.service;

import com.github.kukim.point.core.domain.message.PointCacheMessage;

public interface PointCacheService {

	/**
	 * PointCacheMessage 업데이트
	 * @param pointMessage 포인트 캐시 정보
	 */
	void update(PointCacheMessage pointMessage);
}
