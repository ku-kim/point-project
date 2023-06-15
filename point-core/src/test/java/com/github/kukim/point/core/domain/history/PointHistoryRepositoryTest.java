package com.github.kukim.point.core.domain.history;

import com.github.kukim.point.core.CoreTestConfiguration;
import com.github.kukim.point.core.domain.type.EventDetailType;
import com.github.kukim.point.core.domain.type.EventType;
import java.time.LocalDateTime;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest
@ContextConfiguration(classes = {CoreTestConfiguration.class})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles({"db", "db-test"})
@DisplayName("PointHistory 클래스")
class PointHistoryRepositoryTest {

	@Autowired
	private PointHistoryRepository pointHistoryRepository;

	@Test
	@Transactional
	void testSave() {
		PointHistory history = new PointHistory("abcd", EventType.SAVE, EventDetailType.SAVE_EVENT,
			100L,
			1L, 1L, 1L, LocalDateTime.now(), 10L);

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
