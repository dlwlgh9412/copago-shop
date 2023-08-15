package com.copago.common.service;

import com.copago.common.infrastructer.client.coupang.CoupangPartnerClient;
import com.copago.common.infrastructer.client.coupang.dto.request.CoupangPartnerRequest;
import com.copago.common.infrastructer.client.coupang.dto.response.CoupangPartnerResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CoupangPartnerService {
    private final CoupangPartnerClient coupangPartnerClient;
    private Long apiCallCount = 0L;
    private String apiTime = "";



    private Boolean callCount() {
        String time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm"));
        if (apiTime.equals(time)) {
            apiCallCount++;
            return apiCallCount < 40;
        } else {
            apiTime = time;
            apiCallCount = 1L;
            return true;
        }
    }

    public Object getReportOrders(String startDate, String endDate) {
        if (!callCount()) return null;
        return coupangPartnerClient.getReportOrders(startDate, endDate);
    }

    public CoupangPartnerResponse<List<CoupangPartnerResponse.ShortenUrl>> getDeepLink(CoupangPartnerRequest.DeepLink request) {
        if (!callCount()) return null;
        return coupangPartnerClient.getDeepLink(request);
    }

    public Object getCommission(String startDate, String endDate) {
        if (!callCount()) return null;
        return coupangPartnerClient.getCommission(startDate, endDate);
    }
}
