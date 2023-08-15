package com.copago.common.exception;

import lombok.Getter;

@Getter
public class CoupangShopException extends RuntimeException {
    private final ErrorCode errorCode;
    private final String message;

    public CoupangShopException(ErrorCode errorCode, String message) {
        this.errorCode = errorCode;
        this.message = message;
    }
}
