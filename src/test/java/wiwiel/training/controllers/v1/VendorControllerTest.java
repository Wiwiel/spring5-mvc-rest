package wiwiel.training.controllers.v1;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import wiwiel.training.api.v1.model.VendorDTO;
import wiwiel.training.controllers.RestResponseEntityExceptionHandler;
import wiwiel.training.services.VendorService;
import wiwiel.training.services.ResourceNotFoundException;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static wiwiel.training.controllers.v1.AbstractRestControllerTest.asJsonString;

class VendorControllerTest {
    
    public static final String NAME = "Fruits Ltd";
    public static final long ID = 1L;

    @Mock
    VendorService vendorService;

    @InjectMocks
    VendorController vendorController;

    MockMvc mockMvc;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(vendorController)
                .setControllerAdvice(new RestResponseEntityExceptionHandler())
                .build();
    }

    @Test
    public void testListCategories() throws Exception {
        VendorDTO vendor1 = new VendorDTO();
        vendor1.setId(ID);
        vendor1.setName(NAME);
        vendor1.setVendorUrl(VendorController.BASE_URL + "1");

        VendorDTO vendor2 = new VendorDTO();
        vendor2.setId(2L);
        vendor2.setName("Bob");
        vendor1.setVendorUrl(VendorController.BASE_URL + "2");

        List<VendorDTO> vendors = Arrays.asList(vendor1, vendor2);

        when(vendorService.getAllVendors()).thenReturn(vendors);

        mockMvc.perform(get(VendorController.BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.vendors", hasSize(2)));
    }

    @Test
    public void testGetByIdVendors() throws Exception {
        VendorDTO vendor1 = new VendorDTO();
        vendor1.setId(ID);
        vendor1.setName(NAME);
        vendor1.setVendorUrl(VendorController.BASE_URL + "1");

        when(vendorService.getVendorById(anyLong())).thenReturn(vendor1);

        mockMvc.perform(get(VendorController.BASE_URL + "1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", equalTo(NAME)));
    }

    @Test
    public void createNewVendor() throws Exception {
        VendorDTO vendor = new VendorDTO();
        vendor.setName(NAME);

        VendorDTO returnVendor = new VendorDTO();
        returnVendor.setId(ID);
        returnVendor.setName(NAME);
        returnVendor.setVendorUrl(VendorController.BASE_URL + "1");

        when(vendorService.createNewVendor(any(VendorDTO.class))).thenReturn(returnVendor);

        mockMvc.perform(post(VendorController.BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(vendor)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", equalTo(NAME)))
                .andExpect(jsonPath("$.vendor_url", equalTo(VendorController.BASE_URL + "1")));
    }

    @Test
    public void testUpdateVendor() throws Exception {
        //given
        VendorDTO vendor = new VendorDTO();
        vendor.setName(NAME);

        VendorDTO returnVendor = new VendorDTO();
        returnVendor.setId(ID);
        returnVendor.setName(NAME);
        returnVendor.setVendorUrl(VendorController.BASE_URL + "1");

        when(vendorService.saveVendorByDTO(anyLong(), any(VendorDTO.class))).thenReturn(returnVendor);

        //when/then
        mockMvc.perform(put(VendorController.BASE_URL + "1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(vendor)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", equalTo(NAME)))
                .andExpect(jsonPath("$.vendor_url", equalTo(VendorController.BASE_URL + "1")));
    }

    @Test
    public void testPatchVendor() throws Exception {

        //given
        VendorDTO vendor = new VendorDTO();
        vendor.setName(NAME);

        VendorDTO returnVendor = new VendorDTO();
        returnVendor.setId(ID);
        returnVendor.setName(vendor.getName());
        returnVendor.setVendorUrl(VendorController.BASE_URL + "1");

        when(vendorService.patchVendor(anyLong(), any(VendorDTO.class))).thenReturn(returnVendor);

        mockMvc.perform(patch("/api/v1/vendors/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(vendor)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", equalTo(NAME)))
                .andExpect(jsonPath("$.vendor_url", equalTo(VendorController.BASE_URL + "1")));
    }

    @Test
    public void testDeleteVendor() throws Exception {

        mockMvc.perform(delete(VendorController.BASE_URL + "1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(vendorService).deleteVendorById(anyLong());
    }

    @Test
    public void testNotFoundException() throws Exception {

        when(vendorService.getVendorById(anyLong())).thenThrow(ResourceNotFoundException.class);

        mockMvc.perform(get(VendorController.BASE_URL + "/222")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

}