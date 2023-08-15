package com.copago.common.entity.admin;

import com.copago.common.entity.AbstractBaseEntity;
import lombok.Getter;

import javax.persistence.*;

@Getter
@Entity
@Table(name = "tb_admin_user")
public class AdminUserEntity extends AbstractBaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "password")
    private String password;

    @Column(name = "user_name")
    private String userName;

    public void checkPassword(String requestPassword) {
        if (!password.equals(requestPassword))
            throw new IllegalArgumentException("아이디 또는 패스워드가 틀렸습니다.");
    }
}
