package com.copago.api.exception;

import com.copago.common.exception.CoupangShopException;
import com.copago.common.exception.ErrorCode;

public class AlarmException extends CoupangShopException {
    public AlarmException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }
}
