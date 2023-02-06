package com.everag.mobilemanifest.bootstrap.interfaces.web.rest.dto.equipment;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Getter
@Setter
public class BarcodeDTO  implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    @Pattern(regexp = "^DDC[0-9]+")
    @Size(max = 13)
    private String code;

    private Integer txLock;

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((code == null) ? 0 : code.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
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
        BarcodeDTO other = (BarcodeDTO) obj;
        if (code == null) {
            if (other.code != null)
                return false;
        } else if (!code.equals(other.code))
            return false;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "BarcodeDTO [id=" + id + ", code=" + code + ", txLock=" + txLock + "]";
    }


}
