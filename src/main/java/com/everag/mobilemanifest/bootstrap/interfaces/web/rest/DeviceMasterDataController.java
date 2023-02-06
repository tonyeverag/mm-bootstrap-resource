package com.everag.mobilemanifest.bootstrap.interfaces.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.everag.mobilemanifest.bootstrap.application.*;
import com.everag.mobilemanifest.bootstrap.interfaces.web.rest.dto.company.CompanyDTO;
import com.everag.mobilemanifest.bootstrap.interfaces.web.rest.dto.company.SupplyCompanyDTO;
import com.everag.mobilemanifest.bootstrap.interfaces.web.rest.dto.configuration.SamplePurposeDTO;
import com.everag.mobilemanifest.bootstrap.interfaces.web.rest.dto.configuration.SealLocationDTO;
import com.everag.mobilemanifest.bootstrap.interfaces.web.rest.dto.configuration.SealRemovalReasonDTO;
import com.everag.mobilemanifest.bootstrap.interfaces.web.rest.dto.equipment.TankerDTO;
import com.everag.mobilemanifest.bootstrap.interfaces.web.rest.dto.equipment.TruckDTO;
import com.everag.mobilemanifest.bootstrap.interfaces.web.rest.dto.location.DropYardDTO;
import com.everag.mobilemanifest.bootstrap.interfaces.web.rest.dto.location.PlantDTO;
import com.everag.mobilemanifest.bootstrap.interfaces.web.rest.dto.location.ProducerDTO;
import com.everag.mobilemanifest.bootstrap.interfaces.web.rest.dto.user.DriverUserDTO;
import com.everag.mobilemanifest.bootstrap.interfaces.web.rest.mapper.location.ProducerMapper;
import com.fasterxml.jackson.annotation.JsonView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path="/api/device")
@PreAuthorize("hasAnyRole('DRIVER','YARD_FOREMAN','PLANT_RECEIVER','ASSET_MANAGER','PRODUCER')")
public class DeviceMasterDataController {

    private static final Logger log = LoggerFactory.getLogger(DeviceMasterDataController.class);

    private final DropYardService dropYardService;
    private final PlantService plantService;
    private final ProducerService producerService;
    private final SamplePurposeService samplePurposeService;
    private final SealLocationService sealLocationService;
    private final SealRemovalReasonService sealRemovalReasonService;
    private final SupplyCompanyService supplyCompanyService;
    private final TankerService tankerService;
    private final TruckService truckService;
    private final CompanyService companyService;

    private final CurrentUserService currentUserService;
    private final GeoLocationService geoLocationService;

    private final ProducerMapper producerMapper;

    public DeviceMasterDataController(DropYardService dropYardService, PlantService plantService, ProducerService producerService, SamplePurposeService samplePurposeService, SealLocationService sealLocationService, SealRemovalReasonService sealRemovalReasonService, SupplyCompanyService supplyCompanyService, TankerService tankerService, TruckService truckService, CompanyService companyService, CurrentUserService currentUserService, GeoLocationService geoLocationService, ProducerMapper producerMapper) {
        this.dropYardService = dropYardService;
        this.plantService = plantService;
        this.producerService = producerService;
        this.samplePurposeService = samplePurposeService;
        this.sealLocationService = sealLocationService;
        this.sealRemovalReasonService = sealRemovalReasonService;
        this.supplyCompanyService = supplyCompanyService;
        this.tankerService = tankerService;
        this.truckService = truckService;
        this.companyService = companyService;
        this.currentUserService = currentUserService;
        this.geoLocationService = geoLocationService;
        this.producerMapper = producerMapper;
    }

    @GetMapping(value = "/dropyard-updates", produces = {ApiVersion.DEFAULT, ApiVersion.V1_0_0, ApiVersion.V2_0_0})
    @Timed
    public List<DropYardDTO> getDropyardUpdates(
            @RequestParam(required=false) @DateTimeFormat(iso=ISO.DATE_TIME) LocalDateTime startTime,
            @RequestParam(required=false) @DateTimeFormat(iso=ISO.DATE_TIME) LocalDateTime endTime) {
        log.debug("Requesting Drop Yard Updates between {} and {}", startTime, endTime);
        List<DropYardDTO> dropYards;
        if (startTime == null) {
            dropYards = dropYardService.findAllActive();
        } else if (endTime == null) {
            dropYards = dropYardService.findByLastUpdatedDateTimeAfter(startTime);
        }
        else {
            dropYards = dropYardService.findByLastUpdatedDateTimeBetween(startTime, endTime);
        }
        return dropYards;
    }

