package com.github.kukim.point.core.domain.point;

import com.github.kukim.point.core.domain.BaseTimeEntity;
import com.github.kukim.point.core.domain.history.PointHistory;
import com.github.kukim.point.core.domain.type.EventDetailType;
import com.github.kukim.point.core.domain.type.EventType;
import com.github.kukim.point.core.domain.util.KeyGenerator;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;
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
	private String tradeId;
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
	@Column(nullable = false)
	private BigDecimal remainPoint;

	private String description;
	private LocalDateTime expirationDate;

	@Column(nullable = false)
	private Long memberId;

	public Point(String tradeId, String messageId, EventType eventType, EventDetailType eventDetailType,
		BigDecimal savePoint,
		String description, LocalDateTime expirationDate, Long memberId) {
		this.searchId = "p-" + KeyGenerator.generateUUID();
		this.tradeId = tradeId;
		this.messageId = messageId;
		this.eventType = eventType;
		this.eventDetailType = eventDetailType;
		this.savePoint = savePoint;
		this.remainPoint = savePoint;
		this.description = description;
		this.expirationDate = expirationDate;
		this.memberId = memberId;
	}

	public PointHistory toEarnPointHistory() {
		String pointHistorySearchId = "h-" + KeyGenerator.generateUUID();
		return new PointHistory(messageId, eventType, eventDetailType, savePoint, pointHistorySearchId,
			pointHistorySearchId, searchId, expirationDate, memberId);
	}

	public BigDecimal deductPoint(BigDecimal point) {
		if (Objects.isNull(point)) {
			throw new NullPointerException("입력한 포인트가 비어있습니다.");
		}

		this.remainPoint = remainPoint.add(point);
		if (this.remainPoint.signum() == 1) {
			BigDecimal underFlowPoint = point.subtract(this.remainPoint);
			this.remainPoint = new BigDecimal(0);
			return underFlowPoint;
		} else if (this.remainPoint.signum() == 0){
			return new BigDecimal(0);
		}

		return this.remainPoint;
	}

	// TODO: Only 디버깅용
	@Override
	public String toString() {
		return "Point{" +
			"id=" + id +
			", searchId='" + searchId + '\'' +
			", tradeId='" + tradeId + '\'' +
			", messageId='" + messageId + '\'' +
			", eventType=" + eventType +
			", eventDetailType=" + eventDetailType +
			", savePoint=" + savePoint +
			", remainPoint=" + remainPoint +
			", description='" + description + '\'' +
			", expirationDate=" + expirationDate +
			", memberId=" + memberId +
			'}';
	}
}
