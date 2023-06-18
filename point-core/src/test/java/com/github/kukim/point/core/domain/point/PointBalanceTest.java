package com.github.kukim.point.core.domain.point;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.github.kukim.point.core.exception.PointBalanceNegativeNumberException;
import java.math.BigDecimal;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

@DisplayName("PointBalance 클래스")
class PointBalanceTest {

	@DisplayName("만약 PointBalance 생성자의 point가 음수라면 예외를 발생시킵니다.")
	@ParameterizedTest
	@ValueSource(ints = {-1, -2, -900, Integer.MIN_VALUE})
	void testConstructor(int value) {
		assertThatThrownBy(() -> new PointBalance(null, new BigDecimal(value), null))
			.isInstanceOf(PointBalanceNegativeNumberException.class);
	}

	@DisplayName("만약 PointBalance.plus 메서드 실행 시 음수가 아니라면, 합 연산을 합니다. ")
	@ParameterizedTest
	@MethodSource("providePlusCases")
	void testPlus(PointBalance pointBalance, BigDecimal rightValue, BigDecimal expected) {
		pointBalance.plus(rightValue);

		assertThat(pointBalance.getPoint()).isEqualTo(expected);
	}

	@DisplayName("만약 PointBalance.plus 메서드 실행 시 음수라면 예외를 발생시킵니다. ")
	@Test
	void testPlusException() {
		PointBalance pointBalance = new PointBalance(null, new BigDecimal(10), null);

		assertThatThrownBy(() -> pointBalance.plus(new BigDecimal(-1000)))
			.isInstanceOf(PointBalanceNegativeNumberException.class);
	}

	private static Stream<Arguments> providePlusCases() {
		return Stream.of(
			Arguments.of(new PointBalance(null, new BigDecimal(10), null), new BigDecimal(20),
				new BigDecimal(30)),
			Arguments.of(new PointBalance(null, new BigDecimal(100000), null),
				new BigDecimal(100000), new BigDecimal(200000)),
			Arguments.of(new PointBalance(null, new BigDecimal(10.5), null), new BigDecimal(200),
				new BigDecimal(210.5))
		);
	}
}