    @GetMapping(value = "/plant-updates", produces = {ApiVersion.DEFAULT, ApiVersion.V1_0_0, ApiVersion.V2_0_0})
    @Timed
    public List<PlantDTO> getPlantUpdates(
            @RequestParam(required=false) @DateTimeFormat(iso=ISO.DATE_TIME) LocalDateTime startTime,
            @RequestParam(required=false) @DateTimeFormat(iso=ISO.DATE_TIME) LocalDateTime endTime) {
        log.debug("Requesting Plant Updates between {} and {}", startTime, endTime);

        List<PlantDTO> plants;
        if (startTime == null) {
            plants = plantService.findAllActive();
        } else if (endTime == null) {
            plants = plantService.findByLastUpdatedDateTimeAfter(startTime);
        } else {
            plants = plantService.findByLastUpdatedDateTimeBetween(startTime, endTime);
        }
        return plants;
    }

    @GetMapping(value = "/producer-updates", produces = {ApiVersion.DEFAULT, ApiVersion.V1_0_0, ApiVersion.V2_0_0})
    @Timed
    public List<ProducerDTO> getProducerUpdates(
            @RequestParam(required=false) @DateTimeFormat(iso=ISO.DATE_TIME) LocalDateTime startTime,
            @RequestParam(required=false) @DateTimeFormat(iso=ISO.DATE_TIME) LocalDateTime endTime,
            @RequestParam(required=false) BigDecimal latitude,
            @RequestParam(required=false) BigDecimal longitude) {
        log.debug("Requesting Producer Updates between {} and {}", startTime, endTime);

        var producerUser = currentUserService.getCurrentProducerUser();

        List<ProducerDTO> producers;
        if(producerUser.isPresent()) {
            producers = producerService.findAllByProducerUser();
        } else {
            if (startTime == null) {
                producers = producerService.findAllActive();

            } else if (endTime == null) {
                producers = producerService.findByLastUpdatedDateTimeAfter(startTime);
            } else {
                producers = producerService.findByLastUpdatedDateTimeBetween(startTime, endTime);
            }
        }
        return producers.stream().filter(p -> {
            if (latitude == null || longitude == null) {
                return true;
            } else {
                return geoLocationService.withinGeoFence(p, latitude, longitude);
            }
        }).collect(Collectors.toCollection(LinkedList::new));
    }

    @GetMapping(value = "/sample-purpose-updates", produces = {ApiVersion.DEFAULT, ApiVersion.V1_0_0, ApiVersion.V2_0_0})
    @Timed
    public List<SamplePurposeDTO> getSamplePurposeUpdates(
            @RequestParam(required=false) @DateTimeFormat(iso=ISO.DATE_TIME) LocalDateTime startTime,
            @RequestParam(required=false) @DateTimeFormat(iso=ISO.DATE_TIME) LocalDateTime endTime) {
        log.debug("Requesting Sample Purpose Updates between {} and {}", startTime, endTime);
        List<SamplePurposeDTO> purposes;
        if (startTime == null) {
            purposes= samplePurposeService.findAll();
        } else if (endTime == null) {
            purposes = samplePurposeService.findByLastUpdatedDateTimeAfter(startTime);
        }
        else {
            purposes= samplePurposeService.findByLastUpdatedDateTimeBetween(startTime, endTime);
        }
        return purposes;
    }

    @GetMapping(value = "/supplier-updates", produces = {ApiVersion.DEFAULT, ApiVersion.V1_0_0, ApiVersion.V2_0_0})
    @PreAuthorize("hasAnyRole('DRIVER','YARD_FOREMAN','PRODUCER','PLANT_RECEIVER','ASSET_MANAGER')")
    @Timed
    public List<SupplyCompanyDTO> getSupplierUpdates(
            @RequestParam(required=false) @DateTimeFormat(iso=ISO.DATE_TIME) LocalDateTime startTime,
            @RequestParam(required=false) @DateTimeFormat(iso=ISO.DATE_TIME) LocalDateTime endTime) {
        log.debug("Requesting Supplier Updates between {} and {}", startTime, endTime);

        List<SupplyCompanyDTO> supplyCompanies;

        if (startTime == null) {
            supplyCompanies = supplyCompanyService.findAll();
        } else if (endTime == null) {
            supplyCompanies = supplyCompanyService.findByLastUpdatedDateTimeAfter(startTime);
        }
        else {
            supplyCompanies = supplyCompanyService.findByLastUpdatedDateTimeBetween(startTime, endTime);
        }
        Collections.sort(supplyCompanies, SupplyCompanyDTO.COMPANY_ALPHA_ORDER);
        return supplyCompanies;

    }

