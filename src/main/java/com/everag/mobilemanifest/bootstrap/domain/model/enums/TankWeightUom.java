package com.everag.mobilemanifest.bootstrap.domain.model.enums;

public enum TankWeightUom implements CodeAndDisplayNameEnum<Integer> {
    POUNDS(0, "Pounds"),
    USGAL(1, "Gallons");

    private final Integer code;
    private final String displayName;

    TankWeightUom(int code, String displayName){
        this.code=code;
        this.displayName=displayName;
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
