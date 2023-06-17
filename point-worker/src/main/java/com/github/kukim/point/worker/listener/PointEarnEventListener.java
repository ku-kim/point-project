package com.github.kukim.point.worker.listener;

import com.github.kukim.point.core.domain.message.PointMessage;
import com.github.kukim.point.worker.service.PointEarnWorkerFacade;
import io.awspring.cloud.messaging.listener.Acknowledgment;
import io.awspring.cloud.messaging.listener.SqsMessageDeletionPolicy;
import io.awspring.cloud.messaging.listener.annotation.SqsListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

/**
 * 포인트 적립 Listener
 */
@Slf4j
@Component
public class PointEarnEventListener {

	private final PointEarnWorkerFacade pointEarnWorkerFacade;

	public PointEarnEventListener(PointEarnWorkerFacade pointEarnWorkerFacade) {
		this.pointEarnWorkerFacade = pointEarnWorkerFacade;
	}

	@SqsListener(value = "${aws.sqs.queue.point-earn}", deletionPolicy = SqsMessageDeletionPolicy.NEVER)
	public void pointEarn(@Payload PointMessage pointMessage, Acknowledgment ack) {
		log.info("[point-worker][point-earn] 메시지 수신 성공. pointMessage= {}", pointMessage);
		try {
			pointEarnWorkerFacade.startEarn(pointMessage);
			ack.acknowledge();
			log.info("[point-worker][point-earn] 포인트 적립 성공: {}", pointMessage);
		} catch (Exception e) {
			log.error("[point-worker][point-earn] 포인트 적립 실패: " + pointMessage, e);
		}
	}
}
