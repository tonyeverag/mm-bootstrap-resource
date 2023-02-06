package com.everag.mobilemanifest.bootstrap.domain.model.security;

import com.everag.mobilemanifest.bootstrap.domain.model.user.SamplerLicense;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Audited
@Getter
@Setter
public abstract class TransporterUser extends SysUser{

    @OneToMany(mappedBy = "transporter", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<SamplerLicense> samplerLicences = new HashSet<>();

    public TransporterUser() {
        super();
    }

    public TransporterUser(String firstName, String lastName, String username, String email, boolean receivesNotification) {
        super(firstName, lastName, username, email, receivesNotification);
    }

    public String getSamplerNumber(){
        if (samplerLicences == null || samplerLicences.isEmpty()) return null;
        List<String> samplerNumbers = samplerLicences.stream().map(sl -> sl.getSamplerNumber()).collect(Collectors.toList());
        return String.join(", ", samplerNumbers);
    }


    public void setSamplerLicences(Set<SamplerLicense> samplerLicences) {
        this.samplerLicences = samplerLicences;
        if (samplerLicences != null) {
            this.samplerLicences.forEach((license) -> {
                license.setTransporter(this);
            });
        }
    }
}
