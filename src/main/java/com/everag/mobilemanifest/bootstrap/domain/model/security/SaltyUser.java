package com.everag.mobilemanifest.bootstrap.domain.model.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.ArrayList;
import java.util.Collection;

public class SaltyUser extends User {
    private static final long serialVersionUID = 1L;

    private Long id;

    public Long getId() {
        return id;
    }

    public SaltyUser(String username, String password, Long id,
                     boolean enabled, boolean accountNonExpired,
                     boolean credentialsNonExpired, boolean accountNonLocked,
                     Collection<? extends GrantedAuthority> authorities) {
        super(username, password, enabled, accountNonExpired,
                credentialsNonExpired, accountNonLocked, authorities);
        this.id = id;
    }

    public SaltyUser(SysUser user, Collection<GrantedAuthority> authorities) {
        this(user.getUsername(), user.getPasswordHash(), user.getId(), user
                .getEnabled(), true, !user.getPasswordExpired(), user.accountNonLocked(), authorities);
    }

    public SaltyUser(SysUser user) {
        this(user, new ArrayList<GrantedAuthority>());
        if (user.getId() == null) {
            throw new NullPointerException(
                    "SysUser.getId() cannot be null because it's used for password salting purposes.");
        }
    }
}
