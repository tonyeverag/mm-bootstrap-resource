package com.everag.mobilemanifest.bootstrap.domain.model.enums;

public enum BarcodeType  implements CodeAndDisplayNameEnum<Integer> {
    INTER2OF5(0, "InterleavedTwo0fFive");


    private final Integer code;
    private final String displayName;

    BarcodeType(int code, String displayName){
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
