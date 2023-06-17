package com.github.kukim.point.worker.service.point;

public interface PointFinder {

	/**
	 * messageId 값이 존재하는지 확인합니다.
	 * @param messageId 메세지 아이디
	 * @return boolean 값
	 */
	boolean existsByMessageId(String messageId);
}
