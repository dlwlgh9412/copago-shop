package com.copago.scheduler.service;

import com.copago.common.constants.Telegram;
import com.copago.common.entity.message.MessageEntity;
import com.copago.common.entity.product.ProductEntity;
import com.copago.common.infrastructer.client.coupang.CoupangClient;
import com.copago.common.infrastructer.client.coupang.CoupangPartnerClient;
import com.copago.common.infrastructer.client.coupang.dto.request.CoupangPartnerRequest;
import com.copago.common.infrastructer.client.coupang.dto.response.CoupangPartnerResponse;
import com.copago.common.infrastructer.client.coupang.dto.response.CoupangProductResponse;
import com.copago.common.infrastructer.client.coupang.dto.response.CoupangReturnMResponse;
import com.copago.common.infrastructer.repository.message.MessageRepository;
import com.copago.common.infrastructer.repository.product.ProductRepository;
import com.copago.common.utils.Extensions;
import com.copago.scheduler.dto.ProductReturnResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.*;

@Service
@RequiredArgsConstructor
public class ProductTrackingServiceImpl implements ProductTrackingService {
    private final CoupangClient coupangClient;
    private final CoupangPartnerClient coupangPartnerClient;
    private final ProductRepository productRepository;
    private final MessageRepository messageRepository;

    @Override
    @Async
    @Transactional
    public void searchBatch(List<ProductEntity> returnItems, Boolean isTelegram) {

        if (isTelegram) {
            List<MessageEntity> messageList = new ArrayList<>();
            returnItems.stream().map(this::search).filter(Objects::nonNull).forEach(product -> {
                messageList.add(new MessageEntity(Telegram.COPAGO.getChatId(), createMessageByProductReturnResponse(product)));
            });

            messageRepository.saveAll(messageList);
        } else {
            returnItems.forEach(this::search);
        }
    }

    private ProductReturnResponse search(ProductEntity returnItem) {
        CoupangReturnMResponse response = coupangClient.getReturnProduct(returnItem.getCoupangId(), returnItem.getItemId(), returnItem.getVendorId());
        CoupangReturnMResponse.CoupangMData detail = coupangClient.getProductDetail(returnItem.getCoupangId(), returnItem.getVendorId());

        CoupangReturnMResponse.CoupangReturnMVendorItem vendorItem = null;
        Long originPrice = null;
        Long salePrice = null;
        if (response != null && response.getVendorItems() != null && !response.getVendorItems().isEmpty()) {
            vendorItem = response.getVendorItems().stream().findFirst().get();
        }

        if (vendorItem != null && vendorItem.getPrice() != null) {
            if (StringUtils.hasText(vendorItem.getPrice().getSalePrice())) {
                originPrice = Extensions.removeDotToLong(vendorItem.getPrice().getSalePrice());
            }

            if (StringUtils.hasText(vendorItem.getPrice().getCouponPrice())) {
                salePrice = Extensions.removeDotToLong(vendorItem.getPrice().getCouponPrice());
            }
        }

        if (salePrice != null && salePrice.equals(returnItem.getPrice()) && !returnItem.getIsNew()) return null;

        returnItem.setPrice(salePrice);
        returnItem.setOriginPrice(originPrice);

        String cardInfo = null;
        if (detail != null && detail.getPanelContainerVo() != null && !detail.getPanelContainerVo().getPanelList().isEmpty()) {
            CoupangReturnMResponse.CoupangMPanelContainerContent1 content1 = detail.getPanelContainerVo().getPanelList().get(0);

            if (content1 != null && !content1.getContents().isEmpty()) {
                CoupangReturnMResponse.CoupangMPanelContainerContent2 content2 = content1.getContents().get(0);

                if (content2 != null && !content2.getContents().isEmpty()) {
                    CoupangReturnMResponse.CoupangMPanelContainerContent3 content3 = content2.getContents().get(0);

                    if (content3 != null && !content3.getTitle().isEmpty()) {
                        StringBuilder stringBuilder = new StringBuilder();
                        content3.getTitle().forEach(title -> stringBuilder.append(title.get("text")));
                        cardInfo = stringBuilder.toString();
                    }
                }
            }
        }
        returnItem.setCardInfo(cardInfo);
        returnItem.setIsNew(false);
        returnItem.setSale(returnItem.getSalePercent());
        productRepository.save(returnItem);

        if (vendorItem == null || salePrice == null || salePrice == 0L) return null;

        Long cardPrice = null;
        if (StringUtils.hasText(returnItem.getCardInfo())) {
            Long percent = Extensions.removeToLong(returnItem.getCardInfo());
            cardPrice = salePrice * (100 - percent) / 100;
        }

        if (!StringUtils.hasText(returnItem.getUrl())) {
            String url = getUrl(returnItem.getCoupangId(), returnItem.getItemId(), returnItem.getVendorId());
            CoupangPartnerResponse<List<CoupangPartnerResponse.ShortenUrl>> deepLinkResponse = coupangPartnerClient.getDeepLink(new CoupangPartnerRequest.DeepLink(Collections.singletonList(url), "telegram"));

            if (deepLinkResponse != null && deepLinkResponse.getData() != null && !deepLinkResponse.getData().isEmpty()) {
                returnItem.setUrl(deepLinkResponse.getData().stream().findFirst().get().getShortenUrl());
                productRepository.save(returnItem);
            }
        }

        return ProductReturnResponse.builder()
                .title(returnItem.getTitle())
                .model(returnItem.getModel())
                .url(StringUtils.hasText(returnItem.getUrl()) ? returnItem.getUrl() : getDefaultUrl(returnItem.getCoupangId()))
                .coupangPrice(originPrice != null ? originPrice.toString() : null)
                .salePrice(salePrice.toString())
                .detail(returnItem.getDetail())
                .cardInfo(returnItem.getCardInfo())
                .cardPrice(cardPrice.toString())
                .status(vendorItem.getBadgeMap().get("OFFER_BADGE").getText())
                .build();
    }

