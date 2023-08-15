package com.copago.api.web.dto.response;

import com.copago.common.exception.CoupangShopException;
import com.copago.common.exception.ErrorCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ErrorResponse {
    private final ErrorCode errorCode;
    private final String reason;

    public ErrorResponse(ErrorCode errorCode, String reason) {
        this.errorCode = errorCode;
        this.reason = reason;
    }

    public ErrorResponse(CoupangShopException e) {
        this.errorCode = e.getErrorCode();
        this.reason = e.getMessage();
    }

    public HttpStatus getHttpStatus() {
        return errorCode.getHttpStatus();
    }
}
