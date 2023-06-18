package com.github.kukim.point.api.domain.member.controller;

import static com.github.kukim.point.core.common.Constant.HEADER_MEMBER_ID;

import com.github.kukim.point.api.common.CustomApiResponse;
import com.github.kukim.point.api.common.CustomApiResponseGenerator;
import com.github.kukim.point.api.domain.member.controller.dto.MemberPointBalanceDto;
import com.github.kukim.point.api.domain.member.service.MemberFinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MemberRestController {

	private final MemberFinder memberFinder;

	public MemberRestController(MemberFinder memberFinder) {
		this.memberFinder = memberFinder;
	}


	/**
	 * 잔여 포인트 조회 API
	 * Member 도메인은 별도로 구현하지 않음, memberId는 유효하다는 가정
	 */
	@GetMapping("/api/v1/members/points/balance")
	public CustomApiResponse<?> readBalance(@RequestHeader(HEADER_MEMBER_ID) Long memberId) {
		MemberPointBalanceDto body = memberFinder.readBalance(memberId);

		return CustomApiResponseGenerator.success(body);
	}


}
