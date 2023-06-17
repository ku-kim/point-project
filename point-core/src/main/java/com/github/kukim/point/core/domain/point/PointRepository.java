package com.github.kukim.point.core.domain.point;

import java.math.BigDecimal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PointRepository extends JpaRepository<Point, Long> {

	boolean existsByMessageId(String messageId);

	@Query(
		"SELECT SUM(p.savePoint) " +
			"FROM Point p " +
			"WHERE p.memberId = :memberId AND p.eventType <> 'CANCEL'"
	)
	BigDecimal sumPointByMemberId(Long memberId);
}