    @GetMapping(value = "/tanker-updates", produces = {ApiVersion.DEFAULT, ApiVersion.V1_0_0, ApiVersion.V2_0_0})
    @PreAuthorize("hasAnyRole('DRIVER','YARD_FOREMAN','PRODUCER','PLANT_RECEIVER','ASSET_MANAGER')")
    @Timed
    public List<TankerDTO> getTankerUpdates(
            @RequestParam(required=false) @DateTimeFormat(iso=ISO.DATE_TIME) LocalDateTime startTime,
            @RequestParam(required=false) @DateTimeFormat(iso=ISO.DATE_TIME) LocalDateTime endTime) {
        log.debug("Requesting Tanker Updates between {} and {}", startTime, endTime);
        List<TankerDTO> tankers= null;
        if (startTime == null) {
            tankers= tankerService.findAll();
        } else if (endTime == null) {
            tankers = tankerService.findByLastUpdatedDateTimeAfter(startTime);
        }
        else {
            tankers= tankerService.findByLastUpdatedDateTimeBetween(startTime, endTime);
        }
        return tankers;

    }

    @GetMapping(value = "/seal-location-updates", produces = {ApiVersion.DEFAULT, ApiVersion.V1_0_0, ApiVersion.V2_0_0})
    @Timed
    public List<SealLocationDTO> getSealLocationUpdates(
            @RequestParam(required=false) @DateTimeFormat(iso=ISO.DATE_TIME) LocalDateTime startTime,
            @RequestParam(required=false) @DateTimeFormat(iso=ISO.DATE_TIME) LocalDateTime endTime) {
        log.debug("Requesting Seal Location Updates between {} and {}", startTime, endTime);
        List<SealLocationDTO> sealLocations;
        if (startTime == null) {
            sealLocations = sealLocationService.findAll();
        } else if (endTime == null) {
            sealLocations = sealLocationService.findByLastUpdatedDateTimeAfter(startTime);
        }
        else {
            sealLocations = sealLocationService.findByLastUpdatedDateTimeBetween(startTime, endTime);
        }
        return sealLocations;
    }

    @GetMapping(value = "/seal-removal-reason-updates", produces = {ApiVersion.DEFAULT, ApiVersion.V1_0_0, ApiVersion.V2_0_0})
    @Timed
    public List<SealRemovalReasonDTO> getSealRemovalReasonUpdates(
            @RequestParam(required=false) @DateTimeFormat(iso=ISO.DATE_TIME) LocalDateTime startTime,
            @RequestParam(required=false) @DateTimeFormat(iso=ISO.DATE_TIME) LocalDateTime endTime) {
        log.debug("Requesting Seal Removal Reason Updates between {} and {}", startTime, endTime);
        List<SealRemovalReasonDTO> sealRemovalReasons;
        if (startTime == null) {
            sealRemovalReasons = sealRemovalReasonService.findAllActive();
        } else if (endTime == null) {
            sealRemovalReasons = sealRemovalReasonService.findActiveByLastUpdatedDateTimeAfter(startTime);
        }
        else {
            sealRemovalReasons = sealRemovalReasonService.findActiveByLastUpdatedDateTimeBetween(startTime, endTime);
        }
        return sealRemovalReasons;
    }

