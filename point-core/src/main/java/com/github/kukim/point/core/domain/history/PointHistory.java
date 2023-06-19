package com.github.kukim.point.core.domain.history;

import com.github.kukim.point.core.domain.BaseTimeEntity;
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
import javax.persistence.Index;
import javax.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Point 적립 / 사용 / 사용 취소 등 Point에 관련된 모든 Event 발생 시 insert 되는 도메인
 */
@Getter
@NoArgsConstructor
@Entity
@Table(
	indexes = {
		@Index(name = "IDX_POINT_HISTORY_MEMBER_ID", columnList = "memberId"),
		@Index(name = "IDX_POINT_HISTORY_MEMBER_ID", columnList = "memberId"),
		@Index(name = "IDX_POINT_HISTORY_EARN_POINT_ID", columnList = "earnPointId"),
		@Index(name = "IDX_POINT_HISTORY_ORIGIN_POINT_ID", columnList = "originPointId"),
		@Index(name = "IDX_POINT_HISTORY_SAVE_POINT", columnList = "savePoint")
	}
)
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

	@Column(nullable = false)
	private BigDecimal savePoint;

	private String cancelPointId;
	private String earnPointId;
	private String originPointId;

	private LocalDateTime expirationDate;

	@Column(nullable = false)
	private Long memberId;

	public PointHistory(String messageId, EventType eventType, EventDetailType eventDetailType,
		BigDecimal savePoint, String cancelPointId, String earnPointId, String originPointId,
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

	public static PointHistory createRedeemHistoryBy(String messageId, EventType eventType,
		EventDetailType eventDetailType, BigDecimal redeemPoint, String earnPointId,
		String searchId, LocalDateTime expirationDate,
		Long memberId) {
		String key = "h-" + KeyGenerator.generateUUID();
		return new PointHistory(messageId, eventType, eventDetailType, redeemPoint, key,
			earnPointId, searchId, expirationDate, memberId);
	}

	// Only 디버깅용
	@Override
	public String toString() {
		return "PointHistory{" +
			"id=" + id +
			", messageId='" + messageId + '\'' +
			", eventType=" + eventType +
			", eventDetailType=" + eventDetailType +
			", savePoint=" + savePoint +
			", cancelPointId='" + cancelPointId + '\'' +
			", earnPointId='" + earnPointId + '\'' +
			", originPointId='" + originPointId + '\'' +
			", expirationDate=" + expirationDate +
			", memberId=" + memberId +
			'}';
	}
}
