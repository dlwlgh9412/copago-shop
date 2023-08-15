package com.copago.common.entity.alarm;

import com.copago.common.entity.AbstractBaseEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@Table(name = "tb_alarm_product_mapping")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AlarmProductMappingEntity extends AbstractBaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "uid")
    private Long uid;

    @Column(name = "pid")
    private Long pid;

    @Column(name = "price")
    private Long price;

    public AlarmProductMappingEntity(Long uid, Long pid, Long price) {
        this.uid = uid;
        this.pid = pid;
        this.price = price;
    }

    public void changePrice(Long price) {
        this.price = price;
    }
}
