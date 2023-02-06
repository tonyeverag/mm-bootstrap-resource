package com.everag.mobilemanifest.bootstrap.interfaces.web.rest.dto;

import com.everag.mobilemanifest.bootstrap.domain.model.location.Location;

import javax.validation.Payload;

public class NumberRegex {
    public static class LATITUDE implements Payload {
        public static String pattern = Location.LAT_REGEXP;
    }
    public static class LONGITUDE implements Payload {
        public static String pattern = Location.LON_REGEXP;
    }
}
