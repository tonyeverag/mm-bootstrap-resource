package com.everag.mobilemanifest.bootstrap.domain.repository.user;

import com.cosium.spring.data.jpa.entity.graph.domain.EntityGraph;
import com.cosium.spring.data.jpa.entity.graph.repository.EntityGraphJpaRepository;
import com.everag.mobilemanifest.bootstrap.domain.model.location.Producer;
import com.everag.mobilemanifest.bootstrap.domain.model.security.ProducerUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface ProducerUserRepository  extends EntityGraphJpaRepository<ProducerUser, Long> {
    List<ProducerUser> findAll(EntityGraph entityGraph);

    @Query("select pu from ProducerUser pu where id = ?#{new Long(principal.username)}")
    Optional<ProducerUser> findCurrentProducerUser();

}
