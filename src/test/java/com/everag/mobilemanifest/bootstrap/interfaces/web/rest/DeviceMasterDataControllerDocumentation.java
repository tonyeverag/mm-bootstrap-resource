package com.everag.mobilemanifest.bootstrap.interfaces.web.rest;

import com.cosium.spring.data.jpa.entity.graph.domain.EntityGraphs;
import com.everag.mobilemanifest.bootstrap.MmBootstrapResourceApplication;
import com.everag.mobilemanifest.bootstrap.config.TestConfig;
import com.everag.mobilemanifest.bootstrap.domain.model.company.*;
import com.everag.mobilemanifest.bootstrap.domain.model.configuration.SamplePurpose;
import com.everag.mobilemanifest.bootstrap.domain.model.configuration.SealLocation;
import com.everag.mobilemanifest.bootstrap.domain.model.configuration.SealRemovalReason;
import com.everag.mobilemanifest.bootstrap.domain.model.enums.*;
import com.everag.mobilemanifest.bootstrap.domain.model.equipment.*;
import com.everag.mobilemanifest.bootstrap.domain.model.location.DropYard;
import com.everag.mobilemanifest.bootstrap.domain.model.location.Plant;
import com.everag.mobilemanifest.bootstrap.domain.model.location.PostalAddress;
import com.everag.mobilemanifest.bootstrap.domain.model.location.Producer;
import com.everag.mobilemanifest.bootstrap.domain.model.security.DriverUser;
import com.everag.mobilemanifest.bootstrap.domain.repository.company.BTURepository;
import com.everag.mobilemanifest.bootstrap.domain.repository.company.CompanyRepository;
import com.everag.mobilemanifest.bootstrap.domain.repository.company.HaulingCompanyRepository;
import com.everag.mobilemanifest.bootstrap.domain.repository.company.SupplyCompanyRepository;
import com.everag.mobilemanifest.bootstrap.domain.repository.configuration.SamplePurposeRepository;
import com.everag.mobilemanifest.bootstrap.domain.repository.configuration.SealLocationRepository;
import com.everag.mobilemanifest.bootstrap.domain.repository.configuration.SealRemovalReasonRepository;
import com.everag.mobilemanifest.bootstrap.domain.repository.equipment.TankerRepository;
import com.everag.mobilemanifest.bootstrap.domain.repository.equipment.TruckRepository;
import com.everag.mobilemanifest.bootstrap.domain.repository.location.DropYardRepository;
import com.everag.mobilemanifest.bootstrap.domain.repository.location.PlantRepository;
import com.everag.mobilemanifest.bootstrap.domain.repository.location.ProducerRepository;
import com.everag.mobilemanifest.bootstrap.domain.repository.user.DriverUserRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.CacheManager;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.IntStream;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes={MmBootstrapResourceApplication.class, TestConfig.class})
@AutoConfigureMockMvc
@AutoConfigureRestDocs(outputDir = "target/generated-snippets")
@Transactional
public class DeviceMasterDataControllerDocumentation {

    @Autowired private BTURepository btuRepository;
    @Autowired private CompanyRepository companyRepository;
    @Autowired private DriverUserRepository driverRepository;
    @Autowired private DropYardRepository dropYardRepository;
    @Autowired private HaulingCompanyRepository haulerRepository;
    @Autowired private PlantRepository plantRepository;
    @Autowired private ProducerRepository producerRepository;
    @Autowired private SamplePurposeRepository samplePurposeRepository;
    @Autowired private SupplyCompanyRepository supplierRepository;
    @Autowired private TankerRepository tankerRepository;
    @Autowired private SealLocationRepository sealLocationRepository;
    @Autowired private SealRemovalReasonRepository sealRemovalReasonRepository;
    @Autowired private TruckRepository truckRepository;

    @Autowired private CacheManager cacheManager;


    @Autowired private MockMvc mockMvc;




    @Before
    public void setUp(){

        Clock systemClock= Clock.systemDefaultZone();
        systemClock.millis();
        DateTime today = new DateTime();
        DateTime yesterday = today.minusDays(1);

    }

    @Test
    public void getDropyardUpdatesAll() throws Exception {
        createDropYard("My Dropyard1");


        this.mockMvc.perform(
                        get("/api/device/dropyard-updates")
                                .with(user("1").roles("DRIVER"))
                                .accept(ApiVersion.V1_0_0)
                                .contentType(ApiVersion.V1_0_0)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("[*].id").isNotEmpty())
                .andExpect(jsonPath("[*].@class").isNotEmpty())
                .andExpect(jsonPath("[*].allowsDrops").isNotEmpty())
                .andExpect(jsonPath("[*].latitude").isNotEmpty())
                .andExpect(jsonPath("[*].longitude").isNotEmpty())
                .andExpect(jsonPath("[*].geofenceRadius").isNotEmpty())
                .andExpect(jsonPath("[*].name").isNotEmpty())
                .andExpect(jsonPath("[*].txLock").isNotEmpty())
                .andExpect(jsonPath("[*].owningCompany").isNotEmpty());
    }

