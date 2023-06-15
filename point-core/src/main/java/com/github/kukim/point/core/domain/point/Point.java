package com.github.kukim.point.core.domain.point;

import com.github.kukim.point.core.domain.BaseTimeEntity;
import com.github.kukim.point.core.domain.type.EventDetailType;
import com.github.kukim.point.core.domain.type.EventType;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
@Table
public class Point extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "point_id")
	private Long id;

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private EventType eventType;

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private EventDetailType eventDetailType;

	@Column(nullable = false)
	private Long savePoint;
	@Column(nullable = false)
	private Long remainPoint;

	private String description;
	private LocalDateTime expirationDate;

	@Column(nullable = false)
	private Long memberId;

	public Point(EventType eventType, EventDetailType eventDetailType, Long savePoint,
		String description, LocalDateTime expirationDate, Long memberId) {
		this.eventType = eventType;
		this.eventDetailType = eventDetailType;
		this.savePoint = savePoint;
		this.remainPoint = savePoint;
		this.description = description;
		this.expirationDate = expirationDate;
		this.memberId = memberId;
	}


}
