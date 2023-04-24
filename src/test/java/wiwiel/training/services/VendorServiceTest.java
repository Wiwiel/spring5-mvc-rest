package wiwiel.training.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import wiwiel.training.api.v1.mapper.VendorMapper;
import wiwiel.training.api.v1.model.VendorDTO;
import wiwiel.training.controllers.v1.VendorController;
import wiwiel.training.domain.Vendor;
import wiwiel.training.repositories.VendorRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class VendorServiceTest {
    
    public static final String NAME = "Fruits Ltd";
    public static final long ID = 1L;
    
    @Mock
    VendorRepository vendorRepository;
    
    VendorService vendorService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        vendorService = new VendorServiceImpl(vendorRepository, VendorMapper.INSTANCE);
    }

    @Test
    void getAllVendors() {
        List<Vendor> vendors = Arrays.asList(new Vendor(), new Vendor(), new Vendor());
        when(vendorRepository.findAll()).thenReturn(vendors);

        List<VendorDTO> vendorDTOS = vendorService.getAllVendors();

        assertEquals(3, vendorDTOS.size());
    }

    @Test
    void getVendorById() {
        Vendor vendor = new Vendor();
        vendor.setId(ID);
        vendor.setName(NAME);

        when(vendorRepository.findById(anyLong())).thenReturn(Optional.of(vendor));

        VendorDTO vendorDTO = vendorService.getVendorById(ID);

        assertEquals(ID, vendorDTO.getId());
        assertEquals(NAME, vendorDTO.getName());
        assertEquals(VendorController.BASE_URL + "1", vendorDTO.getVendorUrl());
    }


    @Test
    void createNewVendor() {
        VendorDTO vendor = new VendorDTO();
        vendor.setName(NAME);

        Vendor savedVendor = new Vendor();
        savedVendor.setName(vendor.getName());
        savedVendor.setId(ID);

        when(vendorRepository.save(any(Vendor.class))).thenReturn(savedVendor);

        VendorDTO savedDto = vendorService.createNewVendor(vendor);

        assertEquals(NAME, savedDto.getName());
        assertEquals(VendorController.BASE_URL + "1", savedDto.getVendorUrl());
    }

    @Test
    public void saveVendorByDTO() throws Exception {

        //given
        VendorDTO vendor = new VendorDTO();
        vendor.setName(NAME);

        Vendor savedVendor = new Vendor();
        savedVendor.setName(vendor.getName());
        savedVendor.setId(ID);

        when(vendorRepository.save(any(Vendor.class))).thenReturn(savedVendor);

        //when
        VendorDTO savedDto = vendorService.saveVendorByDTO(1L, vendor);

        //thenF
        assertEquals(vendor.getName(), savedDto.getName());
        assertEquals(VendorController.BASE_URL + "1", savedDto.getVendorUrl());
    }

    @Test
    void deleteVendorById() {
        vendorService.deleteVendorById(ID);
        verify(vendorRepository, times(1)).deleteById(anyLong());
    }
}