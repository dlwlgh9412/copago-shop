package com.copago.common.properties;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Getter
@Configuration
@PropertySource("classpath:properties/coupang/coupang.properties")
public class CoupangProperties {
    @Value("${base.url}")
    private String baseUrl;

    @Value("${mbase.url}")
    private String mBaseUrl;

    @Value("${link.base.url}")
    private String linkBaseUrl;
}
