package com.everag.mobilemanifest.bootstrap.domain.repository.company;

import com.cosium.spring.data.jpa.entity.graph.domain.EntityGraph;
import com.cosium.spring.data.jpa.entity.graph.repository.EntityGraphJpaRepository;
import com.everag.mobilemanifest.bootstrap.domain.model.company.Company;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface CompanyRepository extends EntityGraphJpaRepository<Company, Long>, JpaSpecificationExecutor<Company> {

    @Cacheable(value="COMPANIES",  key="#entityGraph.getEntityGraphAttributePaths() != null ? #entityGraph.getEntityGraphAttributePaths() : #entityGraph.getEntityGraphName()")
    List<Company> findAll(EntityGraph entityGraph);

}
