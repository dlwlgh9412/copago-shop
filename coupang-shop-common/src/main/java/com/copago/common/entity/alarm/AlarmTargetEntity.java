package com.copago.common.entity.alarm;

import com.copago.common.entity.AbstractBaseEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@Table(name = "tb_alarm_target")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AlarmTargetEntity extends AbstractBaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "pid")
    private Long pid;

    @Column(name = "is_send")
    private Boolean isSend = false;

    public AlarmTargetEntity(Long pid) {
        this.pid = pid;
    }

    public void setSend() {
        this.isSend = true;
    }
}
