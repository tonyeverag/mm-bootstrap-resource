package com.everag.mobilemanifest.bootstrap.domain.model.security;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;
import org.hibernate.validator.constraints.Email;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@DiscriminatorColumn
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Audited
@Getter
@Setter
public abstract class SysUser {
    /**
     * Login attempts before a user is locked out.
     */
    public static final int LOGIN_LIMIT = 5;

    public static final int PASSWORD_MIN_LENGTH = 6;
    public static final int PASSWORD_MAX_LENGTH = 100;

    /**
     * Authority required to configure this type of user.
     *
     * @return
     */
    public abstract BasicAuthorityType authorityRequired();

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SYS_USER_SEQ")
    @SequenceGenerator(name = "SYS_USER_SEQ", sequenceName = "SYS_USER_SEQ", initialValue = 1, allocationSize = 1)
    private Long id;

    @Column(name="DTYPE", insertable = false, updatable = false)
    protected String dtype;

    @NotNull
    @Size(min = 1, max = 255)
    private String firstName;

    @NotNull
    @Size(min = 1, max = 255)
    private String lastName;

    @NotNull
    @Email
    @Size(min = 1, max = 255)
    private String email;

    private String phoneNumber;

    @NotNull
    @Size(min = 6, max = 50)
    @Pattern(regexp = "^[a-zA-Z0-9._@-]*$")
    @Column(length = 50, unique = true)
    private String username;

    @Column(length = 64)
    private String passwordHash;

    @NotNull
    private Boolean enabled = true;

    @NotNull
    private Boolean receivesNotifications = true;

    @NotNull
    private Boolean receivesPlantNotifications = false;

    @NotNull
    private Boolean receivesLabNotifications = false;

    @Column(name = "receives_alert_notifications", nullable = false)
    private boolean receivesAlertNotifications = false;

    @Column(name = "receives_unplan_notifications")
    private Boolean receivesUnplannedNotifications = false;

    @Column(name = "receives_verbose_notifications", nullable = false)
    private boolean receivesVerboseNotifications = false;

    @NotNull
    private Boolean passwordExpired = false;

    @Version
    private Integer txLock;

    @OneToMany(cascade = {CascadeType.ALL}, mappedBy = "assignedUser", targetEntity = AssignedRole.class, orphanRemoval=true)
    private Set<AssignedRole> assignedRoles = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "sysUser")
    @NotAudited
    private Set<LoginAttempt> loginAttempts = new HashSet<>();

    private String appVersion;

    private String platform;

    @NotNull
    private boolean enrolledInBeta;

    public boolean accountNonLocked() {
        return loginAttempts.size() < LOGIN_LIMIT;
    }

    /**
     * Default authority that this user type gets, such as
     * <code>AUTHORITY_CSR</code>
     *
     * @return standardAuthority
     */
    public abstract String standardAuthority();

    public SysUser() {
        super();
    }

    public SysUser(String firstName, String lastName, String username, String email, boolean receivesNotifications) {
        super();
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.email = email;
        this.receivesNotifications=receivesNotifications;
    }

    public String fullName() {
        return firstName + " " + lastName;
    }

    public boolean isReceivesPlantNotifications() {
        return receivesPlantNotifications;
    }

    public boolean isReceivesLabNotifications() {
        return receivesLabNotifications;
    }

    public boolean isReceivesAlertNotifications() {
        return receivesAlertNotifications;
    }

    public boolean isReceivesUnplannedNotifications() {
        return receivesUnplannedNotifications;
    }

    public boolean isReceivesVerboseNotifications() {
        return receivesVerboseNotifications;
    }

    public void addAssignedRole(AssignedRole assignedRole) {
        assignedRole.setAssignedUser(this);
        this.assignedRoles.add(assignedRole);
    }
    public void removeAssignedRole(AssignedRole assignedRole) {
        this.assignedRoles.remove(assignedRole);
    }

    public String getName() {
        return String.format("%s,%s", getFirstName(), getLastName());
    }

    @JsonIgnore
    public String getFirstAndLastNameNoComma() {
        return String.format("%s %s", getFirstName(), getLastName());
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("id: ").append(getId()).append(", ");
        sb.append("username: ").append(getUsername()).append(", ");
        sb.append("email: ").append(getEmail()).append(", ");
        sb.append("firstName: ").append(getFirstName()).append(", ");
        sb.append("lastName: ").append(getLastName()).append(", ");
        sb.append("assignedRoles: ").append(getAssignedRoles());
        return sb.toString();
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
