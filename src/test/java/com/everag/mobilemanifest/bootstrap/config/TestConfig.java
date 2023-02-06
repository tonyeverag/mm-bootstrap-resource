package com.everag.mobilemanifest.bootstrap.config;

import com.github.fridujo.rabbitmq.mock.MockConnectionFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.client.loadbalancer.RestTemplateCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.web.client.RestTemplate;

@Configuration
public class TestConfig {
    @Bean
    @Primary
    public RestTemplateCustomizer customizer() {
        return (restTemplate)-> {};
    }

    @Bean
    @Primary
    public RestTemplate restTemplate(RestTemplateCustomizer customizer) {
        return new RestTemplate();
    }

    @Bean
    @Primary
    public String getKeyFromAuthorizationServer(@Qualifier("restTemplate") RestTemplate keyUriRestTemplate) {
        return "-----BEGIN PUBLIC KEY-----\n" +
                "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAoGVDUk8RbA4ZW8WWu+l2TYpeW5aneyW7cZ2u0MKc+dm+5x3FClcWuWVs4hiMLSBFijQtoVvKRCEpNzkkmUjRw/Re4FZbFhsoatm4ly0MdyM03UDsHYuTKpKmI3+SP6jt77Bjq2O7S6h1J4TxDH6zcKliiAid9QHbNfByACS7SWSuZv3TgALp1JbIy1XTCPsrm+sR5NFduv696iWwGr181T6F8StGEMW4oIBkJGtV1nPXqsXoHgyy0JsRMz06CS4P/r5R/L24vdijDcNI1WNlUtB7ibFoInQYVexxrbFhNXWHXY9Kaw9kG766RTXJkHsUir1d18Xylknbg1KoDe7ffQIDAQAB\n" +
                "-----END PUBLIC KEY-----";
    }

    @Bean
    @Primary
    ConnectionFactory consumerConnectionFactory() {
        return new CachingConnectionFactory(new MockConnectionFactory());
    }


}
