package com.github.kukim.point.core.domain.point;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.github.kukim.point.core.exception.PointCacheNegativeNumberException;
import java.math.BigDecimal;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

@DisplayName("PointCache 클래스")
class PointCacheTest {

	@ParameterizedTest
	@MethodSource("providePlusCases")
	void testPlus(PointCache pointCache, BigDecimal rightValue, BigDecimal expected) {
		pointCache.plus(rightValue);

		assertThat(pointCache.getPoint()).isEqualTo(expected);
	}

	@Test
	void testPlusException() {
		PointCache pointCache = new PointCache(null, new BigDecimal(10), null);

		assertThatThrownBy(() -> pointCache.plus(new BigDecimal(-1000)))
			.isInstanceOf(PointCacheNegativeNumberException.class);
	}

	private static Stream<Arguments> providePlusCases() {
		return Stream.of(
			Arguments.of(new PointCache(null, new BigDecimal(10), null), new BigDecimal(20), new BigDecimal(30)),
			Arguments.of(new PointCache(null, new BigDecimal(100000), null), new BigDecimal(100000), new BigDecimal(200000)),
			Arguments.of(new PointCache(null, new BigDecimal(10.5), null), new BigDecimal(200), new BigDecimal(210.5))
		);
	}
}
