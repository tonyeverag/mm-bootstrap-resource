package com.everag.mobilemanifest.bootstrap.domain.model.enums;

import java.util.Arrays;

public enum MilkType  implements CodeAndDisplayNameEnum<Integer> {

    CONVENTIONAL(1, "Conventional", "CNV"),
    RBST_FREE(2, "RBST Free", "RBF"),
    ORGANIC(3, "Organic", "ORG"),
    ORGANIC_GRASSFED(4, "Organic Grass-Fed", "OGRFD"),
    PV_NGMO(5, "Project Verified non-GMO", "PV_NGMO"),
    PV_ORGANIC(6, "Project Verified Organic", "PV_ORG");

    private final Integer code;
    private final String displayName;

    private final String milkTypeFusionCode;


    public static boolean areCompatibleMilkTypesFusion(MilkTypeFusion milkType, MilkTypeFusion usedAsMilkType) {
        return usedAsMilkType.getHierarchyPriority() <= milkType.getHierarchyPriority();
    }

    MilkType(Integer code, String displayName, String milkTypeFusionCode) {
        this.code = code;
        this.displayName = displayName;
        this.milkTypeFusionCode = milkTypeFusionCode;
    }

    @Override
    public Integer getCode() {
        return code;
    }

    @Override
    public String getDisplayName() {
        return displayName;
    }

    public String getMilkTypeFusionCode() {
        return milkTypeFusionCode;
    }

    public MilkTypeFusion getMilkTypeFusion() {
        return MilkTypeFusion.getByCode(getMilkTypeFusionCode());
    }

    public static MilkType getByCode(Integer code) {
        MilkType[] milkTypes = values();
        return Arrays.stream(milkTypes).filter(mt -> mt.getCode().equals(code)).findFirst().orElse(null);
    }
}
