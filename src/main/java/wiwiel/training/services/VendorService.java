package wiwiel.training.services;

import wiwiel.training.api.v1.model.VendorDTO;

import java.util.List;

public interface VendorService {
    List<VendorDTO> getAllVendors();
    VendorDTO getVendorById(Long id);
    VendorDTO createNewVendor(VendorDTO customerDTO);
    VendorDTO saveVendorByDTO(Long id, VendorDTO customerDTO);
    VendorDTO patchVendor(Long id, VendorDTO customerDTO);
    void deleteVendorById(Long id);
}
