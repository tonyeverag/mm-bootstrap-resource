package com.everag.mobilemanifest.bootstrap.interfaces.web.rest.util;

import org.springframework.web.filter.CommonsRequestLoggingFilter;

import javax.servlet.http.HttpServletRequest;

public class RequestLoggingFilter extends CommonsRequestLoggingFilter {
    @Override
    protected boolean shouldLog(HttpServletRequest request) {

        return this.logger.isDebugEnabled() && !request.getRequestURI().contains("authenticate") && !request.getRequestURI().contains("health");
    }
}
