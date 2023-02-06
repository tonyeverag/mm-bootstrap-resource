package com.everag.mobilemanifest.bootstrap.domain.repository.company;

import com.cosium.spring.data.jpa.entity.graph.domain.EntityGraph;
import com.cosium.spring.data.jpa.entity.graph.repository.EntityGraphJpaRepository;
import com.everag.mobilemanifest.bootstrap.domain.model.company.SupplyCompany;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Repository
public interface SupplyCompanyRepository extends EntityGraphJpaRepository<SupplyCompany, Long>, JpaSpecificationExecutor<SupplyCompany> {

    @Cacheable(value="SUPPLYCOMPANIES", key="#entityGraph.getEntityGraphAttributePaths() != null ? #entityGraph.getEntityGraphAttributePaths() : #entityGraph.getEntityGraphName()")
    List<SupplyCompany> findAll(EntityGraph entityGraph);

    Set<SupplyCompany> findByIdNotNull(EntityGraph entityGraph);

    @Query("select distinct sc from SupplyCompany sc where lastUpdatedDateTime between :startDate and :endDate")
    List<SupplyCompany> findByLastUpdatedDateTimeBetween(
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate,
            EntityGraph entityGraph
    );

    @Query("select distinct sc from SupplyCompany sc where lastUpdatedDateTime > :changeDate ")
    List<SupplyCompany> findByLastUpdatedDateTimeAfter(@Param("changeDate") LocalDateTime changeDate, EntityGraph entityGraph);

}
