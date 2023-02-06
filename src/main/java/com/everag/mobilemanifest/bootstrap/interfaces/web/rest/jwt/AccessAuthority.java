package com.everag.mobilemanifest.bootstrap.interfaces.web.rest.jwt;

import org.springframework.security.core.GrantedAuthority;

public class AccessAuthority implements GrantedAuthority {
    private static final long serialVersionUID = 1L;

    private Long id;

    private AccessScope scope;

    private AccessType accessType;

    public AccessAuthority(Long id, AccessScope scope, AccessType accessType) {
        this.id = id;
        this.scope = scope;
        this.accessType = accessType;
    }

    @Override
    public String getAuthority() {
        return new StringBuilder(accessType.toString())
                .append("-")
                .append(id)
                .append("-")
                .append(scope)
                .toString();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AccessScope getScope() {
        return scope;
    }

    public void setScope(AccessScope scope) {
        this.scope = scope;
    }

    public AccessType getAccessType() {
        return accessType;
    }

    public void setAccessType(AccessType accessType) {
        this.accessType = accessType;
    }

    @Override
    public String toString() {
        return "AccessAuthority [id=" + id + ", scope=" + scope + ", accessType=" + accessType + "]";
    }
}
