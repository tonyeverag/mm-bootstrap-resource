package com.everag.mobilemanifest.bootstrap.domain.model.enums;

import java.time.ZoneId;
import java.util.Optional;

public enum MMTimeZone implements CodeAndDisplayNameEnum<ZoneId>{
    AMERICA_NEW_YORK(ZoneId.of("America/New_York"), "America/New_York"),
    AMERICA_CHICAGO(ZoneId.of("America/Chicago"), "America/Chicago"),
    AMERICA_PHOENIX(ZoneId.of("America/Phoenix"), "America/Phoenix"),
    AMERICA_DENVER(ZoneId.of("America/Denver"), "America/Denver"),
    AMERICA_LOS_ANGELES(ZoneId.of("America/Los_Angeles"), "America/Los_Angeles");


    private final ZoneId code;
    private final String displayName;

    MMTimeZone(ZoneId code, String displayName){
        this.code=code;
        this.displayName=displayName;
    }

    @Override
    public ZoneId getCode() {
        return this.code;
    }

    @Override
    public String getDisplayName() {
        return this.displayName;
    }

    public static Optional<MMTimeZone> of(ZoneId zoneId) {

        for (int i=0; i < values().length; i++) {
            if (values()[i].getCode().equals(zoneId)) {
                return Optional.of(values()[i]);
            }
        }
        return Optional.empty();
    }
}
