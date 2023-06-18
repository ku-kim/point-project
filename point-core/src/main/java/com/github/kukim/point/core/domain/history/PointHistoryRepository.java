package com.github.kukim.point.core.domain.history;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PointHistoryRepository extends JpaRepository<PointHistory, Long> {


	@Query("""
		SELECT NEW com.github.kukim.point.core.domain.history.PointRemainHistory(ph.earnPointId, SUM(ph.savePoint))
		FROM PointHistory ph
		WHERE ph.memberId = :memberId
		GROUP BY ph.earnPointId
		HAVING SUM(ph.savePoint) > 0
		ORDER BY ph.earnPointId
	""")
	List<PointRemainHistory> findAllByEarnEventType(Long memberId);
}
