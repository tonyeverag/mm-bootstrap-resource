package com.everag.mobilemanifest.bootstrap.domain.model.security;

import com.everag.mobilemanifest.bootstrap.domain.model.location.Producer;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@DiscriminatorValue("PRODUCER")
@Audited
@Getter
@Setter
@NamedEntityGraph(name = "ProducerUser.producers", attributeNodes = @NamedAttributeNode("producers"))
public class ProducerUser extends TransporterUser implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final String PRODUCER_ROLE = "Producer Role";

    @ManyToMany
    @JoinTable(name = "producer_user_producer",
            joinColumns = @JoinColumn(name = "producer_user_id", referencedColumnName = "ID"),
            inverseJoinColumns = @JoinColumn(name = "producer_id", referencedColumnName = "ID")
    )
    private Set<Producer> producers = new HashSet<>();

    public ProducerUser() {
        super();
    }

    public ProducerUser(String firstName, String lastName, String userName, String email, Set<Producer> producers, boolean receivesNotifications) {
        super(firstName, lastName, userName, email, receivesNotifications);
        this.producers = producers;
    }

    @Override
    public BasicAuthorityType authorityRequired() {
        return BasicAuthorityType.CONFIGURATION_PRODUCER_USER;
    }

    @Override
    public String standardAuthority() {
        return "ROLE_PRODUCER";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ProducerUser producerUser = (ProducerUser) o;
        if (producerUser.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), producerUser.getId());
    }
}
