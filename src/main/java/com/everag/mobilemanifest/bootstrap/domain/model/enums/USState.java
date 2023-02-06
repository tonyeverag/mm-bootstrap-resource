package com.everag.mobilemanifest.bootstrap.domain.model.enums;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum USState implements CodeAndDisplayNameEnum<String> {
    AL("AL", "Alabama"),
    AK("AK", "Alaska"),
    AS("AS", "American Samoa"),
    AZ("AZ", "Arizona"),
    AR("AR", "Arkansas"),
    CA("CA", "California"),
    CO("CO", "Colorado"),
    CT("CT", "Connecticut"),
    DE("DE", "Delaware"),
    DC("DC", "District of Columbia"),
    //	FM("FM", "Micronesia"),
    FL("FL", "Florida"),
    GA("GA", "Georgia"),
    //	GU("GU", "Guam"),
    HI("HI", "Hawaii"),
    ID("ID", "Idaho"),
    IL("IL", "Illinois"),
    IN("IN", "Indiana"),
    IA("IA", "Iowa"),
    KS("KS", "Kansas"),
    KY("KY", "Kentucky"),
    LA("LA", "Louisiana"),
    ME("ME", "Maine"),
    //	MH("MH", "Marshall Islands"),
    MD("MD", "Maryland"),
    MA("MA", "Massachusetts"),
    MI("MI", "Michigan"),
    MN("MN", "Minnesota"),
    MS("MS", "Mississippi"),
    MO("MO", "Missouri"),
    MT("MT", "Montana"),
    NE("NE", "Nebraska"),
    NV("NV", "Nevada"),
    NH("NH", "New Hampshire"),
    NJ("NJ", "New Jersey"),
    NM("NM", "New Mexico"),
    NY("NY", "New York"),
    NC("NC", "North Carolina"),
    ND("ND", "North Dakota"),
    //	MP("MP", "Northern Marianas"),
    OH("OH", "Ohio"),
    OK("OK", "Oklahoma"),
    OR("OR", "Oregon"),
    //	PW("PW", "Palau"),
    PA("PA", "Pennsylvania"),
    //	PR("PR", "Puerto Rico"),
    RI("RI", "Rhode Island"),
    SC("SC", "South Carolina"),
    SD("SD", "South Dakota"),
    TN("TN", "Tennesee"),
    TX("TX", "Texas"),
    UT("UT", "Utah"),
    VT("VT", "Vermont"),
    //	VI("VI", "Virgin Islands"),
    VA("VA", "Virginia"),
    WA("WA", "Washington"),
    WV("WV", "West Virginia"),
    WI("WI", "Wisconsin"),
    WY("WY", "Wyoming");

    private final String code;
    private final String displayName;

    USState(String code, String displayName){
        this.code=code;
        this.displayName=displayName;
    }

    @Override
    public String getCode() {
        return this.code;
    }
    @Override
    public String getDisplayName() {
        return this.displayName;
    }

    public static List<USState> byString(String search) {
        return Stream.of(USState.values())
                .filter( state -> state.getDisplayName().toLowerCase().contains(search.toLowerCase()))
                .collect(Collectors.toList());
    }

    public static USState toEnumFromCode(String code) {
        if (code == null)
        {
            return null;
        }

        for (USState s : (USState[])USState.class.getEnumConstants()) {
            if (code.equals(s.getCode())) {
                return s;
            }
        }
        return null;
    }
}
