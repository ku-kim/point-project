package com.github.kukim.point.api.domain.point.controller;

import static com.github.kukim.point.core.common.Constant.HEADER_MEMBER_ID;

import com.github.kukim.point.api.common.CustomApiResponse;
import com.github.kukim.point.api.common.CustomApiResponseGenerator;
import com.github.kukim.point.api.domain.point.controller.dto.PointEarnCommand;
import com.github.kukim.point.api.domain.point.service.PointEarnFacade;
import com.github.kukim.point.core.domain.message.dto.PointMessageDto;
import javax.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PointRestController {

	private final PointEarnFacade pointEarnFacade;

	public PointRestController(PointEarnFacade pointEarnFacade) {
		this.pointEarnFacade = pointEarnFacade;
	}

//
//	/**
//	 * 포인트 적립/사용 내역 조회 API
//	 */
//	@GetMapping("/api/v1/...")
//	public ResponseEntity<?> readHistory() {
//		return ResponseEntity.ok();
//	}
//

	/**
	 * 포인트 적립 API
	 * @param memberId memberId가 유효하다는 가정
	 * @param command 적립 포인트 내역
	 * @return 포인트 적립
	 */
	@PostMapping("/api/v1/points/earn")
	public CustomApiResponse<?> earnPoint(@RequestHeader(HEADER_MEMBER_ID) Long memberId, @RequestBody @Valid PointEarnCommand command) {
		PointMessageDto body = pointEarnFacade.earn(memberId, command);
		return CustomApiResponseGenerator.success(body);
	}
//
//	/**
//	 * 포인트 사용 API
//	 */
//	@PostMapping("/api/v1/...")
//	public ResponseEntity<?> redeemPoint() {
//		return ResponseEntity.ok();
//	}
//
//	/**
//	 * 포인트 사용 취소 API
//	 */
//	@PostMapping("/api/v1/...")
//	public ResponseEntity<?> cancelPoint() {
//		return ResponseEntity.ok();
//	}

}
