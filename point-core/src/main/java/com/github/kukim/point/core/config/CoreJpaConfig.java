package com.github.kukim.point.core.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaAuditing
@EntityScan(basePackages = {"com.github.kukim.point.core.domain"})
@EnableJpaRepositories(basePackages = {"com.github.kukim.point.core.domain"})
public class CoreJpaConfig {

}
