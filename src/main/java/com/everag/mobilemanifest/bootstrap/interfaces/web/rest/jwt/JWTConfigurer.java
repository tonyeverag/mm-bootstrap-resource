package com.everag.mobilemanifest.bootstrap.interfaces.web.rest.jwt;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class JWTConfigurer  extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {
    private final Logger log = LoggerFactory.getLogger(JWTConfigurer.class);

    public static final String AUTHORIZATION_HEADER = "Authorization";

    private final TokenProvider tokenProvider;

    public JWTConfigurer(TokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        JWTFilter jwtFilter = new JWTFilter(tokenProvider);

        log.debug("Registering JWT filter...");
        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        log.debug("Registering CORS filter...");
//        http.addFilterBefore(corsFilter(), JWTFilter.class);
    }
}