    @Test
    public void getDropyardUpdatesBetween() throws Exception {
        cacheManager.getCache("DROPYARDS").clear();
        LocalDateTime startTime = LocalDateTime.now();
        createDropYard("My Dropyard1");
        LocalDateTime endTime = startTime.plusMinutes(5);


        this.mockMvc.perform(
                        get("/api/device/dropyard-updates")
                                .with(user("1").roles("DRIVER"))
                                .accept(ApiVersion.V1_0_0)
                                .contentType(ApiVersion.V1_0_0)
                                .param("startTime",startTime.toString())
                                .param("endTime", endTime.toString())
                )
                .andExpect(status().isOk())
                .andDo(
                        document("device/{method-name}",
                                preprocessResponse(prettyPrint()),
                                requestParameters(
                                        parameterWithName("startTime")
                                                .description("The last locally saved timestamp received from the server for this request."),
                                        parameterWithName("endTime")
                                                .description("The last locally saved timestamp received from the server for this request.")
                                ),
                                relaxedResponseFields(
                                        fieldWithPath("[].id").description("The drop yard's unique identifier"),
                                        fieldWithPath("[].@class").description("The Location Type"),
                                        fieldWithPath("[].allowsDrops").description("Does this drop yard allow drops?"),
                                        fieldWithPath("[].latitude").description("The drop yard's latitude"),
                                        fieldWithPath("[].longitude").description("The drop yard's longitude"),
                                        fieldWithPath("[].geofenceRadius").description("The radius of the geofence area (meters)"),
                                        fieldWithPath("[].name").description("The drop yard's name"),
                                        fieldWithPath("[].txLock").description("The version of the drop yard data"),
                                        fieldWithPath("[].owningCompany").description("The owner of the plant")
                                )
                        )
                );
    }

    @Test
    public void getDropyardUpdatesAfter() throws Exception {
        LocalDateTime startTime = LocalDateTime.now();
        Thread.sleep(2000);
        createDropYard("My Dropyard1");


        this.mockMvc.perform(
                        get("/api/device/dropyard-updates")
                                .with(user("1").roles("DRIVER"))
                                .accept(ApiVersion.V1_0_0)
                                .contentType(ApiVersion.V1_0_0)
                                .param("startTime",startTime.toString())
                )
                .andExpect(jsonPath("[0].id").isNotEmpty())
                .andExpect(jsonPath("[0].@class").isNotEmpty())
                .andExpect(jsonPath("[0].allowsDrops").isNotEmpty())
                .andExpect(jsonPath("[0].latitude").isNotEmpty())
                .andExpect(jsonPath("[0].longitude").isNotEmpty())
                .andExpect(jsonPath("[0].geofenceRadius").isNotEmpty())
                .andExpect(jsonPath("[0].name").isNotEmpty())
                .andExpect(jsonPath("[0].txLock").isNotEmpty())
                .andExpect(jsonPath("[0].owningCompany").isNotEmpty());

    }

    @Test
    public void getPlantUpdatesBetween() throws Exception {
        cacheManager.getCache("PLANTS").clear();
        LocalDateTime startTime = LocalDateTime.now();
        createPlant("My plant1 Between");
        LocalDateTime endTime = startTime.plusMinutes(5);


        List<HaulingCompany> haulers = new ArrayList<>();
        HaulingCompany hauler = createHauler("Hauler for plant1 between");
        haulers.add(hauler);
        DriverUser driver = createDriver("DrvFirstName", "DrvUserName"+ "plant1between", haulers);

        this.mockMvc.perform(
                        get("/api/device/plant-updates")
                                .with(user(driver.getId().toString()).roles("DRIVER"))
                                .accept(ApiVersion.V1_0_0)
                                .contentType(ApiVersion.V1_0_0)
                                .param("startTime", startTime.toString())
                                .param("endTime", endTime.toString())
                )
                .andExpect(status().isOk())
                .andDo(
                        document("device/{method-name}",
                                preprocessResponse(prettyPrint()),
                                requestParameters(
                                        parameterWithName("startTime")
                                                .description("The last locally saved timestamp received from the server for this request."),
                                        parameterWithName("endTime")
                                                .description("The last locally saved timestamp received from the server for this request.")
                                ),
                                relaxedResponseFields(
                                        fieldWithPath("[].id").description("The plant's unique identifier"),
                                        fieldWithPath("[].@class").description("The Location Type"),
                                        fieldWithPath("[].allowsDrops").description("Does this plant allow drops?"),
                                        fieldWithPath("[].latitude").description("The plant's latitude"),
                                        fieldWithPath("[].longitude").description("The plant's longitude"),
                                        fieldWithPath("[].geofenceRadius").description("The radius of the geofence area (meters)"),
                                        fieldWithPath("[].name").description("The plant's name"),
                                        fieldWithPath("[].txLock").description("The version of the plant data"),
                                        fieldWithPath("[].owningCompany").description("The owner of the plant")
                                )
                        )
                );
    }

