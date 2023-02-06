package com.everag.mobilemanifest.bootstrap.domain.model.equipment;

import com.everag.mobilemanifest.bootstrap.domain.model.enums.*;
import com.everag.mobilemanifest.bootstrap.domain.model.location.Location;
import com.everag.mobilemanifest.bootstrap.domain.model.location.Producer;
import com.everag.mobilemanifest.bootstrap.interfaces.web.rest.ValidationGroup;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table( uniqueConstraints = {@UniqueConstraint(name="UQ_TANK_NUMBER", columnNames={"owned_by_id", "tankNumber"})})
@NamedEntityGraph(name = "Tank.calibrationEntries",
        attributeNodes = @NamedAttributeNode("calibrationEntries"))
@Audited
@Getter
@Setter
public class Tank implements Barcodeable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="TANK_SEQ")
    @SequenceGenerator(name="TANK_SEQ", sequenceName="TANK_SEQ", initialValue = 1, allocationSize = 1)
    private Long id;

    @NotBlank
    @Pattern(regexp = "^.{1,25}$",groups= ValidationGroup.DeviceValidation.class)
    @Column(length = 25, nullable = false)
    private String tankNumber;

    @NotNull
    private ReadMethod defaultReadMethod;

    @NotNull(groups= ValidationGroup.DeviceValidation.class)
    private TankWeightUom weightUom;

    @NotNull(groups= ValidationGroup.DeviceValidation.class)
    private StickReadingUom stickReadingUom;

    @Min(0)
    private BigDecimal calibrationRange;

    @Digits(integer=Location.LAT_DIGITS_INTEGER, fraction=Location.LAT_DIGITS_FRACTION)
    @DecimalMin(Location.LAT_DECIMAL_MIN)
    @DecimalMax(Location.LAT_DECIMAL_MAX)
    private BigDecimal latitude;

    @Digits(integer= Location.LON_DIGITS_INTEGER, fraction=Location.LON_DIGITS_FRACTION)
    @DecimalMin(Location.LON_DECIMAL_MIN)
    @DecimalMax(Location.LON_DECIMAL_MAX)
    private BigDecimal longitude;

    @ManyToOne(optional=false)
    private Producer ownedBy;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name="TANK_ID")
    @NotAudited
    private Set<CalibrationEntry> calibrationEntries = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JoinTable(name = "TANK_BARCODE",
            joinColumns = @JoinColumn(name="tank_id", referencedColumnName="ID"),
            inverseJoinColumns = @JoinColumn(name="barcode_id", referencedColumnName="ID"))
    private Set<Barcode> barcodes = new HashSet<>();

    @Version
    @Column(name = "TX_LOCK")
    private Integer txLock;

    @NotNull
    private MilkGrade milkGrade;

    @NotNull
    private MilkType milkType;

    @NotNull
    private Boolean enableNoWeightAvailable;

    public Tank() {
        super();
    }

    /*
     * Required-args constructor
     */
    public Tank(Producer ownedBy, String tankNumber, ReadMethod defaultReadMethod, TankWeightUom weightUom, StickReadingUom stickReadingUom) {
        this(tankNumber, defaultReadMethod, weightUom, stickReadingUom);
        this.ownedBy = ownedBy;
    }

    public Tank(String tankNumber, ReadMethod defaultReadMethod, TankWeightUom weightUom, StickReadingUom stickReadingUom) {
        super();
        this.tankNumber = tankNumber;
        this.defaultReadMethod = defaultReadMethod;
        this.weightUom = weightUom;
        this.stickReadingUom = stickReadingUom;
    }

    public void addCalibrationEntry(CalibrationEntry ce) {
        calibrationEntries.add(ce);
    }

    public void addCalibrationEntry(Integer stickReadingMajor, Integer stickReadingMinor, BigDecimal weight) {
        calibrationEntries.add(new CalibrationEntry(stickReadingMajor, stickReadingMinor, weight));
    }

    public Barcode getBarcode() {
        return barcodes.stream().findFirst().orElse(null);
    }

    public void setBarcode(Barcode barcode) {
        if (barcode != null) {
            if (barcode.getId() != null && barcodes.stream().noneMatch(barcode1 -> barcode1.getId()==barcode.getId())) {
                barcodes.add(barcode);
            } else if (barcode.getId() == null && barcodes.stream().noneMatch(barcode1 -> barcode1.getCode().equals(barcode.getCode()))) {
                barcodes.add(barcode);
            }
        }
    }

    public Boolean getEnableNoWeightAvailable () {
        return enableNoWeightAvailable;
    }

    public void setEnableNoWeightAvailable (Boolean enableNoWeightAvailable) {
        this.enableNoWeightAvailable = enableNoWeightAvailable;
    }

    @Override
    public String toString() {
        return "Tank{" +
                "id=" + id +
                ", tankNumber='" + tankNumber + "'" +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Tank tank = (Tank) o;
        if(tank.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, tank.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
