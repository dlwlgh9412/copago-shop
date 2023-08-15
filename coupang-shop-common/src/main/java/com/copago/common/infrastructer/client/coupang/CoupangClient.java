package com.copago.common.infrastructer.client.coupang;

import com.copago.common.infrastructer.client.coupang.dto.response.CoupangProductResponse;
import com.copago.common.infrastructer.client.coupang.dto.response.CoupangReturnMResponse;

public interface CoupangClient {
    CoupangProductResponse<CoupangProductResponse.Product> search(String filter, String nextPageKey);
    CoupangProductResponse<CoupangProductResponse.Product> getAppleReturnMallList(Long returnId, String nextPageKey);
    CoupangProductResponse<CoupangProductResponse.CoupangUsedItemNudgeData> getUsedProductDetail(String productId, String itemId, String vendorItemId);
    CoupangReturnMResponse getReturnProduct(String productId, String itemId, String vendorItemId);
    CoupangReturnMResponse.CoupangMData getProductDetail(String productId, String vendorItemId);
}
