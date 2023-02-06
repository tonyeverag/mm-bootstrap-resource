package com.everag.mobilemanifest.bootstrap.domain.model.enums;

public enum EntityStatus implements CodeAndDisplayNameEnum<Integer> {
    NEW (1, "New"),
    CONFIRMED (2, "Confirmed");

    private final Integer code;
    private final String displayName;

    private EntityStatus(Integer code, String displayName) {
        this.code = code;
        this.displayName = displayName;
    }

    @Override
    public Integer getCode() {
        return this.code;
    }
    @Override
    public String getDisplayName() {
        return this.displayName;
    }

}
