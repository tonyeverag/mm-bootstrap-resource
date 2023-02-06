package com.everag.mobilemanifest.bootstrap.interfaces.web.rest.dto.user;

import com.everag.mobilemanifest.bootstrap.domain.model.security.SysUser;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
public class SysUserDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    @NotNull
    @Size(min = 1, max = 255)
    private String firstName;

    @NotNull
    @Size(min = 1, max = 255)
    private String lastName;

    private String dtype;

    @NotNull
    @Email
    @Size(min = 1, max = 255)
    private String email;

    private String phoneNumber;

    @NotNull
    @Size(min = 6, max = 50)
    @Pattern(regexp = "^[a-zA-Z0-9._@-]*$")
    private String username;

    @Size(min = SysUser.PASSWORD_MIN_LENGTH, max = SysUser.PASSWORD_MAX_LENGTH)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String passwordHash;

    @NotNull
    private Boolean enabled;

    protected Boolean receivesNotifications;

    protected Boolean receivesPlantNotifications;

    protected Boolean receivesLabNotifications;

    protected Boolean receivesVerboseNotifications;

    protected Boolean receivesAlertNotifications;

    protected Boolean receivesUnplannedNotifications;

    @NotNull
    private Boolean passwordExpired;

    protected Boolean manageTrucks = false;

    protected Boolean manageTankers = false;

    protected Boolean manageBulkTanks = false;

    private String appVersion;

    private String platform;

    @NotNull
    private boolean enrolledInBeta;

    private Integer txLock;

    public void setReceivesNotifications(Boolean receivesNotifications) {
        if(receivesNotifications != null){
            this.receivesNotifications=receivesNotifications;
        }
        //Otherwise don't change it.
    }

    public void setReceivesPlantNotifications(Boolean receivesPlantNotifications) {
        if(receivesPlantNotifications !=null){
            this.receivesPlantNotifications=receivesPlantNotifications;
        }
        //Otherwise don't change it.
    }

    public void setReceivesLabNotifications(Boolean receivesLabNotifications) {
        if(receivesLabNotifications !=null){
            this.receivesLabNotifications=receivesLabNotifications;
        }
        //Otherwise don't change it.
    }

    public void setReceivesAlertNotifications(Boolean receivesAlertNotifications) {
        if (receivesAlertNotifications !=null){
            this.receivesAlertNotifications = receivesAlertNotifications;
        }
        //Otherwise don't change it.
    }

    public void setReceivesUnplannedNotifications(Boolean receivesUnplannedNotifications) {
        if(receivesUnplannedNotifications !=null){
            this.receivesUnplannedNotifications=receivesUnplannedNotifications;
        }
        //Otherwise don't change it.
    }

    public void setReceivesVerboseNotifications(Boolean receivesVerboseNotifications) {
        if (receivesVerboseNotifications !=null){
            this.receivesVerboseNotifications = receivesVerboseNotifications;
        }
        //Otherwise don't change it.
    }


    /**
     * Default authority that this user type gets, such as
     * <code>AUTHORITY_CSR</code>
     *
     * @return standardAuthority
     */
    public String standardAuthority() {
        return "";
    }

    @JsonIgnore
    public String getFirstAndLastNameNoComma() {
        return String.format("%s %s", getFirstName(), getLastName());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SysUserDTO superUserDTO = (SysUserDTO) o;

        if (!Objects.equals(id, superUserDTO.id))
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        if (id != null) {
            return Objects.hashCode(id);
        } else {
            return super.hashCode();
        }
    }

    @Override
    public String toString() {
        return "SuperUserDTO{" + "id=" + id + ", firstName='" + firstName + "'" + ", lastName='" + lastName + "'"
                + ", email='" + email + "'" + ", phoneNumber='" + phoneNumber + "'" + ", username='" + username + "'"
                + ", passwordHash='" + passwordHash + "'" + ", enabled='" + enabled + "'" + ", passwordExpired='"
                + passwordExpired + "'" + ", receivesNotifications='" + receivesNotifications+ "'" +  '}';
    }
}
