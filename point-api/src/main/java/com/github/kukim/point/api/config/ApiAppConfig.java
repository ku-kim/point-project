package com.github.kukim.point.api.config;

import com.github.kukim.point.core.config.CoreConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(CoreConfig.class)
public class ApiAppConfig {

}
