package com.everag.mobilemanifest.bootstrap.domain.repository.company;

import com.cosium.spring.data.jpa.entity.graph.repository.EntityGraphJpaRepository;
import com.everag.mobilemanifest.bootstrap.domain.model.company.HaulingCompany;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface HaulingCompanyRepository  extends EntityGraphJpaRepository<HaulingCompany, Long>, JpaSpecificationExecutor<HaulingCompany> {
}
