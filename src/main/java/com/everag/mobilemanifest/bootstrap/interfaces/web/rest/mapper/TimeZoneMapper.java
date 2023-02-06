package com.everag.mobilemanifest.bootstrap.interfaces.web.rest.mapper;

import com.everag.mobilemanifest.bootstrap.domain.model.enums.MMTimeZone;
import org.mapstruct.Mapper;

import java.time.ZoneId;
import java.util.Optional;

@Mapper(componentModel = "springLazy")
public interface TimeZoneMapper {
    default ZoneId MMTimeZoneToZoneId(MMTimeZone mmTimeZone) {
        if (mmTimeZone == null) {
            return null;
        }
        var zoneId = mmTimeZone.getCode();


        return zoneId;
    }

    default MMTimeZone zoneIdToMMTimeZone(ZoneId zoneId) {
        if (zoneId == null) {
            return null;
        }
        Optional<MMTimeZone> tz = MMTimeZone.of(zoneId);
        return tz.orElse(null);
    }
}
