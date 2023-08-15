package com.copago.common.infrastructer.client.coupang;

import com.copago.common.infrastructer.client.coupang.dto.request.CoupangPartnerRequest;
import com.copago.common.infrastructer.client.coupang.dto.response.CoupangPartnerResponse;

import java.util.List;

public interface CoupangPartnerClient {
    CoupangPartnerResponse<List<CoupangPartnerResponse.ShortenUrl>> getDeepLink(CoupangPartnerRequest.DeepLink request);
    Object getReportOrders(String startDate, String endDate);
    CoupangPartnerResponse.Commission getCommission(String startDate, String endDate);
}
