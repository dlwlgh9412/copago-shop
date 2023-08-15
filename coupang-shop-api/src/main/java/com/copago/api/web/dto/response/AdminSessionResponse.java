package com.copago.api.web.dto.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AdminSessionResponse {
    private Long uid;
    private String name;
    private String id;
    private String token;
}
