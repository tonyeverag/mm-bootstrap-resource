package com.everag.mobilemanifest.bootstrap.domain.model.security;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.envers.Audited;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Entity
@Audited
@Getter
@Setter
public class AssignedPlantUserRole extends AssignedRole  {
    @NotNull
    @ManyToOne
    private PlantUserRole plantUserRole;
}
