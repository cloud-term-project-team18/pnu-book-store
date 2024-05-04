package org.example.pnubookstore.domain.product;

import org.example.pnubookstore.core.exception.BaseExceptionStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ProductExceptionStatus implements BaseExceptionStatus {
	// 도메인에 맞는 예외를 채워주세요.
	;
	private final int errorCode;
	private final String errorMessage;
}
