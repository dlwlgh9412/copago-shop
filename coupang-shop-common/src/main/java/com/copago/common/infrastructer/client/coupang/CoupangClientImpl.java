package com.copago.common.infrastructer.client.coupang;

import com.copago.common.infrastructer.client.coupang.dto.response.CoupangProductResponse;
import com.copago.common.infrastructer.client.coupang.dto.response.CoupangReturnMResponse;
import com.copago.common.utils.WebClientResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.reactive.function.client.WebClient;

@Component
@RequiredArgsConstructor
public class CoupangClientImpl implements CoupangClient {
    private final WebClient coupangWebClient;
    private final WebClient coupangMWebClient;

    @Override
    public CoupangProductResponse<CoupangProductResponse.Product> search(String filter, String nextPageKey) {
        String defaultFilter = "|SINGLE_ENTITY:TRUE|STATIC_SERVICE:ROCKET_WOW_DELIVERY,COUPANG_GLOBAL,FREE_DELIVERY|EXTRAS:channel/filter@SEARCH";

        String param = "resultType";
        if (StringUtils.hasText(nextPageKey)) param = "nextPageKey";

        String uri = "/v3/products?filter=" + filter + defaultFilter + "&" + param + "=" + (StringUtils.hasText(nextPageKey) ? nextPageKey : "default");

        WebClient.ResponseSpec responseSpec = coupangWebClient
                .post()
                .uri(uri)
                .retrieve();
        return WebClientResolver.resolve(responseSpec, new ParameterizedTypeReference<CoupangProductResponse<CoupangProductResponse.Product>>() {
        });
    }

    @Override
    public CoupangProductResponse<CoupangProductResponse.Product> getAppleReturnMallList(Long returnId, String nextPageKey) {
        String param = "resultType";
        if (StringUtils.hasText(nextPageKey)) param = "nextPageKey";

        String uri = "/modular/v1/pages/17/campaigns/16225/components/" + returnId + "?" + param + "=" + (StringUtils.hasText(nextPageKey) ? nextPageKey : "default");

        WebClient.ResponseSpec responseSpec = coupangWebClient
                .post()
                .uri(uri)
                .retrieve();

        return WebClientResolver.resolve(responseSpec, new ParameterizedTypeReference<CoupangProductResponse<CoupangProductResponse.Product>>() {
        });
    }

    @Override
    public CoupangProductResponse<CoupangProductResponse.CoupangUsedItemNudgeData> getUsedProductDetail(String productId, String itemId, String vendorItemId) {
        String uri = String.format("/modular/v1/endpoints/2373/products/%s/items/%s/offers/offer-item-list?sdpVisitKey=&vendorItemId=%s", productId, itemId, vendorItemId);
        WebClient.ResponseSpec responseSpec = coupangWebClient
                .get()
                .uri(uri)
                .retrieve();
        return WebClientResolver.resolve(responseSpec, new ParameterizedTypeReference<CoupangProductResponse<CoupangProductResponse.CoupangUsedItemNudgeData>>() {
        });
    }

    @Override
    public CoupangReturnMResponse getReturnProduct(String productId, String itemId, String vendorItemId) {
        String uri = String.format("/vm/products/%s/items/%s/offers/offer-item-list?sdpVisitKey=&vendorItemId=%s", productId, itemId, vendorItemId);
        WebClient.ResponseSpec responseSpec = coupangMWebClient
                .get()
                .uri(uri)
                .retrieve();
        return WebClientResolver.resolve(responseSpec, new ParameterizedTypeReference<CoupangReturnMResponse>() {
        });
    }

    @Override
    public CoupangReturnMResponse.CoupangMData getProductDetail(String productId, String vendorItemId) {
        String uri = String.format("/vm/v4/enhanced-pdp/products/%s?vendorItemId=%s", productId, vendorItemId);
        WebClient.ResponseSpec responseSpec = coupangMWebClient
                .get()
                .uri(uri)
                .retrieve();
        return WebClientResolver.resolve(responseSpec, new ParameterizedTypeReference<CoupangReturnMResponse.CoupangMData>() {
        });
    }
}
