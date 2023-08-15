package com.copago.api.advice;

import com.copago.api.web.dto.response.ErrorResponse;
import org.springframework.http.ResponseEntity;

public class AbstractGlobalExceptionHandler {
    protected ResponseEntity<ErrorResponse> response(ErrorResponse errorResponse) {
        return new ResponseEntity<>(errorResponse, errorResponse.getHttpStatus());
    }
}
