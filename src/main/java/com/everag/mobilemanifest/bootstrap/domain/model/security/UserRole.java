package com.everag.mobilemanifest.bootstrap.domain.model.security;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(uniqueConstraints={@UniqueConstraint(name="UQ_USER_ROLE_NAME", columnNames = { "name" })})
@Audited
@Getter
@Setter
public class UserRole {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "USER_ROLE_SEQ")
    @SequenceGenerator(name = "USER_ROLE_SEQ", sequenceName = "USER_ROLE_SEQ", initialValue = 1, allocationSize = 1)
    private Long id;

    @NotNull
    private String name;

    private String displayName;

    @Version
    @Column(name = "TX_LOCK")
    private Integer txLock;

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
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
        UserRole other = (UserRole) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        return true;
    }
}
