package com.github.kukim.point.core.domain.type;

import lombok.Getter;

@Getter
public enum EventType {

	SAVE("적립"),
	USE("사용"),
	DISAPPEAR("소멸");

	private final String title;

	EventType(String title) {
		this.title = title;
	}
}
