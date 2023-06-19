package com.github.kukim.point.clients.aws.config;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest
@ActiveProfiles({"aws-sqs", "aws-sqs-consumer-local"})
@ContextConfiguration(classes = AwsResourceConfig.class)
class AwsSqsQueuePropertiesTest {

	@Autowired
	AwsSqsQueueProperties awsSqsQueueProperties;

	@Test
	void testAwsSqsQueueProperties() {
		assertThat(awsSqsQueueProperties.getPointEarn()).isNotNull();
		assertThat(awsSqsQueueProperties.getPointEarnDlq()).isNotNull();
		assertThat(awsSqsQueueProperties.getPointRedeem()).isNotNull();
		assertThat(awsSqsQueueProperties.getPointCancel()).isNotNull();
		assertThat(awsSqsQueueProperties.getPointCache()).isNotNull();
	}
}
