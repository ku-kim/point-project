package com.github.kukim.point.core.domain.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public abstract class KeyGenerator {
	private KeyGenerator() {
		throw new IllegalStateException("Utility class");
	}

	public static String generateUUID() {
		LocalDateTime localDate = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");
		String now = localDate.format(formatter);

		return now + "-" + UUID.randomUUID().toString().substring(0, 8);
	}

}
