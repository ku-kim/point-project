package com.github.kukim.point.clients.aws.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(AwsSqsQueueProperties.class)
public class AwsResourceConfig {

}
