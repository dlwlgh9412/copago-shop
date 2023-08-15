package com.copago.common.entity.message;

import com.copago.common.entity.AbstractBaseEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@Table(name = "tb_message")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MessageEntity extends AbstractBaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "message")
    private String message;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private MsgStatus status = MsgStatus.READY;

    @Column(name = "channel_id")
    private String channelId;

    public MessageEntity(String message, String channelId) {
        this.message = message;
        this.channelId = channelId;
    }

    public void setStatus(MsgStatus status) {
        this.status = status;
    }
}
