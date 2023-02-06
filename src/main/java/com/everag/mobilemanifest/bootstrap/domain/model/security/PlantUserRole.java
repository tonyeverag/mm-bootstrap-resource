package com.everag.mobilemanifest.bootstrap.domain.model.security;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.envers.Audited;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@Audited
@DiscriminatorValue("PLANT_USER")
@Getter
@Setter
public class PlantUserRole extends UserRole {
}
