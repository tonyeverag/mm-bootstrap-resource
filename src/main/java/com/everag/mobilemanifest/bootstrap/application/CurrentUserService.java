package com.everag.mobilemanifest.bootstrap.application;

import com.everag.mobilemanifest.bootstrap.domain.model.security.ProducerUser;
import com.everag.mobilemanifest.bootstrap.interfaces.web.rest.dto.user.DriverUserDTO;

import java.util.Optional;

public interface CurrentUserService {

    Optional<DriverUserDTO> getCurrentDriverDto();

    Optional<ProducerUser> getCurrentProducerUser();

}
