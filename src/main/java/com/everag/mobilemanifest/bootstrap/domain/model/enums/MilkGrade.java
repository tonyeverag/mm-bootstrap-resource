package com.everag.mobilemanifest.bootstrap.domain.model.enums;

public enum MilkGrade  implements CodeAndDisplayNameEnum<Integer>{
    GRADE_A(0, "Grade A"),
    GRADE_B(1, "Grade B");


    private final Integer code;
    private final String displayName;

    MilkGrade(int code, String displayName){
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
