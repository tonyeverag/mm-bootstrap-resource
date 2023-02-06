package com.everag.mobilemanifest.bootstrap.domain.model.user;

import com.everag.mobilemanifest.bootstrap.domain.model.enums.USState;
import com.everag.mobilemanifest.bootstrap.domain.model.security.TransporterUser;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.ZonedDateTime;

@Entity
@Audited
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SamplerLicense {
    private static final String SEQUENCE_NAME = "SAMPLER_LICENSE_SEQ";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SEQUENCE_NAME)
    @SequenceGenerator(name = SEQUENCE_NAME, sequenceName = SEQUENCE_NAME, initialValue = 1, allocationSize = 1)
    private Long id;

    @NotNull
    @Size(max = 24)
    private String samplerNumber;

    // TODO set both @NotNull annotations...
    // validation would fail if data is not present in database...
//    @NotNull
    private USState state;

    //    @NotNull
    private ZonedDateTime expirationDate;

    @ManyToOne(fetch = FetchType.EAGER)
    private TransporterUser transporter;

    @Override
    public String toString() {
        var stateCode = state != null ? " (" + state.getCode() + ")" : "";
        return samplerNumber + stateCode;
    }
}
