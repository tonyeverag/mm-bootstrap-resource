package com.everag.mobilemanifest.bootstrap.interfaces.web.rest.mapper.company;

import com.everag.mobilemanifest.bootstrap.domain.model.company.Company;
import com.everag.mobilemanifest.bootstrap.domain.model.equipment.Tanker;
import com.everag.mobilemanifest.bootstrap.domain.model.equipment.Truck;
import com.everag.mobilemanifest.bootstrap.interfaces.web.rest.dto.company.CompanyDTO;
import com.everag.mobilemanifest.bootstrap.interfaces.web.rest.dto.equipment.TankerDTO;
import com.everag.mobilemanifest.bootstrap.interfaces.web.rest.dto.equipment.TruckDTO;
import com.everag.mobilemanifest.bootstrap.interfaces.web.rest.mapper.annotations.ReferenceOnly;
import com.everag.mobilemanifest.bootstrap.interfaces.web.rest.mapper.annotations.ReferencesOnly;
import com.everag.mobilemanifest.bootstrap.interfaces.web.rest.mapper.equipment.TankerMapper;
import com.everag.mobilemanifest.bootstrap.interfaces.web.rest.mapper.equipment.TruckMapper;
import org.mapstruct.DecoratedWith;
import org.mapstruct.InheritConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Mapper(componentModel = "springLazy", uses = {TankerMapper.class, TruckMapper.class})
public interface CompanyMapper {


    CompanyDTO companyToCompanyDTO(Company company);

    @ReferencesOnly
    @Mapping(target = "companyNumber", ignore = true)
    CompanyDTO companyToCompanyDTORef(Company company);

    @ReferenceOnly
    default CompanyDTO companyToCompanyDTOReference(Company company) {
        if (company == null) {
            return null;
        }
        CompanyDTO companyDTO = new CompanyDTO();
        companyDTO.setId(company.getId());
        companyDTO.setName(company.getName());
        companyDTO.setTxLock(company.getTxLock());
        return companyDTO;
    }


    @ReferenceOnly
    default Set<TruckDTO> truckSetToTruckDTOSetReference(Set<Truck> set) {
        if ( set == null ) {
            return null;
        }

        Set<TruckDTO> set_ = new HashSet<TruckDTO>();
        for ( Truck truck : set ) {
            TruckDTO truckDTO = new TruckDTO();
            truckDTO.setId(truck.getId());
            truckDTO.setTxLock(truck.getTxLock());
            truckDTO.setTruckId(truck.getTruckId());
            set_.add( truckDTO );
        }

        return set_;
    }

    @ReferenceOnly
    default Set<TankerDTO> tankerSetToTankerDTOSetReference(Set<Tanker> set) {
        if ( set == null ) {
            return null;
        }

        Set<TankerDTO> set_ = new HashSet<TankerDTO>();
        for ( Tanker tanker : set ) {
            TankerDTO tankerDTO = new TankerDTO();
            tankerDTO.setId(tanker.getId());
            tankerDTO.setTxLock(tanker.getTxLock());
            tankerDTO.setTankerId(tanker.getTankerId());
            set_.add( tankerDTO );
        }

        return set_;
    }
}
