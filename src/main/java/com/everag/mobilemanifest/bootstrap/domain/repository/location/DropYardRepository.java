package com.everag.mobilemanifest.bootstrap.domain.repository.location;

import com.cosium.spring.data.jpa.entity.graph.domain.EntityGraph;
import com.cosium.spring.data.jpa.entity.graph.repository.EntityGraphJpaRepository;
import com.everag.mobilemanifest.bootstrap.domain.model.enums.Status;
import com.everag.mobilemanifest.bootstrap.domain.model.location.DropYard;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface DropYardRepository extends EntityGraphJpaRepository<DropYard, Long>, QuerydslPredicateExecutor<DropYard>, JpaSpecificationExecutor<DropYard> {

    @Cacheable(value="DROPYARDS", key="#entityGraph.getEntityGraphAttributePaths() != null ? #entityGraph.getEntityGraphAttributePaths() : #entityGraph.getEntityGraphName()")
    List<DropYard> findAll(EntityGraph entityGraph);

    List<DropYard> findByNameIgnoreCaseLike(String name);

    List<DropYard> findByOwningCompanyId(Long code);

    List<DropYard> findByAllowsDrops(boolean allowDrops);

    @Query("select distinct dy from DropYard dy join fetch dy.address join fetch dy.owningCompany where dy.lastUpdatedDateTime between :startDate and :endDate")
    List<DropYard> findByLastUpdatedDateTimeBetween(
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate
    );

    @Query("select distinct dy from DropYard dy join fetch dy.address join fetch dy.owningCompany where dy.lastUpdatedDateTime > :changeDate")
    List<DropYard> findByLastUpdatedDateTimeAfter(@Param("changeDate") LocalDateTime changeDate);

    Optional<DropYard> findByName(String name);

    Iterable<DropYard> findAllByStatus(EntityGraph entityGraph, Status status);


}