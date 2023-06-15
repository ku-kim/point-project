package com.github.kukim.point.core.domain.message;

import com.github.kukim.point.core.domain.type.EventDetailType;
import com.github.kukim.point.core.domain.type.EventType;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;

class PointMessageTest {

	@Test
	void testConstructor() {
		PointMessage pointMessage = PointMessage.create("0000001", EventType.SAVE,
			EventDetailType.SAVE_EVENT, 100L, "로그인 적립",
			10L);

		SoftAssertions.assertSoftly(softly -> {
			softly.assertThat(pointMessage.getMessageId()).isNotNull();
			softly.assertThat(pointMessage.getEventType()).isEqualTo(EventType.SAVE);
			softly.assertThat(pointMessage.getEventDetailType()).isEqualTo(EventDetailType.SAVE_EVENT);
		});
	}
}
