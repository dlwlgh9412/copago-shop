package com.copago.message.service;

import com.copago.common.entity.alarm.AlarmProductMappingEntity;
import com.copago.common.entity.message.MessageEntity;
import com.copago.common.infrastructer.repository.alarm.AlarmProductMappingRepository;
import com.copago.common.infrastructer.repository.alarm.AlarmProductRepository;
import com.copago.common.infrastructer.repository.alarm.AlarmTargetRepository;
import com.copago.common.infrastructer.repository.alarm.AlarmUserRepository;
import com.copago.common.infrastructer.repository.message.MessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AlarmTargetServiceImpl implements AlarmTargetService {
    private final AlarmTargetRepository alarmTargetRepository;
    private final AlarmProductRepository alarmProductRepository;
    private final AlarmUserRepository alarmUserRepository;
    private final AlarmProductMappingRepository alarmProductMappingRepository;
    private final MessageService messageService;
    private final MessageRepository messageRepository;

    @Override
    @Transactional
    public void makeMessage() {
        alarmTargetRepository.findAllByIsSend(true).forEach(it -> {

            alarmProductRepository.findById(it.getPid()).ifPresent(product -> {
                List<AlarmProductMappingEntity> mappingList = alarmProductMappingRepository.findByPidAndPriceIsGreaterThanEqual(product.getId(), StringUtils.hasText(product.getCardInfo()) ? product.getCardPrice() : product.getPrice());
                String message = messageService.makeMessageByAlarmProduct(product, product.getCardPrice());

                mappingList.forEach(mapping -> alarmUserRepository.findById(mapping.getUid()).ifPresent(user -> {
                    messageRepository.save(new MessageEntity(user.getChatId(), message));
                    it.setSend();
                    alarmTargetRepository.save(it);
                }));

            });
        });
    }
}
