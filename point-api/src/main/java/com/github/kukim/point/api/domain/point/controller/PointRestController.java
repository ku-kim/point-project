package com.github.kukim.point.api.domain.point.controller;

import static com.github.kukim.point.core.common.Constant.HEADER_MEMBER_ID;

import com.github.kukim.point.api.common.CustomApiResponse;
import com.github.kukim.point.api.common.CustomApiResponseGenerator;
import com.github.kukim.point.api.domain.point.controller.dto.PointEarnCommand;
import com.github.kukim.point.api.domain.point.controller.dto.PointRedeemCommand;
import com.github.kukim.point.api.domain.point.service.PointEarnService;
import com.github.kukim.point.api.domain.point.service.PointRedeemFacade;
import com.github.kukim.point.core.domain.message.dto.PointMessageDto;
import javax.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PointRestController {

	private final PointEarnService pointEarnService;
	private final PointRedeemFacade pointRedeemFacade;

	public PointRestController(PointEarnService pointEarnService,
		PointRedeemFacade pointRedeemFacade) {
		this.pointEarnService = pointEarnService;
		this.pointRedeemFacade = pointRedeemFacade;
	}

	/**
	 * 포인트 적립 API
	 * @param memberId memberId가 유효하다는 가정
	 * @param command 포인트 적립 command
	 * @return 포인트 적립 message
	 */
	@PostMapping("/api/v1/points/earn")
	public CustomApiResponse<?> earnPoint(@RequestHeader(HEADER_MEMBER_ID) Long memberId, @RequestBody @Valid PointEarnCommand command) {
		PointMessageDto body = pointEarnService.earn(memberId, command);
		return CustomApiResponseGenerator.success(body);
	}


	/**
	 * 포인트 사용 API
	 * 1. 포인트 사용 유효성 검증 (실제 DB 확인)
	 * 2. 포인트 사용 DB 업데이트 (실제 point 사용 DB 업데이트)
	 * 3. 포인트 사용 워커에 전달 (PointHistory 업데이트를위해 worker에 전달)
	 *
	 * @param memberId memberId가 유효하다는 가정
	 * @param command 포인트 사용 command
	 * @return 포인트 사용 message
	 */
	@PostMapping("/api/v1/points/redeem")
	public CustomApiResponse<?> redeemPoint(@RequestHeader(HEADER_MEMBER_ID) Long memberId, @RequestBody @Valid PointRedeemCommand command) {
		PointMessageDto body = pointRedeemFacade.redeem(memberId, command);
		return CustomApiResponseGenerator.success(body);
	}

//
//	/**
//	 * 포인트 사용 취소 API
//	 */
//	@PostMapping("/api/v1/...")
//	public ResponseEntity<?> cancelPoint() {
//		return ResponseEntity.ok();
//	}

}
