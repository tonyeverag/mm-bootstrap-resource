package com.everag.mobilemanifest.bootstrap.domain.model.location;

import com.everag.mobilemanifest.bootstrap.domain.model.enums.USState;
import lombok.Data;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

import static org.hibernate.envers.RelationTargetAuditMode.NOT_AUDITED;

@Entity
@Table
@Audited(targetAuditMode = NOT_AUDITED)
@Data
public class County {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "COUNTY_SEQ")
    @SequenceGenerator(name = "COUNTY_SEQ", sequenceName = "COUNTY_SEQ", initialValue = 1, allocationSize = 1)
    private Long id;

    @NotNull
    @Column(nullable=false)
    @Size(max=6)
    private String code;

    @Size(max=40)
    private String name;

    @Size(max=40)
    private String description;

    private USState state;

    @OneToMany(mappedBy = "county", fetch = FetchType.LAZY)
    private Set<Producer> producers = new HashSet<>();


    public County() {
        super();
    }
}
