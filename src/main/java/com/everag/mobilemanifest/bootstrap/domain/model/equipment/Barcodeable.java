package com.everag.mobilemanifest.bootstrap.domain.model.equipment;

import java.util.Set;

public interface Barcodeable {
    Set<Barcode> getBarcodes();
    void setBarcodes(Set<Barcode> barcodes);
}
