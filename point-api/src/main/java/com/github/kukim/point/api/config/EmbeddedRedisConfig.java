package com.github.kukim.point.api.config;

import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import redis.embedded.Redis;
import redis.embedded.cluster.RedisCluster.Builder;

@Slf4j
@Profile("local")
@Order(1)
@Configuration
public class EmbeddedRedisConfig {

	private final RedisProperties redisProperties;

	public EmbeddedRedisConfig(RedisProperties redisProperties) {
		this.redisProperties = redisProperties;
	}

	private Redis redisServer;

	@PostConstruct
	public void start() {
		try {
			if (Objects.nonNull(redisServer) && redisServer.isActive()) {
				return;
			}

			redisServer = new Builder()
				.serverPorts(Arrays.asList(redisProperties.getPort(), 6390, 6391))
				.setting("maxmemory 128M") // for Windows: https://github.com/kstyrc/embedded-redis/issues/51
				.build();

			redisServer.start();
		} catch (Exception e) {
			log.error("Embedded Redis Start error.", e);
		}
		log.debug("Embedded Redis Start");
	}

	@PreDestroy
	public void destroy() {
		log.debug("Embedded Redis destroy");
		Optional.ofNullable(redisServer).ifPresent(Redis::stop);
	}
}
