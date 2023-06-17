package com.github.kukim.point.api.config;

import com.github.kukim.point.clients.aws.config.AwsResourceConfig;
import com.github.kukim.point.core.config.CoreConfig;
import com.github.kukim.point.core.config.RedisConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({
	CoreConfig.class,
	AwsResourceConfig.class,
	RedisConfig.class
})
public class ApiAppConfig {

}
