package com.github.kukim.point.api.domain.member.service;

import com.github.kukim.point.api.domain.member.controller.dto.MemberPointBalanceDto;
import com.github.kukim.point.api.domain.member.controller.dto.MemberPointHistoryDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MemberFinder {

	/**
	 * 특정 멤버의 현재 총자산 반환
	 * @param memberId 조회하고자 하는 멤버 id
	 * @return 멤버 총자산 dto
	 */
	MemberPointBalanceDto readBalance(Long memberId);

	Page<MemberPointHistoryDto> readAllPointHistory(Long memberId, Pageable pageable);
}
