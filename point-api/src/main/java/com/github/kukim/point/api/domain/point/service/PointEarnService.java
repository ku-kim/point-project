package com.github.kukim.point.api.domain.point.service;

import com.github.kukim.point.api.domain.point.controller.dto.PointEarnCommand;
import com.github.kukim.point.api.infrastructure.message.QueueMessageSender;
import com.github.kukim.point.clients.aws.config.AwsSqsQueueProperties;
import com.github.kukim.point.core.domain.message.PointMessage;
import com.github.kukim.point.core.domain.message.dto.PointMessageDto;
import org.springframework.stereotype.Service;

@Service
public class PointEarnService {

	private final QueueMessageSender queueMessageSender;
	private final AwsSqsQueueProperties awsSqsQueueProperties;

	public PointEarnService(QueueMessageSender queueMessageSender,
		AwsSqsQueueProperties awsSqsQueueProperties) {
		this.queueMessageSender = queueMessageSender;
		this.awsSqsQueueProperties = awsSqsQueueProperties;
	}

	public PointMessageDto earn(Long memberId, PointEarnCommand command) {
		PointMessage message = PointMessage.create(command.getTradeId(), command.getEventType(),
			command.getEventDetailType(), command.getEarnPoint(), command.getDescription(),
			memberId);
		queueMessageSender.sendMessage(awsSqsQueueProperties.getPointEarn(),
			message);

		return PointMessageDto.of(message);
	}
}
