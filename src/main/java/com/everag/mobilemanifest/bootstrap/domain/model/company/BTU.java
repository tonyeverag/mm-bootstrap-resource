package com.everag.mobilemanifest.bootstrap.domain.model.company;

import com.everag.mobilemanifest.bootstrap.domain.model.location.Producer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = { "btuNumber"}, name = "UQ_BTU_BTUNUMBER") )
@Audited
@Getter
@Setter
public class BTU implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "BTU_SEQ")
    @SequenceGenerator(name = "BTU_SEQ", sequenceName = "BTU_SEQ", initialValue = 1, allocationSize = 1)
    private Long id;

    @NotNull
    @Column(nullable=false)
    @Size(max=255)
    private String name;

    @NotNull
    @Column(nullable=false)
    @Size(max=255)
    private String btuNumber;

    @Version
    private Integer txLock;

    @OneToMany(mappedBy = "btu")
    @JsonIgnore
    private Set<Producer> producers = new HashSet<>();


    public BTU() {
        super();
    }

    public BTU(String name, String btuNumber) {
        super();
        this.name = name;
        this.btuNumber = btuNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        BTU bTU = (BTU) o;
        if(bTU.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, bTU.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "BTU{" +
                "id=" + id +
                ", name='" + name + "'" +
                '}';
    }
}
