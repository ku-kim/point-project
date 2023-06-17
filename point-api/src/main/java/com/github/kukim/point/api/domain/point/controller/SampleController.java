package com.github.kukim.point.api.domain.point.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.kukim.point.core.domain.message.PointMessage;
import com.github.kukim.point.core.domain.type.EventDetailType;
import com.github.kukim.point.core.domain.type.EventType;
import io.awspring.cloud.messaging.core.QueueMessagingTemplate;
import io.awspring.cloud.messaging.listener.Acknowledgment;
import io.awspring.cloud.messaging.listener.SqsMessageDeletionPolicy;
import io.awspring.cloud.messaging.listener.annotation.SqsListener;
import java.math.BigDecimal;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@Profile("local")
public class SampleController {

	@Autowired
	private QueueMessagingTemplate messagingTemplate;

	@Autowired
	private ObjectMapper objectMapper;

	@GetMapping("/test/send")
	public String save() {
		String queueName = "point-command";
		PointMessage pointMessage = PointMessage.create("0000001", EventType.SAVE,
			EventDetailType.SAVE_EVENT, BigDecimal.valueOf(100L), "로그인 적립",
			10L);
		messagingTemplate.convertAndSend(queueName, pointMessage);
		log.info("메시지를 보냈습니다.. message= {}", pointMessage);

		return "pass";
	}

	@SqsListener(value = "point-command", deletionPolicy = SqsMessageDeletionPolicy.NEVER)
	public void receive(@Payload PointMessage pointMessage, String message,
		@Header("SenderId") String senderId, @Headers Map<String, Object> header,
		Acknowledgment ack)
		throws JsonProcessingException {
		log.info("메시지를 받았습니다. message= {}", message);
		log.info("메시지를 받았습니다. payload= {}", pointMessage);

		log.info("[point-command][Queue] senderId: {}, message: {}", senderId, message);
		PointMessage messageObject = objectMapper.readValue(message, PointMessage.class);
		log.info("[point-command][Queue] messageObject: {}", messageObject);

		header.forEach((k, v) -> log.info("header key: {}, header value: {}", k, v));

		try {
			ack.acknowledge();
			log.info("[point-command] Success Ack");
		} catch (Exception e) {
			log.error("[point-command] Point Save Fail: " + message, e);
		}
	}

}
