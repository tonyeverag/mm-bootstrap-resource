package com.everag.mobilemanifest.bootstrap.application;

import com.everag.mobilemanifest.bootstrap.interfaces.web.rest.dto.location.LocationDTO;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;

public interface GeoLocationService {
   boolean withinGeoFence(LocationDTO location, BigDecimal latitude, BigDecimal longitude);
}
