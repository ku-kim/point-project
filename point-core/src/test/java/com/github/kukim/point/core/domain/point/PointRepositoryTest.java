package com.github.kukim.point.core.domain.point;

import com.github.kukim.point.core.CoreTestConfiguration;
import com.github.kukim.point.core.annotation.InMemoryDBJpaTest;
import com.github.kukim.point.core.domain.type.EventDetailType;
import com.github.kukim.point.core.domain.type.EventType;
import com.github.kukim.point.core.domain.util.KeyGenerator;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Transactional;

@InMemoryDBJpaTest
@DisplayName("PointRepository 통합테스트")
@ContextConfiguration(classes = {CoreTestConfiguration.class})
class PointRepositoryTest {

	@Autowired
	private PointRepository pointRepository;

	@Test
	@Transactional
	void testSave() {
		Point point = new Point("tradeNo-1234", KeyGenerator.generateUUID(), EventType.SAVE, EventDetailType.SAVE_EVENT, BigDecimal.valueOf(100L), "로그인 이벤트 적립",
			LocalDateTime.now(), 10L);

		pointRepository.save(point);

		SoftAssertions.assertSoftly(softly -> {
			softly.assertThat(point.getId()).isNotZero();
			softly.assertThat(point.getEventType()).isEqualTo(EventType.SAVE);
			softly.assertThat(point.getEventDetailType()).isEqualTo(EventDetailType.SAVE_EVENT);
			softly.assertThat(point.getCreatedDate()).isNotNull();
			softly.assertThat(point.getModifiedDate()).isNotNull();
		});
	}
}
