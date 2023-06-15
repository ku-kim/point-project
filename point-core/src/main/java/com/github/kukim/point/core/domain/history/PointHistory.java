package com.github.kukim.point.core.domain.history;

import com.github.kukim.point.core.domain.common.BaseTimeEntity;
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

/**
 * Point 적립 / 사용 / 사용 취소 등 Point에 관련된 모든 Event 발생 시 insert 되는 도메인
 */
@Getter
@NoArgsConstructor
@Entity
@Table
public class PointHistory extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "point_history_id")
	private Long id;

	@Column(nullable = false)
	private String messageId;

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private EventType eventType;

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private EventDetailType eventDetailType;

	private Long savePoint;

	@Column
	private Long cancelPointId;
	private Long earnPointId;
	private Long originPointId;

	private LocalDateTime expirationDate;

	private Long memberId;

	public PointHistory(String messageId, EventType eventType, EventDetailType eventDetailType,
		Long savePoint, Long cancelPointId, Long earnPointId, Long originPointId,
		LocalDateTime expirationDate, Long memberId) {
		this.messageId = messageId;
		this.eventType = eventType;
		this.eventDetailType = eventDetailType;
		this.savePoint = savePoint;
		this.cancelPointId = cancelPointId;
		this.earnPointId = earnPointId;
		this.originPointId = originPointId;
		this.expirationDate = expirationDate;
		this.memberId = memberId;
	}
}
