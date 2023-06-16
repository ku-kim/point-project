package com.github.kukim.point.core.domain.util;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("KeyGenerator 클래스")
class KeyGeneratorTest {

	@Test
	void testGenerateUUID() {
		LocalDateTime localDate = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
		String today = localDate.format(formatter);

		String result = KeyGenerator.generateUUID();

		assertThat(result).startsWith(today);
	}
}
