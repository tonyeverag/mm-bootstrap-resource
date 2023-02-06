package com.everag.mobilemanifest.bootstrap.config;

import com.everag.mobilemanifest.bootstrap.interfaces.web.rest.jwt.JWTConfigurer;
import com.everag.mobilemanifest.bootstrap.interfaces.web.rest.jwt.TokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import static org.springframework.http.HttpMethod.OPTIONS;

public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final DairyProperties dairyProperties;
    private final DiscoveryClient discoveryClient;

    public SecurityConfig(DairyProperties dairyProperties,
                          DiscoveryClient discoveryClient) {
        this.dairyProperties = dairyProperties;
        this.discoveryClient = discoveryClient;
    }

    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired // TODO replace
    private AuthenticationSuccessHandler customAuthenticationSuccessHandler;

    @Autowired
    private TokenProvider tokenProvider;

    @Autowired
    protected void configureGlobal(AuthenticationManagerBuilder auth, PasswordEncoder passwordEncoder) throws Exception {
        auth
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
                .antMatchers("/scripts/**/*.{js,html}")
                .antMatchers("/bower_components/**")
                .antMatchers("/i18n/**")
                .antMatchers("/assets/**")
//            .antMatchers("/swagger-ui/index.html")
                .antMatchers("/test/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .headers()
                .frameOptions().disable()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers(OPTIONS, "/**").permitAll()
                .antMatchers("/api/register").permitAll()
                .antMatchers("/api/activate").permitAll()
                .antMatchers("/api/authenticate").permitAll()
                .antMatchers("/api/integration/authenticate").permitAll()
                .antMatchers("/api/account/reset_password/init").permitAll()
                .antMatchers("/api/account/reset_password/finish").permitAll()
                .antMatchers("/api/device/**").hasAnyRole("DRIVER", "YARD_FOREMAN", "PRODUCER", "PLANT_RECEIVER", "ASSET_MANAGER")
                .antMatchers("/api/tanks/**").hasAnyRole("DRIVER", "YARD_FOREMAN", "ASSET_MANAGER")
                .antMatchers("/api/meta/**").hasAnyRole("CSR", "SUPER")
                .antMatchers("/api/logs/**").hasRole("SUPER")
                .antMatchers("/api/audits/**").hasRole("SUPER")
                .antMatchers("/api/**").authenticated()
                .antMatchers("/metrics/**").hasRole("SUPER")
                .antMatchers("/health/**").hasRole("SUPER")
                .antMatchers("/trace/**").hasRole("SUPER")
                .antMatchers("/dump/**").hasRole("SUPER")
                .antMatchers("/shutdown/**").hasRole("SUPER")
                .antMatchers("/beans/**").hasRole("SUPER")
                .antMatchers("/configprops/**").hasRole("SUPER")
                .antMatchers("/info/**").hasRole("SUPER")
                .antMatchers("/autoconfig/**").hasRole("SUPER")
                .antMatchers("/env/**").hasRole("SUPER")
                .antMatchers("/mappings/**").hasRole("SUPER")
                .antMatchers("/liquibase/**").hasRole("SUPER")
                .antMatchers("/docs/**").permitAll()
                .antMatchers("/configuration/security").permitAll()
                .antMatchers("/configuration/ui").permitAll()
                .antMatchers("/management/health").permitAll()
                .antMatchers("/management/**").hasRole("SUPER")
                .antMatchers("/protected/**").authenticated()
                .and()
                .apply(securityConfigurerAdapter());

    }

    private JWTConfigurer securityConfigurerAdapter() {
        return new JWTConfigurer(tokenProvider);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean(name = BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManager();
    }
}
