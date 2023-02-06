package com.everag.mobilemanifest.bootstrap.config;

import com.everag.mobilemanifest.bootstrap.interfaces.web.rest.util.RequestBodyErrorFilter;
import com.everag.mobilemanifest.bootstrap.interfaces.web.rest.util.RequestLoggingFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.CommonsRequestLoggingFilter;

import javax.servlet.DispatcherType;
import java.util.EnumSet;

@Configuration
public class RequestLoggingConfiguration {
    @Bean
    public FilterRegistrationBean filterRegistrationBean() {
        final FilterRegistrationBean registration = new FilterRegistrationBean();
        final RequestBodyErrorFilter filter = new RequestBodyErrorFilter();

        filter.setIncludeClientInfo(true);
        filter.setIncludePayload(true);
        filter.setIncludeQueryString(true);
        filter.setMaxPayloadLength(20000);
        registration.setFilter(filter);
        registration.setDispatcherTypes(EnumSet.allOf(DispatcherType.class));

        return registration;
    }

    @Bean
    public CommonsRequestLoggingFilter logFilter() {
        CommonsRequestLoggingFilter filter
                = new RequestLoggingFilter();
        filter.setIncludeQueryString(true);
        filter.setIncludePayload(true);
        filter.setMaxPayloadLength(20000);
        filter.setIncludeHeaders(false);
        filter.setAfterMessagePrefix("REQUEST DATA : ");
        return filter;
    }
}
