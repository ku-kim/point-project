package com.github.kukim.point.api.domain.member.service;

import com.github.kukim.point.api.domain.member.controller.dto.MemberPointBalanceDto;
import com.github.kukim.point.api.domain.member.controller.dto.MemberPointHistoryDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MemberFinder {

	/**
	 * 특정 멤버의 현재 총자산 반환
	 * @param memberId 멤버 id
	 * @return 멤버 총자산 dto
	 */
	MemberPointBalanceDto readBalance(Long memberId);

	/**
	 * 특정 멤버희 현재 총자산 반환 / 단, 결제용으로 실제 DB만 조회
	 * @param memberId 멤버 id
	 * @return 멤버 총자산 dto
	 */
	MemberPointBalanceDto readBalanceNoCache(Long memberId);

	/**
	 * 특정 멤버의 포인트 적립/사용 히스토리 반환
	 * @param memberId 멤버 id
	 * @param pageable 페이징 정보
	 * @return 페이징된 포인트 히스토리 반환
	 */
	Page<MemberPointHistoryDto> readAllPointHistory(Long memberId, Pageable pageable);

}
