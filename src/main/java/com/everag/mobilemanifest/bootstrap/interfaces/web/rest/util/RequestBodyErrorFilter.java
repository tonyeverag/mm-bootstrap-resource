package com.everag.mobilemanifest.bootstrap.interfaces.web.rest.util;

import com.everag.mobilemanifest.bootstrap.interfaces.web.rest.CommonErrorHandler;
import org.springframework.web.filter.AbstractRequestLoggingFilter;

import javax.servlet.http.HttpServletRequest;

public class RequestBodyErrorFilter extends AbstractRequestLoggingFilter {
    @Override
    protected String createMessage(HttpServletRequest request, String prefix, String suffix) {
        if (shouldLogRequestBody(request)) {
            return super.createMessage(request, prefix, suffix);
        }
        return null;
    }


    @Override
    protected boolean shouldLog(HttpServletRequest request) {
        return (logger.isDebugEnabled() || logger.isTraceEnabled()) && !request.getRequestURI().contains("health");
    }

    protected boolean shouldLogRequestBody(HttpServletRequest request) {
        Object logBody = request.getAttribute(CommonErrorHandler.LOG_REQUEST_BODY_ATTRIBUTE_NAME) ;
        return ((logBody != null && ((Boolean)logBody).booleanValue()) || logger.isTraceEnabled()) && !request.getRequestURI().contains("authenticate");
    }

    /**
     * Writes a log message before the request is processed.
     */
    @Override
    protected void beforeRequest(HttpServletRequest request, String message) {
        if (message != null) {
            logger.debug(message);
        }
    }

    /**
     * Writes a log message after the request is processed.
     */
    @Override
    protected void afterRequest(HttpServletRequest request, String message) {
        if (message != null) {
            logger.debug(message);
        }
    }
}
