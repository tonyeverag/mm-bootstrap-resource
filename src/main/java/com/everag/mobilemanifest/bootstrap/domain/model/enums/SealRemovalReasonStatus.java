package com.everag.mobilemanifest.bootstrap.domain.model.enums;

public enum SealRemovalReasonStatus implements CodeAndDisplayNameEnum<Integer> {

    ACTIVE(0,"Active"),
    REMOVED(1,"Removed");


    private final Integer code;
    private final String displayName;

    private SealRemovalReasonStatus(Integer code, String displayName) {
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
