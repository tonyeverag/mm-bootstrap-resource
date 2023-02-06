package com.everag.mobilemanifest.bootstrap.domain.model.equipment;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Getter
@Setter
public class CalibrationEntry implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CALIBRATION_SEQ")
    @SequenceGenerator(name = "CALIBRATION_SEQ", sequenceName = "CALIBRATION_SEQ", initialValue = 1, allocationSize = 1)
    private Long id;

    @NotNull
    private BigDecimal weight;

    @NotNull
    private Integer stickReadingMajor;

    @NotNull
    private Integer stickReadingMinor;

    public CalibrationEntry() {
        super();
    }

    /*
     * Required-args constructor
     */
    public CalibrationEntry(Integer stickReadingMajor, Integer stickReadingMinor, BigDecimal weight) {
        super();
        this.stickReadingMajor = stickReadingMajor;
        this.stickReadingMinor = stickReadingMinor;
        this.weight = weight;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CalibrationEntry calibrationEntry = (CalibrationEntry) o;
        if (calibrationEntry.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, calibrationEntry.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "CalibrationEntry{" + "id=" + id + ", weight='" + weight + "'" + ", stickReadingMajor='"
                + stickReadingMajor + "'" + ", stickReadingMinor='" + stickReadingMinor + "'" + '}';
    }
}
