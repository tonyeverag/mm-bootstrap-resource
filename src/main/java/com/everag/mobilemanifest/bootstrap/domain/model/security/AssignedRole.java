package com.everag.mobilemanifest.bootstrap.domain.model.security;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Audited
@Inheritance(strategy= InheritanceType.TABLE_PER_CLASS)
@Getter
@Setter
public abstract class AssignedRole implements Comparable<AssignedRole> {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="ASSIGNED_ROLE_SEQ")
    @SequenceGenerator(name="ASSIGNED_ROLE_SEQ", sequenceName="ASSIGNED_ROLE_SEQ", initialValue = 1, allocationSize = 1)
    private Long id;

    @ManyToOne(optional=false)
    private SysUser assignedUser;

    @Version
    private Integer txLock;

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AssignedRole assignedRole = (AssignedRole) o;
        if (assignedRole.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), assignedRole.getId());
    }

    @Override
    public int compareTo(AssignedRole ar) {
        if (this.equals(ar)) {
            return 0;
        }
        if (getId() != null && ar.getId() != null) {
            return getId().compareTo(ar.getId());
        } else if (getId() == null) {
            return 1;
        } else if (ar.getId() == null) {
            return -1;
        }

        return 0;
    }
}
