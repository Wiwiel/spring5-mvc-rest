package wiwiel.training.api.v1.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import wiwiel.training.api.v1.model.VendorDTO;
import wiwiel.training.domain.Vendor;

@Mapper
public interface VendorMapper {
    VendorMapper INSTANCE = Mappers.getMapper(VendorMapper.class);

    VendorDTO vendorToVendorDto(Vendor vendor);
    Vendor vendorDtoToVendor(VendorDTO vendor);
}
