package com.everag.mobilemanifest.bootstrap.domain.model.enums;

public enum ReadMethod implements CodeAndDisplayNameEnum<Integer> {
    STICK(0, "Stick"),
    SCALE(1, "Scale"),
    FLOW_METER(2, "Meter"),
    ESTIMATE(3, "Estimate");

    private final Integer code;
    private final String displayName;

    private ReadMethod(Integer code, String displayName) {
        this.code = code;
        this.displayName = displayName;
    }

    @Override
    public Integer getCode() {
        return code;
    }

    @Override
    public String getDisplayName() {
        return displayName;
    }

}