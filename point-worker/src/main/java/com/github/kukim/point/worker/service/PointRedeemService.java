package com.github.kukim.point.worker.service;

import com.github.kukim.point.core.domain.history.PointHistory;
import com.github.kukim.point.core.domain.history.PointHistoryRepository;
import com.github.kukim.point.core.domain.history.PointRemainHistory;
import com.github.kukim.point.core.domain.message.PointMessage;
import com.github.kukim.point.core.domain.point.Point;
import com.github.kukim.point.core.domain.point.PointRepository;
import com.github.kukim.point.worker.exception.NotFoundPointReceiptException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class PointRedeemService {

	private final PointHistoryRepository pointHistoryRepository;
	private final PointRepository pointRepository;

	public PointRedeemService(PointHistoryRepository pointHistoryRepository,
		PointRepository pointRepository) {
		this.pointHistoryRepository = pointHistoryRepository;
		this.pointRepository = pointRepository;
	}


	@Transactional
	public void redeem(PointMessage pointMessage) {
		Point redeemPoint = pointRepository.findByTradeId(pointMessage.getTradeNo())
			.orElseThrow(NotFoundPointReceiptException::new);


		List<PointRemainHistory> pointRemainHistories = pointHistoryRepository.findAllByEarnEventType(
			pointMessage.getMemberId());
		List<PointHistory> savePointHistory = new ArrayList<>();

		for (PointRemainHistory pointRemainHistory : pointRemainHistories) {
			BigDecimal underFlowPoint = redeemPoint.deductPoint(pointRemainHistory.getRemainPoint());

			if (underFlowPoint.signum() == -1) {
				PointHistory redeemPointHistory = PointHistory.createRedeemBy(
					pointMessage.getMessageId(),
					pointMessage.getEventType(), pointMessage.getEventDetailType(),
					pointRemainHistory.getRemainPoint().multiply(new BigDecimal(-1)),
					pointRemainHistory.getEarnPointId(),
					redeemPoint.getSearchId(),
					redeemPoint.getExpirationDate(), redeemPoint.getMemberId());
				savePointHistory.add(redeemPointHistory);
				log.info("[point-worker][point-redeem] pointhistory 사용 업데이트 중: {}", redeemPointHistory);
			} else {
				PointHistory redeemPointHistory = PointHistory.createRedeemBy(pointMessage.getMessageId(),
					pointMessage.getEventType(), pointMessage.getEventDetailType(),
					underFlowPoint.multiply(new BigDecimal(-1)),
					pointRemainHistory.getEarnPointId(), redeemPoint.getSearchId(),
					redeemPoint.getExpirationDate(), redeemPoint.getMemberId());
				savePointHistory.add(redeemPointHistory);
				log.info("[point-worker][point-redeem] pointhistory 사용 업데이트 완료: {}", redeemPointHistory);
				break;
			}
		}

		pointHistoryRepository.saveAll(savePointHistory);
	}
}
