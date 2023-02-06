package com.everag.mobilemanifest.bootstrap.application.impl;

import com.everag.mobilemanifest.bootstrap.application.CurrentUserService;
import com.everag.mobilemanifest.bootstrap.domain.model.security.DriverUser;
import com.everag.mobilemanifest.bootstrap.domain.model.security.ProducerUser;
import com.everag.mobilemanifest.bootstrap.domain.model.security.SysUser;
import com.everag.mobilemanifest.bootstrap.domain.repository.user.DriverUserRepository;
import com.everag.mobilemanifest.bootstrap.domain.repository.user.ProducerUserRepository;
import com.everag.mobilemanifest.bootstrap.interfaces.web.rest.dto.user.DriverUserDTO;
import com.everag.mobilemanifest.bootstrap.interfaces.web.rest.mapper.user.DriverUserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.util.Optional;

@Service("currentUserService")
@Scope(value= WebApplicationContext.SCOPE_REQUEST, proxyMode= ScopedProxyMode.INTERFACES)
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class CurrentUserServiceJpa implements CurrentUserService {

    private final DriverUserRepository driverUserRepository;
    private final ProducerUserRepository producerUserRepository;
    private final DriverUserMapper driverUserMapper;
    private SysUser currentUser;

    @Override
    public Optional<DriverUserDTO> getCurrentDriverDto() {

        return getCurrentDriver().map(u -> driverUserMapper.driverUserToDriverUserDTO(u));
    }

    @Override
    public Optional<ProducerUser> getCurrentProducerUser() {
        if (currentUser instanceof ProducerUser) {
            return Optional.of((ProducerUser) currentUser);
        }

        Optional<ProducerUser> u = producerUserRepository.findCurrentProducerUser();
        if (u.isPresent()) {
            currentUser = u.get();
        }
        return u;
    }


    private Optional<DriverUser> getCurrentDriver() {
        if (currentUser instanceof DriverUser) {
            return Optional.of((DriverUser) currentUser);
        }

        Optional<DriverUser> u = driverUserRepository.findCurrentDriver();
        u.ifPresent(driverUser -> currentUser = driverUser);
        return u;
    }
}