    @GetMapping(value = "/truck-updates", produces = {ApiVersion.DEFAULT, ApiVersion.V1_0_0, ApiVersion.V2_0_0})
    @Timed
    public List<TruckDTO> getTruckUpdates(
            @RequestParam(required=false) @DateTimeFormat(iso=ISO.DATE_TIME) LocalDateTime startTime,
            @RequestParam(required=false) @DateTimeFormat(iso=ISO.DATE_TIME) LocalDateTime endTime) {
        log.debug("Requesting Truck Updates between {} and {}", startTime, endTime);

        List<TruckDTO> trucks;
        if (startTime == null) {
            trucks = truckService.findAll();
        } else if (endTime == null) {
            trucks = truckService.findByLastUpdatedDateTimeAfter(startTime);
        }
        else {
            trucks = truckService.findByLastUpdatedDateTimeBetween(startTime, endTime);
        }
        return trucks;
    }

    @GetMapping(value = "/company-updates", produces = {ApiVersion.DEFAULT, ApiVersion.V1_0_0, ApiVersion.V2_0_0})
    @Timed
    public List<CompanyDTO> getCompanies(
            @RequestParam(required=false) @DateTimeFormat(iso=ISO.DATE_TIME) LocalDateTime startTime,
            @RequestParam(required=false) @DateTimeFormat(iso=ISO.DATE_TIME) LocalDateTime endTime) {
        log.debug("Requesting Company Updates between {} and {}", startTime, endTime);
        List<CompanyDTO> companies;
        if (startTime == null) {
            companies = companyService.findAllCompaniesBootstrap();
        }
        else if (endTime == null) {
            companies = companyService.findByLastUpdatedDateTimeAfter(startTime);
        }
        else {
            companies = companyService.findByLastUpdatedDateTimeBetween(startTime, endTime);
        }
        return companies;
    }

    @GetMapping(value = "/supplier-metadata-updates", produces = {ApiVersion.DEFAULT, ApiVersion.V1_0_0, ApiVersion.V2_0_0})
    @Timed
    public List<SupplyCompanyDTO> getSupplierMetadata(
            @RequestParam(required=false) @DateTimeFormat(iso=ISO.DATE_TIME) LocalDateTime startTime,
            @RequestParam(required=false) @DateTimeFormat(iso=ISO.DATE_TIME) LocalDateTime endTime) {

        return supplyCompanyService.findWithUIConfigurationsByIdNotNull();
    }

    //*****************************************************************************
    //*****************************************************************************
    //*****************************************************************************
    //*****************************V3_1_0 STARTS HERE******************************
    //*****************************************************************************
    //*****************************************************************************
    //*****************************************************************************

    @GetMapping(value = "/dropyard-updates", produces = {ApiVersion.V3_1_0})
    @Timed
    public List<DropYardDTO> getDropyardUpdatesV3_1(
            @RequestParam(required=false) @DateTimeFormat(iso=ISO.DATE_TIME) LocalDateTime startTime,
            @RequestParam(required=false) @DateTimeFormat(iso=ISO.DATE_TIME) LocalDateTime endTime) {
        log.debug("Requesting Drop Yard Updates between {} and {}", startTime, endTime);
        List<DropYardDTO> dropYards;
        if (startTime == null) {
            dropYards = dropYardService.findAllActive();
        } else if (endTime == null) {
            dropYards = dropYardService.findByLastUpdatedDateTimeAfter(startTime);
        }
        else {
            dropYards = dropYardService.findByLastUpdatedDateTimeBetween(startTime, endTime);
        }
        return dropYards;
    }

    @GetMapping(value = "/plant-updates", produces = {ApiVersion.V3_1_0})
    @Timed
    public List<PlantDTO> getPlantUpdatesV3_1(
            @RequestParam(required=false) @DateTimeFormat(iso=ISO.DATE_TIME) LocalDateTime startTime,
            @RequestParam(required=false) @DateTimeFormat(iso=ISO.DATE_TIME) LocalDateTime endTime) {
        log.debug("Requesting Plant Updates between {} and {}", startTime, endTime);
        Optional<DriverUserDTO> driver = currentUserService.getCurrentDriverDto();
        if (driver.isEmpty()) {
            return new ArrayList<>();
        }

        List<PlantDTO> plants= null;
        if (startTime == null) {
            plants = plantService.findAllActive();
        } else if (endTime == null) {
            plants = plantService.findByLastUpdatedDateTimeAfter(startTime);
        }
        else {
            plants = plantService.findByLastUpdatedDateTimeBetween(startTime, endTime);
        }
        return plants;
    }

