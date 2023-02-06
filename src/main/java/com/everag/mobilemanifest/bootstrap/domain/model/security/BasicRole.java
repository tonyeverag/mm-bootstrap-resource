package com.everag.mobilemanifest.bootstrap.domain.model.security;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Audited
@DiscriminatorValue("BASIC")
@Getter
@Setter
@NamedEntityGraph(name = "BasicAuthorityType.detail",
        attributeNodes = @NamedAttributeNode("basicAuthorityTypes"))
public class BasicRole extends UserRole {

    @ElementCollection
    @CollectionTable(name="basic_role_auth_types") // default join table name is too long for Oracle
    @Enumerated(EnumType.STRING)
    private Set<BasicAuthorityType> basicAuthorityTypes = new HashSet<>();
}
