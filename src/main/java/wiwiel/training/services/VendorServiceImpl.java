package wiwiel.training.services;

import org.springframework.stereotype.Service;
import wiwiel.training.api.v1.mapper.VendorMapper;
import wiwiel.training.api.v1.model.VendorDTO;
import wiwiel.training.controllers.v1.VendorController;
import wiwiel.training.domain.Vendor;
import wiwiel.training.repositories.VendorRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class VendorServiceImpl implements VendorService {
    private final VendorRepository vendorRepository;
    private final VendorMapper vendorMapper;

    public VendorServiceImpl(VendorRepository vendorRepository, VendorMapper vendorMapper) {
        this.vendorRepository = vendorRepository;
        this.vendorMapper = vendorMapper;
    }

    @Override
    public List<VendorDTO> getAllVendors() {
        return vendorRepository.findAll()
                .stream()
                .map(vendor -> {
                    VendorDTO vendorDTO = vendorMapper.vendorToVendorDto(vendor);
                    vendorDTO.setVendorUrl(getVendorUrl(vendor.getId()));
                    return vendorDTO;
                })
                .collect(Collectors.toList());
    }

    @Override
    public VendorDTO getVendorById(Long id) {
        VendorDTO vendorDTO = vendorMapper.vendorToVendorDto(
                vendorRepository.findById(id).orElseThrow(ResourceNotFoundException::new));
        vendorDTO.setVendorUrl(getVendorUrl(id));
        return vendorDTO;
    }

    @Override
    public VendorDTO createNewVendor(VendorDTO vendorDTO) {
        return saveAndReturnDTO(vendorMapper.vendorDtoToVendor(vendorDTO));
    }

    @Override
    public VendorDTO saveVendorByDTO(Long id, VendorDTO vendorDTO) {
        Vendor vendor = vendorMapper.vendorDtoToVendor(vendorDTO);
        vendor.setId(id);
        return saveAndReturnDTO(vendor);
    }

    @Override
    public VendorDTO patchVendor(Long id, VendorDTO vendorDTO) {
        return vendorRepository.findById(id).map(vendor -> {
            if (vendorDTO.getName() != null)
                vendor.setName(vendorDTO.getName());
            return saveAndReturnDTO(vendor);
        }).orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public void deleteVendorById(Long id) {
        vendorRepository.deleteById(id);
    }

    private VendorDTO saveAndReturnDTO(Vendor vendor) {
        Vendor savedVendor = vendorRepository.save(vendor);
        VendorDTO returnDto = vendorMapper.vendorToVendorDto(savedVendor);
        returnDto.setVendorUrl(getVendorUrl(savedVendor.getId()));
        return returnDto;
    }

    private String getVendorUrl(Long id) {
        return VendorController.BASE_URL + id;
    }
}
