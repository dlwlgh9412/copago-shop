package com.copago.common.infrastructer.repository.message;

import com.copago.common.entity.message.MessageEntity;
import com.copago.common.entity.message.MsgStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<MessageEntity, Long> {
    List<MessageEntity> findByStatus(MsgStatus status);
}
