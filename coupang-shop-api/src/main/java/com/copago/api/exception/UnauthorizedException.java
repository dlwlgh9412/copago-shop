package com.copago.api.exception;

import com.copago.common.exception.CoupangShopException;
import com.copago.common.exception.ErrorCode;

public class UnauthorizedException extends CoupangShopException {
    public UnauthorizedException(String message) {
        super(ErrorCode.UNAUTHORIZED, message);
    }
}
