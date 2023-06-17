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
	private final String pointCommand;
	@NotEmpty
	private final String pointCommandDlq;

	public AwsSqsQueueProperties(String pointCommand, String pointCommandDlq) {
		this.pointCommand = pointCommand;
		this.pointCommandDlq = pointCommandDlq;
	}
}
