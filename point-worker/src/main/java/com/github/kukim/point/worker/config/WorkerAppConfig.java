package com.github.kukim.point.worker.config;

import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.sqs.AmazonSQSAsync;
import com.amazonaws.services.sqs.AmazonSQSAsyncClientBuilder;
import com.github.kukim.point.clients.aws.config.AwsResourceConfig;
import com.github.kukim.point.core.config.CoreConfig;
import io.awspring.cloud.messaging.core.QueueMessagingTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

@Configuration
@Import({
	CoreConfig.class,
	AwsResourceConfig.class
})
public class WorkerAppConfig {

	@Profile("local")
	public static class LocalAwsConfig {
		@Bean
		@Primary
		public AmazonSQSAsync amazonSQSAsync() {
			return AmazonSQSAsyncClientBuilder.standard()
				.withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration("http://localhost:9324", "ap-northeast-2"))
				.build();
		}

		@Bean
		public QueueMessagingTemplate queueMessagingTemplate() {
			return new QueueMessagingTemplate(amazonSQSAsync());
		}
	}

}
