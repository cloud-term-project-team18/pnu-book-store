package org.example.pnubookstore.core.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum BaseExceptionStatus {
	// User
	USER_NOT_FOUND("404", "회원 정보를 찾을 수 없습니다."),
	USER_MISMATCH("400", "일치하지 않는 회원정보입니다."),
	USERNAME_ALREADY_USED("400", "이미 존재하는 회원입니다."),

	// Auction
	AUCTION_NOT_FOUND("404", "경매 정보를 찾을 수 없습니다.");

	private final String errorCode;
	private final String errorMessage;
}
