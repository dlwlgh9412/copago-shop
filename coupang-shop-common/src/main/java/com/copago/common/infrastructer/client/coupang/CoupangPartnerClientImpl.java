package com.copago.common.infrastructer.client.coupang;

import com.copago.common.infrastructer.client.coupang.dto.request.CoupangPartnerRequest;
import com.copago.common.infrastructer.client.coupang.dto.response.CoupangPartnerResponse;
import com.copago.common.properties.CoupangPartnerProperties;
import com.copago.common.utils.HmacGenerator;
import com.copago.common.utils.WebClientResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CoupangPartnerClientImpl implements CoupangPartnerClient {
    private final WebClient coupangPartnerWebClient;
    private final CoupangPartnerProperties properties;

    @Override
    public CoupangPartnerResponse<List<CoupangPartnerResponse.ShortenUrl>> getDeepLink(CoupangPartnerRequest.DeepLink request) {
        String uri = properties.getPath() + "/deeplink";
        String auth = HmacGenerator.generate("POST", uri, properties.getSecretKey(), properties.getAccessKey());
        WebClient.ResponseSpec responseSpec = coupangPartnerWebClient
                .post()
                .uri(uri)
                .header("Authorization", auth)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .retrieve();
        return WebClientResolver.resolve(responseSpec, new ParameterizedTypeReference<CoupangPartnerResponse<List<CoupangPartnerResponse.ShortenUrl>>>() {
        });
    }

    @Override
    public Object getReportOrders(String startDate, String endDate) {
        String uri = properties.getPath() + "/reports/orders?startDate=" + startDate + "&endDat=" + endDate;
        String auth = HmacGenerator.generate("GET", uri, properties.getSecretKey(), properties.getAccessKey());
        WebClient.ResponseSpec responseSpec = coupangPartnerWebClient
                .get()
                .uri(uri)
                .header("Authorization", auth)
                .retrieve();
        return WebClientResolver.resolve(responseSpec, Object.class);
    }

    @Override
    public CoupangPartnerResponse.Commission getCommission(String startDate, String endDate) {
        String uri = properties.getPath() + "/reports/commission?startDate=" + startDate + "&endDate=" + endDate;
        String auth = HmacGenerator.generate("GET", uri, properties.getSecretKey(), properties.getAccessKey());
        WebClient.ResponseSpec responseSpec = coupangPartnerWebClient
                .get()
                .uri(uri)
                .header("Authorization", auth)
                .retrieve();
        return WebClientResolver.resolve(responseSpec, CoupangPartnerResponse.Commission.class);
    }
}
