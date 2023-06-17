package com.github.kukim.point.core.domain.point;

import com.github.kukim.point.core.domain.BaseTimeEntity;
import com.github.kukim.point.core.domain.history.PointHistory;
import com.github.kukim.point.core.domain.type.EventDetailType;
import com.github.kukim.point.core.domain.type.EventType;
import com.github.kukim.point.core.domain.util.KeyGenerator;
import java.math.BigDecimal;
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
	private String searchId;
	@Column(nullable = false)
	private String messageId;

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private EventType eventType;

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private EventDetailType eventDetailType;

	@Column(nullable = false)
	private BigDecimal savePoint;

	private String description;
	private LocalDateTime expirationDate;

	@Column(nullable = false)
	private Long memberId;

	public Point(String messageId, EventType eventType, EventDetailType eventDetailType,
		BigDecimal savePoint,
		String description, LocalDateTime expirationDate, Long memberId) {
		this.searchId = KeyGenerator.generateUUID();
		this.messageId = messageId;
		this.eventType = eventType;
		this.eventDetailType = eventDetailType;
		this.savePoint = savePoint;
		this.description = description;
		this.expirationDate = expirationDate;
		this.memberId = memberId;
	}

	public PointHistory toEarnPointHistory() {
		return new PointHistory(messageId, eventType, eventDetailType, savePoint, searchId,
			searchId, searchId, expirationDate, memberId);
	}
}
