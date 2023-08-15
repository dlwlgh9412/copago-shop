package com.copago.scheduler.service;

import com.copago.common.entity.alarm.AlarmProductEntity;
import com.copago.common.entity.alarm.AlarmTargetEntity;
import com.copago.common.infrastructer.client.coupang.CoupangClient;
import com.copago.common.infrastructer.client.coupang.CoupangPartnerClient;
import com.copago.common.infrastructer.client.coupang.dto.request.CoupangPartnerRequest;
import com.copago.common.infrastructer.client.coupang.dto.response.CoupangPartnerResponse;
import com.copago.common.infrastructer.client.coupang.dto.response.CoupangReturnMResponse;
import com.copago.common.infrastructer.repository.alarm.AlarmProductRepository;
import com.copago.common.infrastructer.repository.alarm.AlarmTargetRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AlarmProductTrackingServiceImpl implements AlarmProductTrackingService {
    private final AlarmProductRepository alarmProductRepository;
    private final AlarmTargetRepository alarmTargetRepository;
    private final CoupangClient coupangClient;
    private final CoupangPartnerClient coupangPartnerClient;
    private final String deepLinkPlaceHolderUrl = "https://www.coupang.com/vp/products/%s?itemId=%s&vendorItemId=%s";

    @Transactional
    public void updateByNewProduct() {
        List<AlarmProductEntity> products = alarmProductRepository.findAllByIsNewTrue();

        products.forEach(product -> {
            CoupangReturnMResponse.CoupangMData response = coupangClient.getProductDetail(product.getCoupangId(), product.getVendorId());
            CoupangPartnerResponse<List<CoupangPartnerResponse.ShortenUrl>> deepLinkResponse = coupangPartnerClient.getDeepLink(new CoupangPartnerRequest.DeepLink(Collections.singletonList(String.format(deepLinkPlaceHolderUrl, product.getCoupangId(), product.getItemId(), product.getVendorId())), "telegram"));

            if (response != null) {
                product.updateByNewProduct(response, deepLinkResponse);
            }
        });
        alarmProductRepository.saveAll(products);
    }

    @Transactional
    public void update() {
        List<AlarmProductEntity> products = alarmProductRepository.findAllByIsNewFalse();
        List<AlarmTargetEntity> alarmTargetList = new ArrayList<>();
        products.forEach(product -> {
            CoupangReturnMResponse.CoupangMData response = coupangClient.getProductDetail(product.getCoupangId(), product.getVendorId());
            CoupangPartnerResponse<List<CoupangPartnerResponse.ShortenUrl>> deepLinkResponse = coupangPartnerClient.getDeepLink(new CoupangPartnerRequest.DeepLink(Collections.singletonList(String.format(deepLinkPlaceHolderUrl, product.getCoupangId(), product.getItemId(), product.getVendorId())), "telegram"));

            if (response != null) {
                product.update(response);
                product.updateUrl(deepLinkResponse);
            }
            alarmTargetList.add(new AlarmTargetEntity(product.getId()));
        });
        alarmProductRepository.saveAll(products);
        alarmTargetRepository.saveAll(alarmTargetList);
    }
}
