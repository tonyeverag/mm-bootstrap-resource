package com.everag.mobilemanifest.bootstrap.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.RestTemplateCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Configuration
public class AuthorizationServerConfiguration {

    private final DiscoveryClient discoveryClient;

    public AuthorizationServerConfiguration(DiscoveryClient discoveryClient) {
        this.discoveryClient = discoveryClient;
    }

    @Bean
    public String getKeyFromAuthorizationServer(@Qualifier("loadBalancedRestTemplate") RestTemplate keyUriRestTemplate) {
        // Load available UAA servers
        discoveryClient.getServices();
        HttpEntity<Void> request = new HttpEntity<Void>(new HttpHeaders());
        return (String) keyUriRestTemplate
                .exchange("http://uaa/oauth/token_key", HttpMethod.GET, request, Map.class).getBody()
                .get("value");
    }

    @Bean
    public RestTemplate loadBalancedRestTemplate(RestTemplateCustomizer customizer) {
        RestTemplate restTemplate = new RestTemplate();
        customizer.customize(restTemplate);
        return restTemplate;
    }
}