    @Test
    public void getPlantUpdatesAll() throws Exception {
        createPlant("My plant1 All");


        List<HaulingCompany> haulers = new ArrayList<>();
        HaulingCompany hauler = createHauler("Hauler" + "plant1 all");
        haulers.add(hauler);
        DriverUser driver = createDriver("DrvFirstName", "DrvUserName"+ "Plant1All", haulers);

        this.mockMvc.perform(
                        get("/api/device/plant-updates")
                                .with(user(driver.getId().toString()).roles("DRIVER"))
                                .accept(ApiVersion.V1_0_0)
                                .contentType(ApiVersion.V1_0_0)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("[*].id").isNotEmpty())
                .andExpect(jsonPath("[*].@class").isNotEmpty())
                .andExpect(jsonPath("[*].allowsDrops").isNotEmpty())
                .andExpect(jsonPath("[*].latitude").isNotEmpty())
                .andExpect(jsonPath("[*].longitude").isNotEmpty())
                .andExpect(jsonPath("[*].geofenceRadius").isNotEmpty())
                .andExpect(jsonPath("[*].name").isNotEmpty())
                .andExpect(jsonPath("[*].txLock").isNotEmpty())
                .andExpect(jsonPath("[*].owningCompany").isNotEmpty());
    }

    @Test
    public void getPlantUpdatesAfter() throws Exception {

        LocalDateTime startTime = LocalDateTime.now();
        Thread.sleep(2000);
        createPlant("My plant1 After");


        List<HaulingCompany> haulers = new ArrayList<>();
        HaulingCompany hauler = createHauler("Hauler" + "Plant1After");
        haulers.add(hauler);
        DriverUser driver = createDriver("DrvFirstName", "DrvUserName"+ "Plant1After", haulers);

        this.mockMvc.perform(
                        get("/api/device/plant-updates")
                                .with(user(driver.getId().toString()).roles("DRIVER"))
                                .accept(ApiVersion.V1_0_0)
                                .contentType(ApiVersion.V1_0_0)
                                .param("startTime", startTime.toString())
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("[*].id").isNotEmpty())
                .andExpect(jsonPath("[*].@class").isNotEmpty())
                .andExpect(jsonPath("[*].allowsDrops").isNotEmpty())
                .andExpect(jsonPath("[*].latitude").isNotEmpty())
                .andExpect(jsonPath("[*].longitude").isNotEmpty())
                .andExpect(jsonPath("[*].geofenceRadius").isNotEmpty())
                .andExpect(jsonPath("[*].name").isNotEmpty())
                .andExpect(jsonPath("[*].txLock").isNotEmpty())
                .andExpect(jsonPath("[*].owningCompany").isNotEmpty());
    }

