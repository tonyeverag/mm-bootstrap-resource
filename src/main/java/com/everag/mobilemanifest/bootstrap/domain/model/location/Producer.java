package com.everag.mobilemanifest.bootstrap.domain.model.location;

import com.everag.mobilemanifest.bootstrap.domain.model.company.BTU;
import com.everag.mobilemanifest.bootstrap.domain.model.company.Division;
import com.everag.mobilemanifest.bootstrap.domain.model.company.SupplyCompany;
import com.everag.mobilemanifest.bootstrap.domain.model.enums.MilkType;
import com.everag.mobilemanifest.bootstrap.domain.model.enums.Status;
import com.everag.mobilemanifest.bootstrap.domain.model.equipment.Tank;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = { "supply_company_id", "producercode" }, name = "UQ_SUPPLYCOMPANY_NUMERICCODE") )
@NamedEntityGraphs(value = {
        @NamedEntityGraph( name = "Producer.tanks", attributeNodes = @NamedAttributeNode("tanks")),
        @NamedEntityGraph( name = "producerBootstrap", attributeNodes = {
                @NamedAttributeNode(value = "tanks", subgraph="tanks"),
                @NamedAttributeNode(value = "supplyCompany", subgraph = "supplyCompany"),
                @NamedAttributeNode("division"),
                @NamedAttributeNode(value = "managedServicesProvider", subgraph = "supplyCompany"),
                @NamedAttributeNode("btu"),
                @NamedAttributeNode("address"),
                @NamedAttributeNode("county")
        },
                subgraphs = {
                        @NamedSubgraph(name = "tanks",
                                attributeNodes = {
                                        @NamedAttributeNode(value = "barcodes")
                                }
                        ),
                        @NamedSubgraph(name = "supplyCompany",
                                attributeNodes = {
                                        @NamedAttributeNode(value = "company", subgraph = "company"),
                                        @NamedAttributeNode(value = "manifestConfig"),
                                }
                        ),
                        @NamedSubgraph(name = "company",
                                attributeNodes = {
                                        @NamedAttributeNode(value = "address")
                                }
                        )
                }
        )
})
@Audited
@Getter
@Setter
public class Producer  extends Location implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotNull
    @Size(min = 1, max = 100)
    @Column(length = 255, nullable = false)
    private String producerCode;

    @ManyToOne(optional = false)
    private SupplyCompany supplyCompany;

    @ManyToOne
    private SupplyCompany managedServicesProvider;

    @Size(max = 100)
    @Column(length = 255)
    private String managedServicesCode;

    @ManyToOne(fetch=FetchType.LAZY, optional = false)
    private Division division;

    @ManyToOne(fetch=FetchType.LAZY)
    private BTU btu;

    @OneToMany(cascade = CascadeType.ALL, fetch=FetchType.LAZY, mappedBy = "ownedBy", orphanRemoval = true)
    private Set<Tank> tanks = new HashSet<>();

    @Digits(integer = Location.LAT_DIGITS_INTEGER, fraction = Location.LAT_DIGITS_FRACTION)
    @DecimalMin(Location.LAT_DECIMAL_MIN)
    @DecimalMax(Location.LAT_DECIMAL_MAX)
    private BigDecimal latitude;

    @Digits(integer = Location.LON_DIGITS_INTEGER, fraction = Location.LON_DIGITS_FRACTION)
    @DecimalMin(Location.LON_DECIMAL_MIN)
    @DecimalMax(Location.LON_DECIMAL_MAX)
    private BigDecimal longitude;

    @NotNull
    private Boolean requireSamplesOverride = false;

    @Size(max = 128)
    private String permitNumber;

    @ManyToOne(fetch=FetchType.LAZY)
    @NotAudited
    private County county;

    @NotNull
    private Boolean offlineLocation = false;

    @NotNull
    private Boolean allowAsynchronousPickups = false;

    public Producer() {
        super();
    }

    /*
     * Required-args constructor
     */
    public Producer(String producerCode, SupplyCompany supplyCompany, String name, PostalAddress postalAddress, Boolean allowsDrops, BigDecimal latitude, BigDecimal longitude, Status status) {
        super(name, postalAddress, allowsDrops, status);
        this.producerCode = producerCode;
        this.supplyCompany = supplyCompany;
        this.latitude = latitude;
        this.longitude = longitude;
    }
    public Producer(String producerCode, SupplyCompany supplyCompany, String name, PostalAddress postalAddress, Boolean allowsDrops, BigDecimal latitude, BigDecimal longitude, Division division, Status status) {
        this(producerCode, supplyCompany, name, postalAddress, allowsDrops, latitude, longitude, status);
        this.division = division;
    }

    public void setTanks(Set<Tank> tanks) {
        this.tanks = tanks;
        if (tanks != null) {
            this.tanks.forEach((tank) -> {
                tank.setOwnedBy(this);
            });
        }
    }

    public void addTank(Tank tank) {
        tank.setOwnedBy(this);
        tanks.add(tank);
    }

    public MilkType getMilkType(){
        final Comparator<Tank> comparator= (t1, t2)-> Integer.compare(t1.getMilkType().getCode(),t2.getMilkType().getCode());
        Tank tank = getTanks().stream().min(comparator).orElse(null);
        if(tank!=null){
            return tank.getMilkType();
        }
        return null;
    }

    @PrePersist
    private void beforeSave() {
        if (this.getAllowsDrops() == null) {
            this.setAllowsDrops(Boolean.FALSE);
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Name: ").append(getName()).append(", ");
        sb.append("ProducerCode: ").append(getProducerCode()).append(", ");
        sb.append("Tanks: ").append(getTanks() == null ? "null" : getTanks().size()).append(", ");
        sb.append("SupplyCompany: ").append(getSupplyCompany()).append(", ");
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Producer producer = (Producer) o;
        if (producer.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), producer.getId());
    }
}
