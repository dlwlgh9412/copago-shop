package com.copago.common.config.client;

import com.copago.common.properties.CoupangPartnerProperties;
import com.copago.common.properties.CoupangProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class CoupangWebClientConfig {
    @Bean
    public WebClient coupangPartnerWebClient(CoupangPartnerProperties properties) {
        System.out.println("========================== Coupang Partner Web Client init ==========================");
        System.out.println("Base Url: " + properties.getBaseUrl());
        WebClient webClient = WebClient.builder()
                .baseUrl(properties.getBaseUrl())
                .build();

        return webClient;
    }

    @Bean
    public WebClient coupangWebClient(CoupangProperties properties) {
        System.out.println("========================== Coupang Web Client init ==========================");
        System.out.println("Base Url: " + properties.getBaseUrl());
        WebClient webClient = WebClient.builder()
                .baseUrl(properties.getBaseUrl())
                .defaultHeader("coupang-app", "COUPANG|IOS|15.6.1|7.1.1||3057b5e9d4af3aa6e58d15df82ac062f085c4a7e7f65e63d024df832965587d5|58C31AA1-DFB4-4BB8-9A5C-07A42248C52A|Y|iPhone|0C219975-D91C-40E2-BA6E-B3A3D64747DB|4586D872-0E2B-494A-B8C3-5DDA71A88EEB|IPHONE_3X|16360225166942551458875||0||WiFi||||Asia/Seoul|a9c3473197594f2e88a8c73901d5a3b6ef3e48b8|")
                .build();
        return webClient;
    }

    @Bean
    public WebClient coupangMWebClient(CoupangProperties properties) {
        System.out.println("========================== Coupang MWeb Client init ==========================");
        System.out.println("Base Url: " + properties.getBaseUrl());
        WebClient webClient = WebClient.builder()
                .baseUrl(properties.getMBaseUrl())
                .defaultHeaders(header -> {
                    header.set("Host", "m.coupang.com");
                    header.set("Accept-Encoding", "gzip, deflate, br");
                    header.set("Connection", "keep-alive");
                    header.set("Accept", "*/*");
                    header.set("User-Agent", "Mozilla/5.0 (iPhone; CPU iPhone OS 16_0_2 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) Mobile/15E148");
                    header.set("Accept-Language", "ko-KR,ko;q=0.9");
                    header.set("X-Requested-With", "XMLHttpRequest");
                })
                .build();
        return webClient;
    }
}