    @Test
    public void getProducerUpdates() throws Exception {
        LocalDateTime startTime = LocalDateTime.now();
        BTU btu = new BTU("btu-" + RandomStringUtils.randomAlphanumeric(6),
                RandomStringUtils.randomNumeric(2) + "-" + RandomStringUtils.randomNumeric(4));
        btu = btuRepository.save(btu);

        PostalAddress companyAddress = new PostalAddress("3801 Parkwood Blvd.", null, "Frisco", USState.TX, "75034");
        Company company = new Company("supplier-" + RandomStringUtils.randomAlphanumeric(6), companyAddress);
        company = companyRepository.save(company);

        SupplyCompany supplier = new SupplyCompany(company,false);
        Division division = new Division("div-" + RandomStringUtils.randomAlphanumeric(6));
        supplier.addDivision(division);
        supplierRepository.save(supplier);

        PostalAddress producerAddress = new PostalAddress("3801 Parkwood Blvd.", null, "Frisco", USState.TX, "75034");
        Producer producer = new Producer(RandomStringUtils.randomAlphanumeric(6), supplier,
                "producer-" + RandomStringUtils.randomAlphanumeric(6), producerAddress, Boolean.FALSE, new BigDecimal(80),new BigDecimal(90), Status.ACTIVE);
        producer.setBtu(btu);
        producer.setDivision(division);
        producer.setGeofenceRadius(1200000); // meters
        producer.setLatitude(new BigDecimal("89.12345678"));
        producer.setLongitude(new BigDecimal("-172.12345"));
        producer.setTimezone(MMTimeZone.AMERICA_CHICAGO.getCode());

        Tank tank1 = new Tank("1", ReadMethod.STICK, TankWeightUom.POUNDS, StickReadingUom.INCHES);
        Barcode barcode = new Barcode();
        barcode.setCode("DDC" + RandomStringUtils.randomNumeric(10));
        tank1.getBarcodes().add(barcode);
        tank1.setCalibrationRange(new BigDecimal(33.33));
        tank1.setMilkGrade(MilkGrade.GRADE_A);
        tank1.setMilkType(MilkType.CONVENTIONAL);
        tank1.setEnableNoWeightAvailable(Boolean.FALSE);
        IntStream.rangeClosed(1, 32)
                .mapToObj(i -> new CalibrationEntry(1, i, new BigDecimal(20000 + i * 10)))
                .forEach(ce -> tank1.addCalibrationEntry(ce));
        producer.addTank(tank1);
        Tank tank2 = new Tank("2", ReadMethod.STICK, TankWeightUom.POUNDS, StickReadingUom.INCHES);
        tank2.setMilkGrade(MilkGrade.GRADE_A);
        tank2.setMilkType(MilkType.CONVENTIONAL);
        tank2.setEnableNoWeightAvailable(Boolean.FALSE);
        producer.addTank(tank2);
        producerRepository.save(producer);

        LocalDateTime endTime = LocalDateTime.now();

        this.mockMvc.perform(
                        get("/api/device/producer-updates")
                                .with(user("1").roles("DRIVER"))
                                .accept(ApiVersion.V1_0_0)
                                .contentType(ApiVersion.V1_0_0)
                                .param("startTime", startTime.toString())
                                .param("endTime", endTime.toString())
                                .param("latitude", "89.1")
                                .param("longitude", "-172.1")
                )
                .andExpect(status().isOk())
                .andDo(
                        document("device/{method-name}",
                                preprocessResponse(prettyPrint()),
                                requestParameters(
                                        parameterWithName("startTime")
                                                .description("The last locally saved timestamp received from the server for this request."),
                                        parameterWithName("endTime")
                                                .description("The last locally saved timestamp received from the server for this request."),
                                        parameterWithName("latitude")
                                                .description("The device latitude sent with bootstrap request."),
                                        parameterWithName("longitude")
                                                .description("The device longitude sent with bootstrap request.")
                                ),
                                relaxedResponseFields(
                                        fieldWithPath("[].id").description("The producer's unique identifier"),
                                        fieldWithPath("[].@class").description("The Location Type"),
                                        fieldWithPath("[].producerCode").description("The producer's identifier"),
                                        fieldWithPath("[].allowsDrops").description("Does this producer allow drops?"),
                                        fieldWithPath("[].latitude").description("The producer's latitude"),
                                        fieldWithPath("[].longitude").description("The producer's longitude"),
                                        fieldWithPath("[].timezone").description("The producer's timezone"),
                                        fieldWithPath("[].geofenceRadius").description("The radius of the geofence area (meters)"),
                                        fieldWithPath("[].name").description("The producer's name"),
                                        fieldWithPath("[].supplyCompany").description("The producer's associated supplier"),
                                        fieldWithPath("[].division").description("The producer's associated division"),
                                        fieldWithPath("[].tanks").description("The producer's associated tanks"),
                                        fieldWithPath("[].tanks[].barcodes[]").optional().description("The tanks barcode"),
                                        fieldWithPath("[].tanks[].calibrationRange").optional().description("The tank's Calibration Range"),
                                        fieldWithPath("[].txLock").description("The version of the producer data")
                                )
                        )
                );
    }

    @Test
    public void getSamplePurposeUpdatesBetween() throws Exception {
        LocalDateTime startTime = LocalDateTime.now();
        createSamplePurpose("Needs Sampling");
        LocalDateTime endTime = startTime.plusMinutes(5);


        this.mockMvc.perform(
                        get("/api/device/sample-purpose-updates")
                                .with(user("1").roles("DRIVER"))
                                .accept(ApiVersion.V1_0_0)
                                .contentType(ApiVersion.V1_0_0)
                                .param("startTime", startTime.toString())
                                .param("endTime", endTime.toString())
                )
                .andExpect(status().isOk())
                .andDo(
                        document("device/{method-name}",
                                preprocessResponse(prettyPrint()),
                                requestParameters(
                                        parameterWithName("startTime")
                                                .description("The last locally saved timestamp received from the server for this request."),
                                        parameterWithName("endTime")
                                                .description("The last locally saved timestamp received from the server for this request.")
                                ),
                                responseFields(
                                        fieldWithPath("[].id").description("The sample purpose's unique identifier"),
                                        fieldWithPath("[].description").description("The sample purpose description"),
                                        fieldWithPath("[].txLock").description("The version of the sample purpose")
                                )
                        )
                );

    }

