package com.copago.message.service;

import com.copago.common.entity.alarm.AlarmProductEntity;
import com.copago.common.entity.alarm.AlarmProductMappingEntity;
import com.copago.common.entity.alarm.AlarmType;
import com.copago.common.entity.alarm.AlarmUserEntity;
import com.copago.common.infrastructer.repository.alarm.AlarmProductMappingRepository;
import com.copago.common.infrastructer.repository.alarm.AlarmProductRepository;
import com.copago.common.infrastructer.repository.alarm.AlarmUserRepository;
import com.copago.common.utils.Extensions;
import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;


@Service
@RequiredArgsConstructor
public class ProductCommandServiceImpl implements ProductCommandService {
    private final AlarmUserRepository alarmUserRepository;
    private final AlarmProductRepository alarmProductRepository;
    private final AlarmProductMappingRepository alarmProductMappingRepository;

    @Override
    @Transactional
    public String addProduct(String chatId, String url, String price) {
        AlarmUserEntity user = alarmUserRepository.findByChatIdAndType(chatId, AlarmType.TELEGRAM).orElseGet(() -> alarmUserRepository.save(new AlarmUserEntity(chatId, AlarmType.TELEGRAM)));

        if (50 <= user.getProductCount()) {
            return "최대 등록은 50개 입니다.";
        }

        String uri;
        if (url.contains("https://link.coupang.com/")) {
            try {
                uri = Jsoup.connect(url).get().location();
            } catch (Exception e) {
                return "공유링크가 아닌 직접 링크를 넣어주세요";
            }
        } else if (url.contains("https://www.coupang.com/") || url.contains("https://m.coupang.com/")) {
            uri = url;
        } else {
            return "비정상적인 URL입니다.";
        }

        UriComponents uriComponents = UriComponentsBuilder.fromUriString(uri).build();
        MultiValueMap<String, String> query = uriComponents.getQueryParams();
        List<String> pathList = uriComponents.getPathSegments();

        if (pathList.size() != 3) return "비정상적인 URL입니다.";

        String coupangId = pathList.get(2);
        String itemId = query.getFirst("itemId");
        String vendorItemId = query.getFirst("vendorItemId");

        AlarmProductEntity product = alarmProductRepository.findByCoupangIdAndItemIdAndVendorId(coupangId, itemId, vendorItemId).orElseGet(() -> alarmProductRepository.save(new AlarmProductEntity(coupangId, itemId, vendorItemId)));

        if (alarmProductMappingRepository.existsByUidAndPid(user.getId(), product.getId())) {
            return "이미 등록된 상품입니다.";
        }

        alarmProductMappingRepository.save(new AlarmProductMappingEntity(user.getId(), product.getId(), Extensions.removeToLong(price)));
        user.increaseProductCnt();
        alarmUserRepository.save(user);

        return "알람이 정상적으로 등록되었습니다.";
    }
}
