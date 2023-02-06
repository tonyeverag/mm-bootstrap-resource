package com.everag.mobilemanifest.bootstrap.domain.model.security;

public enum BasicAuthorityType {
    LOAD_PERFORM_ACTION,
    LOAD_SET_ATTRIBUTE,
    LOAD_SUPPORT_WORKFLOW,

    IMPORT_SETUP_DATA,
    IMPORT_LOAD_DATA,
    IMPORT_MAPPING,

    EXPORT_MANIFEST,

    CSR_SEARCH,

    CONFIGURATION_EXPORT,
    CONFIGURATION_SETUP,
    CONFIGURATION_LOAD_ATTRIBUTE,
    CONFIGURATION_VIEW_ATTRIBUTE,
    CONFIGURATION_USER_ROLE,
    CONFIGURATION_CSR_USER,
    CONFIGURATION_DRIVER_USER,
    CONFIGURATION_APPLICATION_USER,
    CONFIGURATION_SUPER_USER,
    CONFIGURATION_YARD_FOREMAN_USER,
    CONFIGURATION_PLANT_USER,
    CONFIGURATION_PRODUCER_USER,
    CONFIGURATION_ASSET_MANAGER,
    CONFIGURATION_HAULER_USER,
    CONFIGURATION_SUPPLIER_USER;


    /**
     * Qualified name for this authority type, which is <code>"BASIC_"</code>
     * plus the <code>name()</code> of the enum.
     *
     * @return
     */
    public String qualifiedName() {
        return "BASIC_" + name();
    }
}
