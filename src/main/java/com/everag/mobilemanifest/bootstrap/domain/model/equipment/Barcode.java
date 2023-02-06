package com.everag.mobilemanifest.bootstrap.domain.model.equipment;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Entity
@Table( uniqueConstraints = {@UniqueConstraint(name="UQ_SERIAL_NUMBER", columnNames={"code"})})
@Audited
@Getter
@Setter
@NoArgsConstructor
public class Barcode {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="BARCODE_SEQ")
    @SequenceGenerator(name="BARCODE_SEQ", sequenceName="BARCODE_SEQ", initialValue = 1, allocationSize = 1)
    private Long id;

    @NotNull
    @Pattern(regexp = "^DDC[0-9]+")
    @Size(max = 13)
    private String code;

    @Version
    private Integer txLock;

    public Barcode(String code) {
        this.code = code;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((code == null) ? 0 : code.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Barcode other = (Barcode) obj;
        if (code == null) {
            if (other.code != null)
                return false;
        } else if (!code.equals(other.code))
            return false;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Barcode [id=" + id + ", code=" + code + ", txLock=" + txLock + "]";
    }

}
