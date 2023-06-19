package com.github.kukim.point.core.domain.point;

import java.math.BigDecimal;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PointRepository extends JpaRepository<Point, Long> {

	boolean existsByMessageId(String messageId);

	@Query("""
        SELECT SUM(p.savePoint)
        FROM Point p
        WHERE p.memberId = :memberId AND p.eventType <> 'CANCEL'
        """)
	BigDecimal sumPointByMemberId(Long memberId);

	@Query("""
		SELECT p
		FROM Point p
		WHERE p.memberId = :memberId AND p.eventType <> 'CANCEL'
		ORDER BY p.createdDate DESC
		""")
	Page<Point> findAllByMemberIdEventTypeNotCancel(Long memberId, Pageable pageable);

	Optional<Point> findByTradeId(String tradeNo);


	@Query("""
		SELECT p
		FROM Point p
		WHERE p.memberId = :memberId 
			AND p.tradeId = :tradeId
			AND p.eventType = 'USE'
	""")
	Optional<Point> findRedeemPointByMemberIdAndTradeId(Long memberId, String tradeId);
}