    @Test
    public void getSamplePurposeUpdatesAll() throws Exception {
        createSamplePurpose("Needs Sampling All");


        this.mockMvc.perform(
                        get("/api/device/sample-purpose-updates")
                                .with(user("1").roles("DRIVER"))
                                .accept(ApiVersion.V1_0_0)
                                .contentType(ApiVersion.V1_0_0)
                )
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("[0].id").isNotEmpty())
                .andExpect(jsonPath("[0].description").isNotEmpty())
                .andExpect(jsonPath("[0].txLock").isNotEmpty());

    }

    public void getSamplePurposeUpdatesAfter() throws Exception {
        LocalDateTime startTime = LocalDateTime.now();
        Thread.sleep(2000);
        createSamplePurpose("Needs Sampling After");


        this.mockMvc.perform(
                        get("/api/device/sample-purpose-updates")
                                .with(user("1").roles("DRIVER"))
                                .accept(ApiVersion.V1_0_0)
                                .contentType(ApiVersion.V1_0_0)
                                .param("startTime", startTime.toString())
                )
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("[*].id").isNotEmpty())
                .andExpect(jsonPath("[*].description").isNotEmpty())
                .andExpect(jsonPath("[*].txLock").isNotEmpty());
    }

    @Test
    public void getSupplierUpdatesBetween() throws Exception {
        LocalDateTime startTime = LocalDateTime.now();
        createSupplier("My supplier1");
        LocalDateTime endTime = startTime.plusMinutes(5);


        this.mockMvc.perform(
                        get("/api/device/supplier-updates")
                                .with(user("1").roles("DRIVER"))
                                .accept(ApiVersion.V1_0_0)
                                .contentType(ApiVersion.V1_0_0)
                                .param("startTime", startTime.toString())
                                .param("endTime", endTime.toString())
                )
                .andExpect(status().isOk())
                .andDo(
                        document("device/{method-name}",
                                preprocessResponse(prettyPrint()),
                                requestParameters(
                                        parameterWithName("startTime")
                                                .description("The last locally saved timestamp received from the server for this request."),
                                        parameterWithName("endTime")
                                                .description("The last locally saved timestamp received from the server for this request.")
                                ),
                                responseFields(
                                        fieldWithPath("[].id").description("The supplier's unique identifier"),
                                        fieldWithPath("[].@class").ignored(),
                                        fieldWithPath("[].name").description("The supplier's name"),
                                        fieldWithPath("[].enableAutogenManifest").description("The supplier's name"),
                                        fieldWithPath("[].enableSampleValidation").description("Boolean to enable sample validation"),
                                        fieldWithPath("[].maxSampleNumberSize").description("Maximum sample number size"),
                                        fieldWithPath("[].minSampleNumberSize").description("Minimum sample number size"),
                                        fieldWithPath("[].generateBarcode").description("Boolean to generate barcode"),
                                        fieldWithPath("[].barcodeType").description("The type of barcode"),
                                        fieldWithPath("[].enableRequireSamples").description("Flag on Supplier for Requires Samples"),
                                        fieldWithPath("[].requireScaleImageOnPickup").description("Flag on Supplier for Requires Scale Image on Pickup"),
                                        fieldWithPath("[].txLock").description("The version of the supplier data")
                                )
                        )
                );

    }


    @Test
    public void getSupplierUpdatesAfter() throws Exception {
        cacheManager.getCache("SUPPLYCOMPANIES").clear();
        LocalDateTime startTime = LocalDateTime.now();
        Thread.sleep(1000);
        createSupplier("My supplier1After");


        this.mockMvc.perform(
                        get("/api/device/supplier-updates")
                                .with(user("1").roles("DRIVER"))
                                .accept(ApiVersion.V1_0_0)
                                .contentType(ApiVersion.V1_0_0)
                                .param("startTime", startTime.toString())
                )
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("[0].id").isNotEmpty())
                .andExpect(jsonPath("[0].name").isNotEmpty())
                .andExpect(jsonPath("[0].txLock").isNotEmpty());

    }


    @Test
    public void getSupplierUpdatesAll() throws Exception {
        createSupplier("My supplier1All");


        this.mockMvc.perform(
                        get("/api/device/supplier-updates")
                                .with(user("1").roles("DRIVER"))
                                .accept(ApiVersion.V1_0_0)
                                .contentType(ApiVersion.V1_0_0)
                )
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("[0].id").isNotEmpty())
                .andExpect(jsonPath("[0].name").isNotEmpty())
                .andExpect(jsonPath("[0].txLock").isNotEmpty());
    }


    @Test
    public void getTankerUpdatesBetween() throws Exception {
        LocalDateTime startTime = LocalDateTime.now();
        createTanker("My tanker1");
        cacheManager.getCache("TANKERS").clear();

        LocalDateTime endTime = startTime.plusMinutes(5);

        this.mockMvc.perform(
                        get("/api/device/tanker-updates")
                                .with(user("1").roles("DRIVER"))
                                .accept(ApiVersion.V1_0_0)
                                .contentType(ApiVersion.V1_0_0)
                                .param("startTime", startTime.toString())
                                .param("endTime", endTime.toString())
                )
                .andExpect(status().isOk())
                .andDo(
                        document("device/{method-name}",
                                preprocessResponse(prettyPrint()),
                                requestParameters(
                                        parameterWithName("startTime")
                                                .description("The last locally saved timestamp received from the server for this request."),
                                        parameterWithName("endTime")
                                                .description("The last locally saved timestamp received from the server for this request.")
                                ),
                                responseFields(
                                        fieldWithPath("[].id").description("The tanker's unique identifier"),
                                        fieldWithPath("[].tankerId").description("The tanker's identifier"),
                                        fieldWithPath("[].txLock").description("The version of the tanker data"),
                                        fieldWithPath("[].ownedBy.id").description("ID of the company that owns the tanker"),
                                        fieldWithPath("[].ownedBy.name").description("Name of the company that owns the tanker"),
                                        fieldWithPath("[].ownedBy.txLock").description("txlock"),
                                        fieldWithPath("[].barcodes[].id").description("The internal identifier for the barcode"),
                                        fieldWithPath("[].barcodes[].code").description("The serial number associated with the tanker"),
                                        fieldWithPath("[].barcodes[].txLock").description("The version of the barcode")
                                )
                        )
                );
    }


    @Test
    public void getTankerUpdatesAfter() throws Exception {
        LocalDateTime startTime = LocalDateTime.now();
        Thread.sleep(1000);;
        createTanker("My tanker1 After");

        this.mockMvc.perform(
                        get("/api/device/tanker-updates")
                                .with(user("1").roles("DRIVER"))
                                .accept(ApiVersion.V1_0_0)
                                .contentType(ApiVersion.V1_0_0)
                                .param("startTime", startTime.toString())
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("[*].id").isNotEmpty())
                .andExpect(jsonPath("[*].tankerId").isNotEmpty())
                .andExpect(jsonPath("[*].barcodes[*].id").isNotEmpty())
                .andExpect(jsonPath("[*].barcodes[*].txLock").isNotEmpty())
                .andExpect(jsonPath("[*].barcodes[*].code").isNotEmpty());
    }


    @Test
    public void getTankerUpdatesAll() throws Exception {
        createTanker("My tanker1 All");

        this.mockMvc.perform(
                        get("/api/device/tanker-updates")
                                .with(user("1").roles("DRIVER"))
                                .accept(ApiVersion.V1_0_0)
                                .contentType(ApiVersion.V1_0_0)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("[*].id").isNotEmpty())
                .andExpect(jsonPath("[*].tankerId").isNotEmpty())
                .andExpect(jsonPath("[*].barcodes[*].id").isNotEmpty())
                .andExpect(jsonPath("[*].barcodes[*].txLock").isNotEmpty())
                .andExpect(jsonPath("[*].barcodes[*].code").isNotEmpty());
    }

    @Test
    public void getSealLocationUpdates() throws Exception {
        LocalDateTime startTime = LocalDateTime.now();
        createSealLocation("My seal location");
        LocalDateTime endTime = startTime.plusMinutes(5);

        this.mockMvc.perform(
                        get("/api/device/seal-location-updates")
                                .with(user("1").roles("DRIVER"))
                                .accept(ApiVersion.V1_0_0)
                                .contentType(ApiVersion.V1_0_0)
                                .param("startTime", startTime.toString())
                                .param("endTime", endTime.toString())
                )
                .andExpect(status().isOk())
                .andDo(
                        document("device/{method-name}",
                                preprocessResponse(prettyPrint()),
                                requestParameters(
                                        parameterWithName("startTime")
                                                .description("The last locally saved timestamp received from the server for this request."),
                                        parameterWithName("endTime")
                                                .description("The last locally saved timestamp received from the server for this request.")
                                ),
                                responseFields(
                                        fieldWithPath("[].id").description("The seal location's unique identifier"),
                                        fieldWithPath("[].description").description("The seal location's description"),
                                        fieldWithPath("[].txLock").description("The version of the seal location")
                                )
                        )
                );

    }

    @Test
    public void getSealRemovalReasonUpdates() throws Exception {
        LocalDateTime startTime = LocalDateTime.now();
        createSealRemovalReason("Broken seal");
        LocalDateTime endTime = startTime.plusMinutes(5);

        this.mockMvc.perform(
                        get("/api/device/seal-removal-reason-updates")
                                .with(user("1").roles("DRIVER"))
                                .accept(ApiVersion.V1_0_0)
                                .contentType(ApiVersion.V1_0_0)
                                .param("startTime", startTime.toString())
                                .param("endTime", endTime.toString())
                )
                .andExpect(status().isOk())
                .andDo(
                        document("device/{method-name}",
                                preprocessResponse(prettyPrint()),
                                requestParameters(
                                        parameterWithName("startTime")
                                                .description("The last locally saved timestamp received from the server for this request."),
                                        parameterWithName("endTime")
                                                .description("The last locally saved timestamp received from the server for this request.")
                                ),
                                responseFields(
                                        fieldWithPath("[].id").description("The seal removal reason's unique identifier"),
                                        fieldWithPath("[].description").description("The seal removal reason's description"),
                                        fieldWithPath("[].status").description("The Seal Removal Reason status"),
                                        fieldWithPath("[].txLock").description("The version of the seal location")
                                )
                        )
                );

    }

    @Test
    public void getTruckUpdates() throws Exception {
        LocalDateTime startTime = LocalDateTime.now();
        createTruck("My truck1");
        LocalDateTime endTime = startTime.plusMinutes(5);

        this.mockMvc.perform(
                        get("/api/device/truck-updates")
                                .with(user("1").roles("DRIVER"))
                                .accept(ApiVersion.V1_0_0)
                                .contentType(ApiVersion.V1_0_0)
                                .param("startTime", startTime.toString())
                                .param("endTime", endTime.toString())
                )
                .andExpect(status().isOk())
                .andDo(
                        document("device/{method-name}",
                                preprocessResponse(prettyPrint()),
                                requestParameters(
                                        parameterWithName("startTime")
                                                .description("The last locally saved timestamp received from the server for this request."),
                                        parameterWithName("endTime")
                                                .description("The last locally saved timestamp received from the server for this request.")
                                ),
                                responseFields(
                                        fieldWithPath("[].id").description("The truck's unique identifier"),
                                        fieldWithPath("[].truckId").description("The truck's identifier"),
                                        fieldWithPath("[].txLock").description("The version of the truck data"),
                                        fieldWithPath("[].ownedBy.id").description("ID of the company that owns the tanker"),
                                        fieldWithPath("[].ownedBy.name").description("Name of the company that owns the tanker"),
                                        fieldWithPath("[].ownedBy.txLock").description("txlock"),
                                        fieldWithPath("[].barcodes[].id").description("Internal identifier for the barcode"),
                                        fieldWithPath("[].barcodes[].code").description("The serial number for the truck"),
                                        fieldWithPath("[].barcodes[].txLock").description("The version of the barcode")
                                )
                        )
                );
    }

    @Test
    public void getCompanyUpdatesAll() throws Exception {
        createTruck("My truck" + RandomStringUtils.randomAlphanumeric(5));
        createTanker("tanker - " + RandomStringUtils.randomNumeric(5));

        this.mockMvc.perform(
                        get("/api/device/company-updates")
                                .with(user("1").roles("DRIVER"))
                                .accept(ApiVersion.V1_0_0)
                                .contentType(ApiVersion.V1_0_0)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("[0].id").isNotEmpty())
                .andExpect(jsonPath("[0].name").isNotEmpty())
                .andExpect(jsonPath("[1].id").isNotEmpty())
                .andExpect(jsonPath("[1].name").isNotEmpty());

    }

    @Test
    public void getCompanyUpdatesBetween() throws Exception {
        LocalDateTime startTime = LocalDateTime.now();
        createTruck("My truck" + RandomStringUtils.randomAlphanumeric(5));
        createTanker("tanker - " + RandomStringUtils.randomNumeric(5));
        LocalDateTime endTime = startTime.plusMinutes(5);

        this.mockMvc.perform(
                        get("/api/device/company-updates")
                                .with(user("1").roles("DRIVER"))
                                .accept(ApiVersion.V1_0_0)
                                .contentType(ApiVersion.V1_0_0)
                                .param("startTime", startTime.toString())
                                .param("endTime", endTime.toString())
                )
                .andExpect(status().isOk())
                .andDo(
                        document("device/{method-name}",
                                preprocessResponse(prettyPrint()),
                                requestParameters(
                                        parameterWithName("startTime")
                                                .description("The last locally saved timestamp received from the server for this request."),
                                        parameterWithName("endTime")
                                                .description("The last locally saved timestamp received from the server for this request.")
                                ),
                                responseFields(
                                        fieldWithPath("[].id").description("The companies unique identifier"),
                                        fieldWithPath("[].txLock").description("The version of the company data"),
                                        fieldWithPath("[].name").description("The name of the company")
                                )
                        )
                );

    }

    @Test
    public void getCompanyUpdatesAfter() throws Exception {
        cacheManager.getCache("COMPANIES").clear();
        LocalDateTime startTime = LocalDateTime.now();
        createTruck("My truck" + RandomStringUtils.randomAlphanumeric(5));
        createTanker("tanker - " + RandomStringUtils.randomNumeric(5));
        LocalDateTime endTime = startTime.plusMinutes(5);

        this.mockMvc.perform(
                        get("/api/device/company-updates")
                                .with(user("1").roles("DRIVER"))
                                .accept(ApiVersion.V1_0_0)
                                .contentType(ApiVersion.V1_0_0)
                                .param("startTime", startTime.toString())
                                .param("endTime", endTime.toString())
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("[0].id").isNotEmpty())
                .andExpect(jsonPath("[0].name").isNotEmpty())
                .andExpect(jsonPath("[1].id").isNotEmpty())
                .andExpect(jsonPath("[1].name").isNotEmpty());
    }


    private DriverUser createDriver(String name, String userName, List<HaulingCompany> haulers) {
        DriverUser driver = new DriverUser(name, "Driver", userName, name + "@dairy.com", null,true);
        Set<Employment> employments = new HashSet<>();
        haulers.forEach((hauler) -> employments.add(new Employment(driver, hauler)));
        driver.setEmployers(employments);

        driverRepository.save(driver);
        return driver;
    }



    private DropYard createDropYard(String name) {
        PostalAddress companyAddress = new PostalAddress("3801 Parkwood Blvd.", null, "Frisco", USState.TX, "75034");
        Company company = new Company("dy-company-" + RandomStringUtils.randomAlphanumeric(6), companyAddress);
        company = companyRepository.save(company);
        PostalAddress dropYardAddress = new PostalAddress("3801 Parkwood Blvd.", null, "Frisco", USState.TX, "75034");
        DropYard dropYard = new DropYard(company, name, dropYardAddress, new BigDecimal(80), new BigDecimal(90), Status.ACTIVE);
        dropYard.setGeofenceRadius(1000);
        dropYard.setLatitude(new BigDecimal("89.12345678"));
        dropYard.setLongitude(new BigDecimal("-172.12345"));
        dropYard.setTimezone(MMTimeZone.AMERICA_CHICAGO.getCode());

        this.dropYardRepository.save(dropYard);
        return dropYard;
    }

    private HaulingCompany createHauler(String name) {
        PostalAddress companyAddress = new PostalAddress("3801 Parkwood Blvd.", null, "Frisco", USState.TX, "75034");
        Company company = new Company(name, companyAddress);
        companyRepository.save(company);
        HaulingCompany hauler = new HaulingCompany(company,false);
        haulerRepository.save(hauler);
        return hauler;
    }

    private Plant createPlant(String name) {
        PostalAddress companyAddress = new PostalAddress("3801 Parkwood Blvd.", null, "Frisco", USState.TX, "75034");
        Company company = new Company("plant-company-" + RandomStringUtils.randomAlphanumeric(6), companyAddress);
        company = companyRepository.save(company);
        PostalAddress plantAddress = new PostalAddress("3801 Parkwood Blvd.", null, "Frisco", USState.TX, "75034");
        Plant plant = new Plant(name, company, plantAddress, false, new BigDecimal(80),new BigDecimal(90), Status.ACTIVE);
        plant.setGeofenceRadius(1200);
        plant.setLatitude(new BigDecimal("89.12345678"));
        plant.setLongitude(new BigDecimal("-172.12345"));
        plant.setTimezone(MMTimeZone.AMERICA_CHICAGO.getCode());
        plant.setLastUpdatedDateTime(LocalDateTime.now());
        this.plantRepository.save(plant);
        return plant;
    }

    private SamplePurpose createSamplePurpose(String description) {
        SamplePurpose samplePurpose = new SamplePurpose(description);

        samplePurposeRepository.save(samplePurpose);
        return samplePurpose;
    }

    private SupplyCompany createSupplier(String name) {
        PostalAddress companyAddress = new PostalAddress("3801 Parkwood Blvd.", null, "Frisco", USState.TX, "75034");
        Company company = new Company("s-company-" + name, companyAddress);

        companyRepository.save(company);

        SupplyCompany supplier = new SupplyCompany(company,false);
        supplierRepository.save(supplier);
        return supplier;
    }

    private Tanker createTanker(String tankerId) {
        PostalAddress companyAddress = new PostalAddress("3801 Parkwood Blvd.", null, "Frisco", USState.TX, "75034");
        Company company = new Company("tanker-company-" + tankerId, companyAddress);


        Tanker tanker = new Tanker(EntityStatus.CONFIRMED, tankerId, company);
        Barcode barcode = new Barcode();
        barcode.setCode("DDC" + RandomStringUtils.randomNumeric(10));
        tanker.getBarcodes().add(barcode);

        company.getTankers().add(tanker);
        companyRepository.save(company);
        this.tankerRepository.save(tanker);
        return tanker;
    }

    private SealLocation createSealLocation(String description) {
        SealLocation sealLocation = new SealLocation(description);
        this.sealLocationRepository.save(sealLocation);
        return sealLocation;
    }

    private SealRemovalReason createSealRemovalReason(String description) {
        SealRemovalReason sealRemovalReason = new SealRemovalReason(description);
        sealRemovalReason.setStatus(SealRemovalReasonStatus.ACTIVE);
        this.sealRemovalReasonRepository.save(sealRemovalReason);
        return sealRemovalReason;
    }

    private Truck createTruck(String truckId) {
        PostalAddress companyAddress = new PostalAddress("3801 Parkwood Blvd.", null, "Frisco", USState.TX, "75034");
        Company company = new Company("truck-company-" + truckId, companyAddress);

        Truck truck = new Truck(EntityStatus.CONFIRMED, truckId, company);
        Barcode barcode = new Barcode();
        barcode.setCode("DDC" + RandomStringUtils.randomNumeric(10));
        truck.getBarcodes().add(barcode);
        company.getTrucks().add(truck);
        company.setLastUpdatedDateTime(LocalDateTime.now());
        companyRepository.save(company);
        this.truckRepository.save(truck);
        return truck;
    }
}
