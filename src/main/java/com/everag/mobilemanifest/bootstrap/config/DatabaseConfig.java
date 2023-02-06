package com.everag.mobilemanifest.bootstrap.config;

import com.cosium.spring.data.jpa.entity.graph.repository.support.EntityGraphJpaRepositoryFactoryBean;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EntityScan(basePackages = {"com.dairy.mobilemanifest.domain.model", "com.dairy.mobilemanifest.infrastructure.persistence.envers", "com.dairy.mobilemanifest.util"})
//@EnableJpaRepositories(basePackages = "com.dairy.mobilemanifest.domain.repository", repositoryFactoryBeanClass = EnversRevisionRepositoryFactoryBean.class)
@EnableJpaRepositories(repositoryFactoryBeanClass = EntityGraphJpaRepositoryFactoryBean.class,
        basePackages = "com.dairy.mobilemanifest.domain.repository")
@EnableTransactionManagement
public class DatabaseConfig {

}