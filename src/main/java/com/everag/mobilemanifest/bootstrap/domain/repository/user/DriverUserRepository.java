package com.everag.mobilemanifest.bootstrap.domain.repository.user;

import com.everag.mobilemanifest.bootstrap.domain.model.security.DriverUser;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface DriverUserRepository  extends UserBaseRepository<DriverUser>, JpaSpecificationExecutor<DriverUser> {
    @Query("select d from DriverUser d where id = ?#{new Long(principal.username)}")
    Optional<DriverUser> findCurrentDriver();
}
