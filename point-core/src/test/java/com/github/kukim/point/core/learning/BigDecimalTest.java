package com.github.kukim.point.core.learning;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import org.junit.jupiter.api.Test;

class BigDecimalTest {

	@Test
	void testAdd() {
		BigDecimal number1 = new BigDecimal(100);
		BigDecimal number2 = new BigDecimal(50);

		BigDecimal result = number1.add(number2);

		assertThat(result.toString()).hasToString("150");
	}

	@Test
	void testAdd2() {
		BigDecimal number1 = new BigDecimal(100);
		BigDecimal number2 = new BigDecimal(-150);

		BigDecimal result = number1.add(number2);

		assertThat(result.toString()).hasToString("-50");
	}
}
