package com.github.kukim.point.core.domain.point;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.github.kukim.point.core.domain.type.EventDetailType;
import com.github.kukim.point.core.domain.type.EventType;
import com.github.kukim.point.core.domain.util.KeyGenerator;
import com.github.kukim.point.core.exception.UnsupportedRedeemPointEventTypeException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

@DisplayName("Point 클래스")
class PointTest {

	@Nested
	@DisplayName("DeductPoint 메서드")
	class Describe_DeductPoint {

		@Nested
		@DisplayName("만약 Point보다 많은 포인트를 사용한다면")
		class Context_with_fullyPoint {

			@ParameterizedTest
			@MethodSource("provideValidPoint")
			@DisplayName("Point 객체의 잔액은 0원이다")
			void It_returns_a_zero_remainPoint(Point point) {
				BigDecimal maxRedeemPoint = new BigDecimal(Integer.MAX_VALUE);

				point.deductPoint(maxRedeemPoint);

				assertThat(point.getRemainPoint()).isEqualTo(new BigDecimal(0));
			}

			private static Stream<Arguments> provideValidPoint() {
				return Stream.of(
					Arguments.of(new Point("tradeid-1", KeyGenerator.generateUUID(), EventType.USE,
						EventDetailType.USE_BUY, BigDecimal.valueOf(-100L), "로그인 적립",
						LocalDateTime.now().plusDays(1), 1L)),
					Arguments.of(new Point("tradeid-1", KeyGenerator.generateUUID(), EventType.USE,
						EventDetailType.USE_BUY, BigDecimal.valueOf(-500L), "로그인 적립",
						LocalDateTime.now().plusDays(1), 1L)),
					Arguments.of(new Point("tradeid-1", KeyGenerator.generateUUID(), EventType.USE,
						EventDetailType.USE_BUY, BigDecimal.valueOf(-1000L), "로그인 적립",
						LocalDateTime.now().plusDays(1), 1L))
				);
			}
		}

		@Nested
		@DisplayName("만약 Point보다 적은 포인트를 사용한다면")
		class Context_with_lessPoint {

			@ParameterizedTest
			@MethodSource("provideLessPoint")
			@DisplayName("Point 객체의 잔액이 남는다.")
			void It_returns_a_zero_remainPoint(Point point, BigDecimal earnPoint, BigDecimal remainPoint) {
				point.deductPoint(earnPoint);

				assertThat(point.getRemainPoint()).isEqualTo(remainPoint);
			}

			private static Stream<Arguments> provideLessPoint() {
				return Stream.of(
					Arguments.of(new Point("tradeid-1", KeyGenerator.generateUUID(), EventType.USE,
						EventDetailType.USE_BUY, BigDecimal.valueOf(-100L), "로그인 적립",
						LocalDateTime.now().plusDays(1), 1L), new BigDecimal(50), new BigDecimal(-50)),
					Arguments.of(new Point("tradeid-1", KeyGenerator.generateUUID(), EventType.USE,
						EventDetailType.USE_BUY, BigDecimal.valueOf(-500L), "로그인 적립",
						LocalDateTime.now().plusDays(1), 1L), new BigDecimal(50), new BigDecimal(-450)),
					Arguments.of(new Point("tradeid-1", KeyGenerator.generateUUID(), EventType.USE,
						EventDetailType.USE_BUY, BigDecimal.valueOf(-1000L), "로그인 적립",
						LocalDateTime.now().plusDays(1), 1L), new BigDecimal(999), new BigDecimal(-1))
				);
			}
		}

		@Nested
		@DisplayName("만약 유효하지 않은 Point 타입이 주어진경우라면")
		class Context_with_invalid_eventType {

			@ParameterizedTest
			@MethodSource("provideInvalidEventType")
			@DisplayName("예외를 발생시킨다")
			void It_throws_exception(Point point) {
				assertThatThrownBy(() -> point.deductPoint(new BigDecimal(30)))
					.isInstanceOf(UnsupportedRedeemPointEventTypeException.class);
			}

			private static Stream<Arguments> provideInvalidEventType() {
				return Stream.of(
					Arguments.of(new Point("tradeid-1", KeyGenerator.generateUUID(), EventType.SAVE,
						EventDetailType.SAVE_EVENT, BigDecimal.valueOf(-100L), "로그인 적립",
						LocalDateTime.now().plusDays(1), 1L)),
					Arguments.of(new Point("tradeid-1", KeyGenerator.generateUUID(), EventType.DISAPPEAR,
						EventDetailType.DISAPPEAR_EXPIRATION, BigDecimal.valueOf(-100L), "로그인 적립",
						LocalDateTime.now().plusDays(1), 1L))
				);
			}
		}
	}
}
