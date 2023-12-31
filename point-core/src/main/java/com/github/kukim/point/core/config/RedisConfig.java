package com.github.kukim.point.core.config;

import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

@Profile("local")
@Configuration
@EnableRedisRepositories(basePackages = {"com.github.kukim.point.core.domain"})
public class RedisConfig {

	private final RedisProperties redisProperties;


	public RedisConfig(RedisProperties redisProperties) {
		this.redisProperties = redisProperties;
	}

	@Bean
	public RedisConnectionFactory redisConnectionFactory() {
		RedisClusterConfiguration clusterConfiguration = new RedisClusterConfiguration();
		clusterConfiguration.clusterNode(
			redisProperties.getHost(), redisProperties.getPort());

		return new LettuceConnectionFactory(clusterConfiguration);
	}
}
