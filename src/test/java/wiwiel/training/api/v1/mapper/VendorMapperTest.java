package wiwiel.training.api.v1.mapper;

import org.junit.jupiter.api.Test;
import wiwiel.training.api.v1.model.VendorDTO;
import wiwiel.training.domain.Vendor;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class VendorMapperTest {
    public static final String NAME = "Fruits Ltd";
    public static final long ID = 1L;

    VendorMapper vendorMapper = VendorMapper.INSTANCE;

    @Test
    void vendorToVendorDto() {
        //given
        Vendor vendor = new Vendor();
        vendor.setName(NAME);
        vendor.setId(ID);

        //when
        VendorDTO vendorDTO = vendorMapper.vendorToVendorDto(vendor);

        //then
        assertEquals(NAME, vendorDTO.getName());
    }

    @Test
    void vendorDtoToVendor() {
        //given
        VendorDTO vendorDTO = new VendorDTO();
        vendorDTO.setName(NAME);
        vendorDTO.setVendorUrl("/vendor/1");

        //when
        Vendor vendor = vendorMapper.vendorDtoToVendor(vendorDTO);

        //then
        assertEquals(NAME, vendor.getName());
    }
}
