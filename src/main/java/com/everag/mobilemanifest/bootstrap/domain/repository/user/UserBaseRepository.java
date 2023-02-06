package com.everag.mobilemanifest.bootstrap.domain.repository.user;

import com.cosium.spring.data.jpa.entity.graph.repository.EntityGraphJpaRepository;
import com.everag.mobilemanifest.bootstrap.domain.model.security.SysUser;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.Optional;

@NoRepositoryBean
public interface UserBaseRepository<T extends SysUser>  extends EntityGraphJpaRepository<T, Long> {

    Optional<T> findOneByEmail(String email);

    Optional<T> findOneByUsername(String login);

    Optional<T> findOneById(Long userId);

}
