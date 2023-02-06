package com.everag.mobilemanifest.bootstrap.interfaces.web.rest.mapper.equipment;

import com.everag.mobilemanifest.bootstrap.domain.model.equipment.Barcode;
import com.everag.mobilemanifest.bootstrap.interfaces.web.rest.dto.equipment.BarcodeDTO;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.Set;

@Mapper(componentModel = "springLazy")
public interface BarcodeMapper {
    BarcodeDTO barcodeToBarcoderDTO(Barcode barcode);

    Set<BarcodeDTO> barcodesToBarcodeDTOs(Set<Barcode> barcodes);

    List<BarcodeDTO> barcodeListToBarcodeDTOList(List<Barcode> barcodeList);
}
