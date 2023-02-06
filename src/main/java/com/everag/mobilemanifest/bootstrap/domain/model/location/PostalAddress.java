package com.everag.mobilemanifest.bootstrap.domain.model.location;

import com.everag.mobilemanifest.bootstrap.domain.model.enums.USState;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Objects;

@Entity
@Audited
@Getter
@Setter
public class PostalAddress {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "POSTAL_ADDRESS_SEQ")
    @SequenceGenerator(name = "POSTAL_ADDRESS_SEQ", sequenceName = "POSTAL_ADDRESS_SEQ", initialValue = 1, allocationSize = 1)
    private Long id;

    @Size(max = 255)
    @Column(nullable = false, length = 255)
    private String address1;

    @Size(max = 255)
    @Column(length = 255)
    private String address2;

    @Size(max = 255)
    @Column(nullable = false, length = 255)
    private String city;

    @Enumerated(EnumType.STRING)
    private USState state;

    @Pattern(regexp = "^(\\d{5}-\\d{4}|\\d{5}|\\d{0})$")
    private String zip;

    @Version
    private Integer txLock;

    public PostalAddress() {
        super();
    }

    public PostalAddress(String address1, String address2, String city, USState state, String zip) {
        super();
        this.address1 = address1;
        this.address2 = address2;
        this.city = city;
        this.state = state;
        this.zip = zip;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(address1).append(", ");
        sb.append(address2 == null ? "" : address2).append(", ");
        sb.append(city).append(", ");
        sb.append(state).append(" ");
        sb.append(zip);
        return sb.toString();
    }

    public String getFullAddress() {
        StringBuilder sb = new StringBuilder();
        sb.append(address1).append(" ");
        sb.append(address2 == null ? "" : address2).append(" ");
        sb.append(city).append(", ");
        sb.append(state).append(" ");
        sb.append(zip);
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
        PostalAddress postalAddress = (PostalAddress) o;
        if (postalAddress.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, postalAddress.id);
    }

    @Override
    public int hashCode() {
        if (id != null) {
            return Objects.hashCode(id);
        } else {
            return super.hashCode();
        }
    }
}
