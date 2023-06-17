package com.github.kukim.point.core.domain.history;

import com.github.kukim.point.core.CoreTestConfiguration;
import com.github.kukim.point.core.annotation.InMemoryDBJpaTest;
import com.github.kukim.point.core.domain.type.EventDetailType;
import com.github.kukim.point.core.domain.type.EventType;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Transactional;

@InMemoryDBJpaTest
@ContextConfiguration(classes = {CoreTestConfiguration.class})
@DisplayName("PointHistory Repository 통합테스트")
class PointHistoryRepositoryTest {

	@Autowired
	private PointHistoryRepository pointHistoryRepository;

	@Test
	@Transactional
	@DisplayName("pointHistory repository save 확인")
	void testSave() {
		PointHistory history = new PointHistory("abcd", EventType.SAVE, EventDetailType.SAVE_EVENT,
			BigDecimal.valueOf(100L),
			null, null, null, LocalDateTime.now(), 10L);

		pointHistoryRepository.save(history);

		SoftAssertions.assertSoftly(softly -> {
			softly.assertThat(history.getId()).isNotZero();
			softly.assertThat(history.getEventType()).isEqualTo(EventType.SAVE);
			softly.assertThat(history.getEventDetailType()).isEqualTo(EventDetailType.SAVE_EVENT);
			softly.assertThat(history.getCreatedDate()).isNotNull();
			softly.assertThat(history.getModifiedDate()).isNotNull();
		});
	}
}