    @GetMapping(value = "/producer-updates", produces = {ApiVersion.V3_1_0})
    @Timed
    public List<ProducerDTO> getProducerUpdatesV3_1(
            @RequestParam(required=false) @DateTimeFormat(iso=ISO.DATE_TIME) LocalDateTime startTime,
            @RequestParam(required=false) @DateTimeFormat(iso=ISO.DATE_TIME) LocalDateTime endTime,
            @RequestParam(required=false) BigDecimal latitude,
            @RequestParam(required=false) BigDecimal longitude) {
        log.debug("Requesting Producer Updates between {} and {}", startTime, endTime);

        List<ProducerDTO> producers;
        if (startTime == null) {
            producers = producerService.findAllActive();

        } else if (endTime == null) {
            producers = producerService.findByLastUpdatedDateTimeAfter(startTime);
        }
        else {
            producers = producerService.findByLastUpdatedDateTimeBetween(startTime, endTime);
        }

        return producers.stream().filter(p -> {
            if (latitude == null || longitude == null) {
                return true;
            } else {
                return geoLocationService.withinGeoFence(p, latitude, longitude);
            }
        }).collect(Collectors.toCollection(LinkedList::new));

    }

    @GetMapping(value = "/sample-purpose-updates", produces = {ApiVersion.V3_1_0})
    @Timed
    public List<SamplePurposeDTO> getSamplePurposeUpdatesV3_1(
            @RequestParam(required=false) @DateTimeFormat(iso=ISO.DATE_TIME) LocalDateTime startTime,
            @RequestParam(required=false) @DateTimeFormat(iso=ISO.DATE_TIME) LocalDateTime endTime) {
        log.debug("Requesting Sample Purpose Updates between {} and {}", startTime, endTime);
        List<SamplePurposeDTO> purposes;
        if (startTime == null) {
            purposes = samplePurposeService.findAll();
        } else if (endTime == null) {
            purposes = samplePurposeService.findByLastUpdatedDateTimeAfter(startTime);
        }
        else {
            purposes = samplePurposeService.findByLastUpdatedDateTimeBetween(startTime, endTime);
        }
        return purposes;


    }

    @GetMapping(value = "/supplier-updates", produces = {ApiVersion.V3_1_0})
    @PreAuthorize("hasAnyRole('DRIVER','YARD_FOREMAN','PRODUCER','PLANT_RECEIVER','ASSET_MANAGER')")
    @Timed
    public List<SupplyCompanyDTO> getSupplierUpdatesV3_1(
            @RequestParam(required=false) @DateTimeFormat(iso=ISO.DATE_TIME) LocalDateTime startTime,
            @RequestParam(required=false) @DateTimeFormat(iso=ISO.DATE_TIME) LocalDateTime endTime) {
        log.debug("Requesting Supplier Updates between {} and {}", startTime, endTime);

        List<SupplyCompanyDTO> supplyCompanies= null;

        if (startTime == null) {
            supplyCompanies= supplyCompanyService.findAll();
        } else if (endTime == null) {
            supplyCompanies = supplyCompanyService.findByLastUpdatedDateTimeAfter(startTime);
        }
        else {
            supplyCompanies = supplyCompanyService.findByLastUpdatedDateTimeBetween(startTime, endTime);
        }
        Collections.sort(supplyCompanies, SupplyCompanyDTO.COMPANY_ALPHA_ORDER);
        return supplyCompanies;
    }

    @GetMapping(value = "/tanker-updates", produces = {ApiVersion.V3_1_0})
    @PreAuthorize("hasAnyRole('DRIVER','YARD_FOREMAN','PRODUCER','PLANT_RECEIVER','ASSET_MANAGER')")
    @Timed
    public List<TankerDTO> getTankerUpdatesV3_1(
            @RequestParam(required=false) @DateTimeFormat(iso=ISO.DATE_TIME) LocalDateTime startTime,
            @RequestParam(required=false) @DateTimeFormat(iso=ISO.DATE_TIME) LocalDateTime endTime) {
        log.debug("Requesting Tanker Updates between {} and {}", startTime, endTime);
        List<TankerDTO> tankers= null;
        if (startTime == null) {
            tankers = tankerService.findAll();
        } else if (endTime == null) {
            tankers = tankerService.findByLastUpdatedDateTimeAfter(startTime);
        }
        else {
            tankers= tankerService.findByLastUpdatedDateTimeBetween(startTime, endTime);
        }
        return tankers;
    }

