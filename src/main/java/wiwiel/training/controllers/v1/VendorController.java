package wiwiel.training.controllers.v1;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import wiwiel.training.api.v1.model.VendorDTO;
import wiwiel.training.api.v1.model.VendorListDTO;
import wiwiel.training.services.VendorService;

@Tag(name = "Vendor controller", description = "Performs operations on vendors")
@RestController
@RequestMapping(VendorController.BASE_URL)
public class VendorController {
    public static final String BASE_URL = "/api/v1/vendors/";

    private final VendorService vendorService;

    public VendorController(VendorService vendorService) {
        this.vendorService = vendorService;
    }

    @Operation(description = "List of all vendors")
    @GetMapping
    public VendorListDTO getAllVendors(){
        return new VendorListDTO(vendorService.getAllVendors());
    }

    @GetMapping("{id}")
    public VendorDTO getVendorById(@PathVariable Long id){
        return vendorService.getVendorById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public VendorDTO createNewVendor(@RequestBody VendorDTO vendorDTO){
        return vendorService.createNewVendor(vendorDTO);
    }

    @PutMapping("{id}")
    public VendorDTO updateVendor(@PathVariable Long id, @RequestBody VendorDTO vendorDTO){
        return vendorService.saveVendorByDTO(id, vendorDTO);
    }

    @PatchMapping("{id}")
    public VendorDTO patchVendor(@PathVariable Long id, @RequestBody VendorDTO vendorDTO){
        return vendorService.patchVendor(id, vendorDTO);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteVendor(@PathVariable Long id){
        vendorService.deleteVendorById(id);
    }
}
