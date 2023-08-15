package com.copago.common.entity.alarm;

import com.copago.common.entity.AbstractBaseEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@Table(name = "tb_alarm_user")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AlarmUserEntity extends AbstractBaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "chat_id")
    private String chatId;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private AlarmType type;

    @Column(name = "name")
    private Long productCount = 0L;

    public AlarmUserEntity(String chatId, AlarmType type) {
        this.chatId = chatId;
        this.type = type;
    }

    public AlarmUserEntity(String chatId, AlarmType type, Long productCount) {
        this.chatId = chatId;
        this.type = type;
        this.productCount = productCount;
    }

    public void increaseProductCnt() {
        this.productCount++;
    }

    public void decreaseProductCnt() {
        this.productCount--;
    }
}