    @GetMapping(value = "/seal-location-updates", produces = {ApiVersion.V3_1_0})
    @Timed
    public List<SealLocationDTO> getSealLocationUpdatesV3_1(
            @RequestParam(required=false) @DateTimeFormat(iso=ISO.DATE_TIME) LocalDateTime startTime,
            @RequestParam(required=false) @DateTimeFormat(iso=ISO.DATE_TIME) LocalDateTime endTime) {
        log.debug("Requesting Seal Location Updates between {} and {}", startTime, endTime);
        List<SealLocationDTO> sealLocations;
        if (startTime == null) {
            sealLocations= sealLocationService.findAll();
        } else if (endTime == null) {
            sealLocations = sealLocationService.findByLastUpdatedDateTimeAfter(startTime);
        }
        else {
            sealLocations= sealLocationService.findByLastUpdatedDateTimeBetween(startTime, endTime);
        }
        return sealLocations;
    }

    @GetMapping(value = "/seal-removal-reason-updates", produces = {ApiVersion.V3_1_0})
    @Timed
    public List<SealRemovalReasonDTO> getSealRemovalReasonUpdatesV3_1(
            @RequestParam(required=false) @DateTimeFormat(iso=ISO.DATE_TIME) LocalDateTime startTime,
            @RequestParam(required=false) @DateTimeFormat(iso=ISO.DATE_TIME) LocalDateTime endTime) {
        log.debug("Requesting Seal Removal Reason Updates between {} and {}", startTime, endTime);
        List<SealRemovalReasonDTO> sealRemovalReasons;
        if (startTime == null) {
            sealRemovalReasons = sealRemovalReasonService.findAllActive();
        } else if (endTime == null) {
            sealRemovalReasons = sealRemovalReasonService.findActiveByLastUpdatedDateTimeAfter(startTime);
        }
        else {
            sealRemovalReasons = sealRemovalReasonService.findActiveByLastUpdatedDateTimeBetween(startTime, endTime);
        }
        return sealRemovalReasons;
    }

    @GetMapping(value = "/truck-updates", produces = {ApiVersion.V3_1_0})
    @Timed
    public List<TruckDTO> getTruckUpdatesV3_1(
            @RequestParam(required=false) @DateTimeFormat(iso=ISO.DATE_TIME) LocalDateTime startTime,
            @RequestParam(required=false) @DateTimeFormat(iso=ISO.DATE_TIME) LocalDateTime endTime) {
        log.debug("Requesting Truck Updates between {} and {}", startTime, endTime);

        List<TruckDTO> trucks= null;
        if (startTime == null) {
            trucks = truckService.findAll();
        } else if (endTime == null) {
            trucks = truckService.findByLastUpdatedDateTimeAfter(startTime);
        }
        else {
            trucks = truckService.findByLastUpdatedDateTimeBetween(startTime, endTime);
        }
        return trucks;

    }

    @GetMapping(value = "/company-updates", produces = {ApiVersion.V3_1_0})
    @Timed
    public List<CompanyDTO> getCompaniesV3_1(
            @RequestParam(required=false) @DateTimeFormat(iso=ISO.DATE_TIME) LocalDateTime startTime,
            @RequestParam(required=false) @DateTimeFormat(iso=ISO.DATE_TIME) LocalDateTime endTime) {
        log.debug("Requesting Company Updates between {} and {}", startTime, endTime);
        List<CompanyDTO> companies;
        if (startTime == null) {
            companies = companyService.findAllCompaniesBootstrap();
        }
        else if (endTime == null) {
            companies = companyService.findByLastUpdatedDateTimeAfter(startTime);
        }
        else {
            companies = companyService.findByLastUpdatedDateTimeBetween(startTime, endTime);
        }
        return companies;
    }

    @GetMapping(value = "/supplier-metadata-updates", produces = {ApiVersion.V3_1_0})
    @Timed
    public List<SupplyCompanyDTO> getSupplierMetadataV3_1(
            @RequestParam(required=false) @DateTimeFormat(iso=ISO.DATE_TIME) LocalDateTime startTime,
            @RequestParam(required=false) @DateTimeFormat(iso=ISO.DATE_TIME) LocalDateTime endTime) {

        return supplyCompanyService.findWithUIConfigurationsByIdNotNull();
    }

}
