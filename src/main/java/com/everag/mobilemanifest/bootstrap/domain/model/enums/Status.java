package com.everag.mobilemanifest.bootstrap.domain.model.enums;

public enum Status implements CodeAndDisplayNameEnum<Integer> {
    ACTIVE (1, "Active"),
    INACTIVE (2, "Inactive");

    private final Integer code;
    private final String displayName;

    private Status(Integer code, String displayName) {
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
