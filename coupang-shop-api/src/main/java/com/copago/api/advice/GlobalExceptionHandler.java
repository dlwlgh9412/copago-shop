package com.copago.api.advice;

import com.copago.common.exception.ErrorCode;
import com.copago.api.exception.UnauthorizedException;
import com.copago.api.web.dto.response.ErrorResponse;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler extends AbstractGlobalExceptionHandler {
    @ExceptionHandler(SignatureException.class)
    public ResponseEntity<ErrorResponse> signatureException() {
        ErrorResponse errorResponse = new ErrorResponse(ErrorCode.UNAUTHORIZED, "토큰 서명검증에 실패하였습니다.");
        return response(errorResponse);
    }

    @ExceptionHandler(MalformedJwtException.class)
    public ResponseEntity<ErrorResponse> malformedJwtException() {
        ErrorResponse errorResponse = new ErrorResponse(ErrorCode.UNAUTHORIZED, "토큰 구성이 올바르지 않습니다.");
        return response(errorResponse);
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<ErrorResponse> expiredJwtException() {
        ErrorResponse errorResponse = new ErrorResponse(ErrorCode.UNAUTHORIZED, "만료된 토큰입니다.");
        return response(errorResponse);
    }

    @ExceptionHandler(UnsupportedJwtException.class)
    public ResponseEntity<ErrorResponse> unsupportedJwtException() {
        ErrorResponse errorResponse = new ErrorResponse(ErrorCode.UNAUTHORIZED, "지원하지 않는 토큰입니다.");
        return response(errorResponse);
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ErrorResponse> unauthorizedException(UnauthorizedException e) {
        ErrorResponse errorResponse = new ErrorResponse(e);
        return response(errorResponse);
    }
}
