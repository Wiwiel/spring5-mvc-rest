package wiwiel.training.controllers.v1;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import wiwiel.training.api.v1.model.CustomerDTO;
import wiwiel.training.api.v1.model.CustomerListDTO;
import wiwiel.training.services.CustomerService;
import org.springframework.http.HttpStatus;

@Tag(name = "Customer Controller", description = "This is my Customer Controller")
@RestController
@RequestMapping(CustomerController.BASE_URL)
public class CustomerController {
    public static final String BASE_URL = "/api/v1/customers/";
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @Operation(summary = "This will get a list of customers.", description = "These are some notes about the API.")
    @GetMapping
    public CustomerListDTO getAllCustomers(){
        return new CustomerListDTO(customerService.getAllCustomers());
    }

    @GetMapping("{id}")
    public CustomerDTO getCustomerById(@PathVariable Long id){
        return customerService.getCustomerById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CustomerDTO createNewCustomer(@RequestBody CustomerDTO customerDTO){
        return customerService.createNewCustomer(customerDTO);
    }

    @PutMapping("{id}")
    public CustomerDTO updateCustomer(@PathVariable Long id, @RequestBody CustomerDTO customerDTO){
        return customerService.saveCustomerByDTO(id, customerDTO);
    }

    @PatchMapping("{id}")
    public CustomerDTO patchCustomer(@PathVariable Long id, @RequestBody CustomerDTO customerDTO){
        return customerService.patchCustomer(id, customerDTO);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteCustomer(@PathVariable Long id){
        customerService.deleteCustomerById(id);
    }
}
