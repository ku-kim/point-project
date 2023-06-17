package com.github.kukim.point.api;

import com.github.kukim.point.core.domain.history.PointHistory;
import com.github.kukim.point.core.domain.history.PointHistoryRepository;
import com.github.kukim.point.core.domain.point.Point;
import com.github.kukim.point.core.domain.point.PointRepository;
import com.github.kukim.point.core.domain.type.EventDetailType;
import com.github.kukim.point.core.domain.type.EventType;
import com.github.kukim.point.core.domain.util.KeyGenerator;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Slf4j
@Profile("local")
@Component
public class LocalCommandRunner implements CommandLineRunner {

	private final PointRepository pointRepository;
	private final PointHistoryRepository pointHistoryRepository;

	public LocalCommandRunner(PointRepository pointRepository,
		PointHistoryRepository pointHistoryRepository) {
		this.pointRepository = pointRepository;
		this.pointHistoryRepository = pointHistoryRepository;
	}

	@Override
	public void run(String... args) {
		log.info("Point-api: init(Local CommandRunner): Point & PointHistory");
		initPointAndHistory();
	}

	private void initPointAndHistory() {
		List<Point> points = new ArrayList<>();
		points.add(new Point(KeyGenerator.generateUUID(), EventType.SAVE, EventDetailType.SAVE_EVENT, BigDecimal.valueOf(100L), "로그인 적립", LocalDateTime.now().plusDays(1), 1L));
		points.add(new Point(KeyGenerator.generateUUID(), EventType.SAVE, EventDetailType.SAVE_EVENT, BigDecimal.valueOf(100L), "로그인 적립", LocalDateTime.now().plusYears(1), 1L));
		points.add(new Point(KeyGenerator.generateUUID(), EventType.SAVE, EventDetailType.SAVE_EVENT, BigDecimal.valueOf(100L), "로그인 적립", LocalDateTime.now().plusYears(1), 2L));
		points.add(new Point(KeyGenerator.generateUUID(), EventType.SAVE, EventDetailType.SAVE_EVENT, BigDecimal.valueOf(100L), "로그인 적립", LocalDateTime.now().plusYears(1), 2L));
		points.add(new Point(KeyGenerator.generateUUID(), EventType.SAVE, EventDetailType.SAVE_EVENT, BigDecimal.valueOf(100L), "로그인 적립", LocalDateTime.now().plusYears(1), 3L));
		points.add(new Point(KeyGenerator.generateUUID(), EventType.SAVE, EventDetailType.SAVE_EVENT, BigDecimal.valueOf(100L), "로그인 적립", LocalDateTime.now().plusYears(1), 3L));

		points.add(new Point(KeyGenerator.generateUUID(), EventType.SAVE, EventDetailType.SAVE_BUY, BigDecimal.valueOf(1500L), "상품 A 구입 적립", LocalDateTime.now().plusYears(1), 1L));
		points.add(new Point(KeyGenerator.generateUUID(), EventType.SAVE, EventDetailType.SAVE_BUY, BigDecimal.valueOf(2000L), "상품 B 구입 적립", LocalDateTime.now().plusYears(1), 1L));
		points.add(new Point(KeyGenerator.generateUUID(), EventType.SAVE, EventDetailType.SAVE_BUY, BigDecimal.valueOf(700L), "상품 C 구입 적립", LocalDateTime.now().plusYears(1), 2L));
		points.add(new Point(KeyGenerator.generateUUID(), EventType.SAVE, EventDetailType.SAVE_BUY, BigDecimal.valueOf(800L), "상품 D 구입 적립", LocalDateTime.now().plusYears(1), 2L));
		points.add(new Point(KeyGenerator.generateUUID(), EventType.SAVE, EventDetailType.SAVE_BUY, BigDecimal.valueOf(1000L), "상품 E 구입 적립", LocalDateTime.now().plusYears(1), 3L));
		points.add(new Point(KeyGenerator.generateUUID(), EventType.SAVE, EventDetailType.SAVE_BUY, BigDecimal.valueOf(1000L), "상품 F 구입 적립", LocalDateTime.now().plusYears(1), 3L));

		points.add(new Point(KeyGenerator.generateUUID(), EventType.SAVE, EventDetailType.USE_BUY, BigDecimal.valueOf(-1000L), "상품 AA 구입 후 적립", LocalDateTime.now().plusYears(1), 1L));
		points.add(new Point(KeyGenerator.generateUUID(), EventType.SAVE, EventDetailType.USE_BUY, BigDecimal.valueOf(-500L), "상품 BB 구입 후 적립", LocalDateTime.now().plusYears(1), 2L));
		points.add(new Point(KeyGenerator.generateUUID(), EventType.SAVE, EventDetailType.USE_BUY, BigDecimal.valueOf(-200L), "상품 CC 구입 후 적립", LocalDateTime.now().plusYears(1), 3L));

		List<PointHistory> pointHistories = new ArrayList<>();
		pointHistories.add(new PointHistory(KeyGenerator.generateUUID(), EventType.SAVE, EventDetailType.SAVE_EVENT,
			BigDecimal.valueOf(100L), null, null, null, LocalDateTime.now().plusDays(1), 1L));
		pointHistories.add(new PointHistory(KeyGenerator.generateUUID(), EventType.SAVE, EventDetailType.SAVE_EVENT, BigDecimal.valueOf(100L), null, null, null, LocalDateTime.now().plusYears(1), 1L));
		pointHistories.add(new PointHistory(KeyGenerator.generateUUID(), EventType.SAVE, EventDetailType.SAVE_EVENT, BigDecimal.valueOf(100L), null, null, null, LocalDateTime.now().plusYears(1), 2L));
		pointHistories.add(new PointHistory(KeyGenerator.generateUUID(), EventType.SAVE, EventDetailType.SAVE_EVENT, BigDecimal.valueOf(100L), null, null, null, LocalDateTime.now().plusYears(1), 2L));
		pointHistories.add(new PointHistory(KeyGenerator.generateUUID(), EventType.SAVE, EventDetailType.SAVE_EVENT, BigDecimal.valueOf(100L), null, null, null, LocalDateTime.now().plusYears(1), 3L));
		pointHistories.add(new PointHistory(KeyGenerator.generateUUID(), EventType.SAVE, EventDetailType.SAVE_EVENT, BigDecimal.valueOf(100L), null, null, null, LocalDateTime.now().plusYears(1), 3L));

		// TODO: Point History 상세 내역 추가

		pointRepository.saveAll(points);
		pointHistoryRepository.saveAll(pointHistories);
	}
}
