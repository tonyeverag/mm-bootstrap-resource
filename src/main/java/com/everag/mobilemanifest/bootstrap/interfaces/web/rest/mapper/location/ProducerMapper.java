package com.everag.mobilemanifest.bootstrap.interfaces.web.rest.mapper.location;

import com.everag.mobilemanifest.bootstrap.domain.model.location.Producer;
import com.everag.mobilemanifest.bootstrap.interfaces.web.rest.dto.company.BtuDTO;
import com.everag.mobilemanifest.bootstrap.interfaces.web.rest.dto.location.ProducerDTO;
import com.everag.mobilemanifest.bootstrap.interfaces.web.rest.mapper.TimeZoneMapper;
import com.everag.mobilemanifest.bootstrap.interfaces.web.rest.mapper.annotations.Bootstrap;
import com.everag.mobilemanifest.bootstrap.interfaces.web.rest.mapper.annotations.ReferenceOnly;
import com.everag.mobilemanifest.bootstrap.interfaces.web.rest.mapper.annotations.ReferencesOnly;
import com.everag.mobilemanifest.bootstrap.interfaces.web.rest.mapper.company.BtuMapper;
import com.everag.mobilemanifest.bootstrap.interfaces.web.rest.mapper.company.DivisionMapper;
import com.everag.mobilemanifest.bootstrap.interfaces.web.rest.mapper.company.SupplyCompanyMapper;
import com.everag.mobilemanifest.bootstrap.interfaces.web.rest.mapper.equipment.TankMapper;
import org.mapstruct.*;

import java.util.List;
import java.util.Set;

@Mapper(componentModel = "springLazy", collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED,
        uses = {SupplyCompanyMapper.class, TankMapper.class, DivisionMapper.class,
                BtuMapper.class, TimeZoneMapper.class})
public interface ProducerMapper {

    @Bootstrap
    @Mapping(target = "supplyCompany", qualifiedBy =  ReferenceOnly.class)
    @Mapping(target = "tanks", qualifiedBy = Bootstrap.class)
    ProducerDTO producerToProducerDTO(Producer producer);


    @ReferenceOnly
    default ProducerDTO producerToProducerDTOReference(Producer producer) {
        if (producer == null) {
            return null;
        }
        ProducerDTO producerDTO = new ProducerDTO();
        producerDTO.setId(producer.getId());
        producerDTO.setName(producer.getName());
        producerDTO.setProducerCode(producer.getProducerCode());
        producerDTO.setTxLock(producer.getTxLock());
        if (producer.getBtu() != null) {
            BtuDTO btuDTO = new BtuDTO();
            btuDTO.setId(producer.getBtu().getId());
            btuDTO.setName(producer.getBtu().getName());
            btuDTO.setBtuNumber(producer.getBtu().getBtuNumber());
            producerDTO.setBtu(btuDTO);
        }
        return producerDTO;
    }
}
