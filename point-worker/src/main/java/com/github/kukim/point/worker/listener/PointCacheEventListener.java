package com.github.kukim.point.worker.listener;

import com.github.kukim.point.core.domain.message.PointCacheMessage;
import com.github.kukim.point.worker.service.PointCacheService;
import io.awspring.cloud.messaging.listener.Acknowledgment;
import io.awspring.cloud.messaging.listener.SqsMessageDeletionPolicy;
import io.awspring.cloud.messaging.listener.annotation.SqsListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

/**
 * 포인트 조회 Listener
 *
 * 원래는 worker들이 별도로 구분될 수 있으나 과제에선 한 곳에서 처리함
 */
@Slf4j
@Component
public class PointCacheEventListener {

	private final PointCacheService pointCacheService;

	public PointCacheEventListener(PointCacheService pointCacheService) {
		this.pointCacheService = pointCacheService;
	}


	@SqsListener(value = "${aws.sqs.queue.point-cache}", deletionPolicy = SqsMessageDeletionPolicy.NEVER)
	public void pointCache(@Payload PointCacheMessage pointMessage, Acknowledgment ack) {
		log.info("[point-worker][point-cache] 포인트 캐쉬 업데이트 수신 성공: pointMessage= {}", pointMessage);
		try {
			pointCacheService.update(pointMessage);
			ack.acknowledge();
			log.info("[point-worker][point-cache] 포인트 캐쉬 업데이트 성공: {}", pointMessage);
		} catch (Exception e) {
			log.error("[point-worker][point-cache] 포인트 캐쉬 업데이트 실패: " + pointMessage, e);
		}
	}
}
