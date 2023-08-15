package com.copago.api.service;

import com.copago.api.exception.AlarmException;
import com.copago.common.exception.ErrorCode;
import com.copago.api.web.dto.request.AlarmAddRequest;
import com.copago.api.web.dto.request.AlarmModifyRequest;
import com.copago.api.web.dto.response.AlarmProductResponse;
import com.copago.common.entity.alarm.AlarmProductEntity;
import com.copago.common.entity.alarm.AlarmProductMappingEntity;
import com.copago.common.entity.alarm.AlarmType;
import com.copago.common.entity.alarm.AlarmUserEntity;
import com.copago.common.infrastructer.client.coupang.CoupangClient;
import com.copago.common.infrastructer.client.coupang.dto.response.CoupangReturnMResponse;
import com.copago.common.infrastructer.repository.alarm.AlarmProductMappingRepository;
import com.copago.common.infrastructer.repository.alarm.AlarmProductRepository;
import com.copago.common.infrastructer.repository.alarm.AlarmUserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class AlarmProductService {
    private final AlarmProductRepository alarmProductRepository;
    private final AlarmProductMappingRepository alarmProductMappingRepository;
    private final AlarmUserRepository alarmUserRepository;
    private final ObjectMapper objectMapper;

    private final CoupangClient coupangClient;


    @Transactional(readOnly = true)
    public Page<AlarmProductResponse> getProducts(String chatId, Integer page, Integer size) {
        AlarmUserEntity user = alarmUserRepository.findByChatIdAndType(chatId, AlarmType.TELEGRAM).orElseThrow(() -> new AlarmException(ErrorCode.BAD_REQUEST, "존재하지 않는 채팅 ID 입니다."));
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by("createdAt").descending());

        return alarmProductMappingRepository.findByUid(user.getId(), pageRequest).map(entity -> {
            AlarmProductEntity product = alarmProductRepository.findById(entity.getPid()).orElseThrow(() -> new AlarmException(ErrorCode.BAD_REQUEST, "존재하지 않는 상품입니다."));
            return new AlarmProductResponse(product, product.getCardPrice(), product.getPrice());
        });
    }

    public void editProductPrice(String chatId, AlarmModifyRequest request) {
        AlarmUserEntity user = alarmUserRepository.findByChatIdAndType(chatId, AlarmType.TELEGRAM).orElseThrow(() -> new AlarmException(ErrorCode.BAD_REQUEST, "존재하지 않는 채팅 ID 입니다."));
        AlarmProductMappingEntity mapping = alarmProductMappingRepository.findByUidAndPid(user.getId(), request.getPid()).orElseThrow(() -> new AlarmException(ErrorCode.BAD_REQUEST, "등록되지 않은 상품 ID 입니다."));
        mapping.changePrice(request.getPrice());
        alarmProductMappingRepository.save(mapping);
    }

    public String addProduct(String chatId, AlarmAddRequest request) {
        UriComponents uriComponents = createCoupangUriComponents(request.getUrl());
        MultiValueMap<String, String> query = uriComponents.getQueryParams();
        List<String> pathSegments = uriComponents.getPathSegments();

        if (pathSegments.size() != 3) throw new AlarmException(ErrorCode.BAD_REQUEST, "비정상적인 URL입니다.");

        String coupangId = pathSegments.get(2);
        String itemId = query.getFirst("itemId");
        String vendorItemId = query.getFirst("vendorItemId");

        if (!StringUtils.hasText(itemId)) throw new AlarmException(ErrorCode.BAD_REQUEST, "비정상적인 URL입니다.");
        if (!StringUtils.hasText(vendorItemId)) {
            CoupangReturnMResponse response = coupangClient.getReturnProduct(coupangId, itemId, vendorItemId);
            Optional<CoupangReturnMResponse.CoupangReturnMVendorItem> item = response.getVendorItems().stream().findFirst();
            if (item.isPresent()) {
                vendorItemId = item.get().getVendorItemId().toString();
            }
        }

        String finalVendorItemId = vendorItemId;
        AlarmProductEntity product = alarmProductRepository.findByCoupangIdAndItemIdAndVendorId(coupangId, itemId, vendorItemId).orElseGet(
                () -> alarmProductRepository.save(new AlarmProductEntity(coupangId, itemId, finalVendorItemId))
        );

        AlarmUserEntity user = alarmUserRepository.findByChatIdAndType(chatId, AlarmType.TELEGRAM).orElseGet(() -> alarmUserRepository.save(new AlarmUserEntity(chatId, AlarmType.TELEGRAM)));
        if (100 <= user.getProductCount()) throw new AlarmException(ErrorCode.BAD_REQUEST, "최대 100까지 등록이 가능합니다.");

        if (alarmProductMappingRepository.existsByUidAndPid(user.getId(), product.getId())) {
            throw new AlarmException(ErrorCode.BAD_REQUEST, "이미 등록된 상품입니다.");
        } else {
            alarmProductMappingRepository.save(new AlarmProductMappingEntity(user.getId(), product.getId(), request.getPrice()));
            user.increaseProductCnt();
            alarmUserRepository.save(user);
        }

        return "정상적으로 상품이 등록되었습니다.";
    }

    public void deleteProduct(String chatId, Long pid) {
        AlarmUserEntity user = alarmUserRepository.findByChatIdAndType(chatId, AlarmType.TELEGRAM).orElseThrow(() -> new AlarmException(ErrorCode.BAD_REQUEST, "존재하지 않는 채팅 ID 입니다."));
        AlarmProductMappingEntity mapping = alarmProductMappingRepository.findByUidAndPid(user.getId(), pid).orElseThrow(() -> new AlarmException(ErrorCode.BAD_REQUEST, "등록되지 않은 상품 ID입니다."));
        alarmProductMappingRepository.delete(mapping);
        user.decreaseProductCnt();
        alarmUserRepository.save(user);
    }

    private UriComponents createCoupangUriComponents(String uri) {
        if (uri.contains("https://www.coupang.com/") || uri.contains("https://m.coupang.com/")) {
            return UriComponentsBuilder.fromUriString(uri).build();
        } else {
            throw new AlarmException(ErrorCode.BAD_REQUEST, "비정상적인 URL입니다.");
        }
    }
}
