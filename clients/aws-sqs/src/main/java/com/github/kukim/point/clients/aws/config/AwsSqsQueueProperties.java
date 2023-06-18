package com.github.kukim.point.clients.aws.config;

import javax.validation.constraints.NotEmpty;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.validation.annotation.Validated;

@Getter
@Validated
@ConstructorBinding
@ConfigurationProperties("aws.sqs.queue")
public class AwsSqsQueueProperties {

	@NotEmpty
	private final String pointEarn;
	@NotEmpty
	private final String pointEarnDlq;
	@NotEmpty
	private final String pointCache;

	public AwsSqsQueueProperties(String pointEarn, String pointEarnDlq, String pointCache) {
		this.pointEarn = pointEarn;
		this.pointEarnDlq = pointEarnDlq;
		this.pointCache = pointCache;
	}
}