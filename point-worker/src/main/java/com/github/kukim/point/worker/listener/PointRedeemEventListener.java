package com.github.kukim.point.worker.listener;

import com.github.kukim.point.core.domain.message.PointMessage;
import com.github.kukim.point.worker.service.PointRedeemService;
import io.awspring.cloud.messaging.listener.Acknowledgment;
import io.awspring.cloud.messaging.listener.SqsMessageDeletionPolicy;
import io.awspring.cloud.messaging.listener.annotation.SqsListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class PointRedeemEventListener {

	private final PointRedeemService pointRedeemService;

	public PointRedeemEventListener(PointRedeemService pointRedeemService) {
		this.pointRedeemService = pointRedeemService;
	}


	@SqsListener(value = "${aws.sqs.queue.point-redeem}", deletionPolicy = SqsMessageDeletionPolicy.NEVER)
	public void pointCache(@Payload PointMessage pointMessage, Acknowledgment ack) {
		log.info("[point-worker][point-redeem] 포인트 사용 업데이트 수신 성공: pointMessage= {}", pointMessage);
		try {
			pointRedeemService.redeem(pointMessage);
			ack.acknowledge();
			log.info("[point-worker][point-redeem] 포인트 사용 history 업데이트 성공: {}", pointMessage);
		} catch (Exception e) {
			log.error("[point-worker][point-redeem] 포인트 사용 history 업데이트 실패:" + pointMessage, e);
		}
	}
}
