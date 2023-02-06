package com.everag.mobilemanifest.bootstrap.domain.model.security;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class LoginAttempt {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="LOGIN_ATTEMPT_SEQ")
    @SequenceGenerator(name="LOGIN_ATTEMPT_SEQ", sequenceName="LOGIN_ATTEMPT_SEQ", initialValue = 1, allocationSize = 1)
    private Long id;

    private LocalDateTime loginTime;

    @ManyToOne
    private SysUser sysUser;

    public LoginAttempt() {
        loginTime = LocalDateTime.now();
    }

    @Version
    @Column(name = "TX_LOCK")
    private Integer txLock;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("LoginTime: ").append(getLoginTime()).append(", ");
        sb.append("SysUser: ").append(getSysUser());
        return sb.toString();
    }
}
