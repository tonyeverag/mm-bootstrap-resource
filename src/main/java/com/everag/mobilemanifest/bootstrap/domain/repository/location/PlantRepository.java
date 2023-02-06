package com.everag.mobilemanifest.bootstrap.domain.repository.location;

import com.cosium.spring.data.jpa.entity.graph.domain.EntityGraph;
import com.cosium.spring.data.jpa.entity.graph.repository.EntityGraphJpaRepository;
import com.everag.mobilemanifest.bootstrap.domain.model.enums.Status;
import com.everag.mobilemanifest.bootstrap.domain.model.location.Plant;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.List;

public interface PlantRepository extends EntityGraphJpaRepository<Plant, Long>, QuerydslPredicateExecutor<Plant>, JpaSpecificationExecutor<Plant> {

    @Cacheable(value="PLANTS", key="#entityGraph.getEntityGraphAttributePaths() != null ? #entityGraph.getEntityGraphAttributePaths() : #entityGraph.getEntityGraphName()")
    List<Plant> findAll(EntityGraph entityGraph);

    Iterable<Plant> findAllByStatus(EntityGraph entityGraph, Status status);

}
