package com.everag.mobilemanifest.bootstrap.domain.repository.location;

import com.cosium.spring.data.jpa.entity.graph.domain.EntityGraph;
import com.cosium.spring.data.jpa.entity.graph.repository.EntityGraphJpaRepository;
import com.everag.mobilemanifest.bootstrap.domain.model.enums.Status;
import com.everag.mobilemanifest.bootstrap.domain.model.location.Producer;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProducerRepository extends EntityGraphJpaRepository<Producer, Long>,JpaSpecificationExecutor<Producer> {

        Iterable<Producer> findAllByStatus(EntityGraph entityGraph, Status status);

        @Cacheable(value="PRODUCERS", key="#entityGraph.getEntityGraphAttributePaths() != null ? #entityGraph.getEntityGraphAttributePaths() : #entityGraph.getEntityGraphName()")
        List<Producer> findAll(EntityGraph entityGraph);

}
