package com.github.kukim.point.core.domain.point;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PointRepository extends JpaRepository<Point, Long> {

	boolean existsByMessageId(String messageId);
}