    private String createMessageByProductReturnResponse(ProductReturnResponse response) {
        return String.format(
                "<b>%s</b>\n" +
                        "설명: %s\n" +
                        "상태: %s\n" +
                        "카드할인: %s\n" +
                        "가격: %s원\n" +
                        "최저가: <b>%s</b>원\n" +
                        "카드할인가: <b>%s</b>원\n" +
                        "정보: %s",
                response.getTitle(), response.getDetail(), response.getStatus(), StringUtils.hasText(response.getCardInfo()) ? response.getCardInfo() : "없음", Extensions.toMoney(response.getCoupangPrice()), Extensions.toMoney(response.getSalePrice()), Extensions.toMoney(response.getCardPrice()), response.getUrl()
        );
    }

    private String getUrl(String id, String itemId, String vendorItemId) {
        String defaultUrl = String.format("https://www.coupang.com/vp/products/%s?itemId=%s&vendorItemId=%s", id, itemId, vendorItemId);
        CoupangProductResponse<CoupangProductResponse.CoupangUsedItemNudgeData> usedUrl = coupangClient.getUsedProductDetail(id, itemId, vendorItemId);

        if (usedUrl == null || usedUrl.getRData().getUsedItemNudge() == null) return defaultUrl;

        String url = usedUrl.getRData().getUsedItemNudge().getOfferItmes().stream().findFirst().get().getAction().getData().getScheme();
        MultiValueMap<String, String> params = UriComponentsBuilder.fromUriString(url).build().getQueryParams();

        return String.format("https://www.coupang.com/vp/products/%s?itemId=%s&vendorItemId=%s", params.getFirst("pId"), params.getFirst("itemId"), params.getFirst("vendorItemId"));
    }

    private String getDefaultUrl(String id) {
        return String.format("https://link.coupang.com/re/AFFSDP?lptag=AF1293179&pageKey=%s&traceid=V0-181-225207e7181231e2", id);
    }
}
