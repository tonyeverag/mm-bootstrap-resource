package com.everag.mobilemanifest.bootstrap.interfaces.web.rest.mapper.configuration;

import com.everag.mobilemanifest.bootstrap.domain.model.configuration.SamplePurpose;
import com.everag.mobilemanifest.bootstrap.interfaces.web.rest.dto.configuration.SamplePurposeDTO;

import org.mapstruct.Mapper;


@Mapper(componentModel = "springLazy")
public interface SamplePurposeMapper {

    SamplePurposeDTO samplePurposeToSamplePurposeDTO(SamplePurpose samplePurpose);

}
