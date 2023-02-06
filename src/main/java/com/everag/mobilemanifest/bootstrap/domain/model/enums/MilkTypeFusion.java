package com.everag.mobilemanifest.bootstrap.domain.model.enums;

public enum MilkTypeFusion  implements CodeAndDisplayNameEnum<String> {

    CNV("CNV", "Conventional", 0, 1),
    RBF("RBF", "rBST-Free", 1, 2),
    ORG("ORG", "Organic", 2, 3),
    OGRFD("OGRFD", "Organic Grass-Fed", 3, 4),
    PVNGMO("PV_NGMO", "PV non-GMO", 4, 5),
    PVORG("PV_ORG", "PV Organic", 5, 6);

    private final String code;
    private final String displayName;
    private final int hierarchyPriority;
    private final Integer milkTypeCode;

    public static boolean areCompatibleMilkTypesFusion(MilkTypeFusion milkType, MilkTypeFusion usedAsMilkType) {
        return usedAsMilkType.getHierarchyPriority() <= milkType.getHierarchyPriority();
    }

    public static MilkTypeFusion getByCode(String code) {
        MilkTypeFusion[] var1 = values();
        int var2 = var1.length;

        for(int var3 = 0; var3 < var2; ++var3) {
            MilkTypeFusion type = var1[var3];
            if (type.getCode().equals(code)) {
                return type;
            }
        }

        return null;
    }

    public String getCode() {
        return this.code;
    }

    public String getDisplayName() {
        return this.displayName;
    }

    public int getHierarchyPriority() {
        return this.hierarchyPriority;
    }

    public Integer getMilkTypeCode() {
        return this.milkTypeCode;
    }

    public MilkType getMilkType() {
        return MilkType.getByCode(milkTypeCode);
    }

    MilkTypeFusion(final String code, final String displayName, final int hierarchyPriority, Integer milkTypeCode) {
        this.code = code;
        this.displayName = displayName;
        this.hierarchyPriority = hierarchyPriority;
        this.milkTypeCode = milkTypeCode;
    }
}
