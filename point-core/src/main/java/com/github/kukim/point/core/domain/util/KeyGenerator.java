package com.github.kukim.point.core.domain.util;

import java.util.UUID;

public abstract class KeyGenerator {
	private KeyGenerator() {
		throw new IllegalArgumentException("Utility class");
	}

	public static String generateUuid() {
		return UUID.randomUUID().toString();
	}

}
