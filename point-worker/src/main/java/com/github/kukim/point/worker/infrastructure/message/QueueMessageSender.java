package com.github.kukim.point.worker.infrastructure.message;

import com.github.kukim.point.worker.exception.QueueMessageException;
import io.awspring.cloud.messaging.core.QueueMessagingTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class QueueMessageSender {

	private final QueueMessagingTemplate queueMessagingTemplate;

	public QueueMessageSender(QueueMessagingTemplate queueMessagingTemplate) {
		this.queueMessagingTemplate = queueMessagingTemplate;
	}

	public void sendMessage(String queueName, Object message) {
		try {
			queueMessagingTemplate.convertAndSend(queueName, message);
			log.info("[point-worker][queue-message]메시지 전송 성공: {}", message);
		} catch (Exception e) {
			log.error("[point-worker][queue-message]메시지 전송 실패: {}", message, e);
			throw new QueueMessageException(e);
		}
	}
}
