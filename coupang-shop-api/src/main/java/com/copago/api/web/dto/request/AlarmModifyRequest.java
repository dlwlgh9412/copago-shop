package com.copago.api.web.dto.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AlarmModifyRequest {
    private Long pid;
    private Long price;
}
