package com.everag.mobilemanifest.bootstrap.domain.model.enums;

public enum StickReadingUom  implements CodeAndDisplayNameEnum<Integer> {
    INCHES(0, "Inches", "In"),
    CM(1, "Centimeters", "Cm");

    private final Integer code;
    private final String displayName;
    private final String shortName;

    StickReadingUom(int code, String displayName, String shortName){
        this.code = code;
        this.displayName = displayName;
        this.shortName = shortName;
    }

    @Override
    public Integer getCode() {
        return this.code;
    }
    @Override
    public String getDisplayName() {
        return this.displayName;
    }

    public String getShortName() {return this.shortName; }

}
