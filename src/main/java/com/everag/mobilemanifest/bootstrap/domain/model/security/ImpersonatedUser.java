package com.everag.mobilemanifest.bootstrap.domain.model.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class ImpersonatedUser extends User {
    private Integer impersonatedByUserId;

    public ImpersonatedUser(String username, String password, Collection<? extends GrantedAuthority> authorities, String impersonatedByUserId) {
        super(username, password, authorities);
        this.impersonatedByUserId = Integer.valueOf(impersonatedByUserId);
    }

    public Integer getImpersonatedByUserId(){
        return this.impersonatedByUserId;
    }
}
