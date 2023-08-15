package com.copago.common.properties;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Getter
@Configuration
@PropertySource("classpath:properties/coupang/coupang-partner.properties")
public class CoupangPartnerProperties {
    @Value("${base-url}")
    private String baseUrl;

    @Value("${path}")
    private String path;

    @Value("${secret-key}")
    private String secretKey;

    @Value("${access-key}")
    private String accessKey;
}
