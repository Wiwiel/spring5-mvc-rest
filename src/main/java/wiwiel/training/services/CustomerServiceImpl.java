package wiwiel.training.services;

import wiwiel.training.api.v1.mapper.CustomerMapper;
import wiwiel.training.api.v1.model.CustomerDTO;
import wiwiel.training.controllers.v1.CustomerController;
import wiwiel.training.domain.Customer;
import wiwiel.training.repositories.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    public CustomerServiceImpl(CustomerRepository customerRepository, CustomerMapper customerMapper) {
        this.customerRepository = customerRepository;
        this.customerMapper = customerMapper;
    }

    @Override
    public List<CustomerDTO> getAllCustomers() {
        return customerRepository.findAll()
                .stream()
                .map(customer -> {
                    CustomerDTO customerDTO = customerMapper.customerToCustomerDto(customer);
                    customerDTO.setCustomerUrl(getCustomerUrl(customer.getId()));
                    return customerDTO;
                })
                .collect(Collectors.toList());
    }

    @Override
    public CustomerDTO getCustomerById(Long id) {
        CustomerDTO customer = customerMapper.customerToCustomerDto(customerRepository.findById(id)
                .orElseThrow(ResourceNotFoundException::new));
        if(customer != null)
            customer.setCustomerUrl(getCustomerUrl(customer.getId()));
        return customer;
    }

    @Override
    public CustomerDTO createNewCustomer(CustomerDTO customerDTO) {
        return saveAndReturnDTO(customerMapper.customerDtoToCustomer(customerDTO));
    }

    @Override
    public CustomerDTO saveCustomerByDTO(Long id, CustomerDTO customerDTO) {
        Customer customer = customerMapper.customerDtoToCustomer(customerDTO);
        customer.setId(id);

        return saveAndReturnDTO(customer);
    }

    private CustomerDTO saveAndReturnDTO(Customer customer) {
        Customer savedCustomer = customerRepository.save(customer);
        CustomerDTO returnDto = customerMapper.customerToCustomerDto(savedCustomer);
        returnDto.setCustomerUrl(getCustomerUrl(savedCustomer.getId()));
        return returnDto;
    }

    @Override
    public CustomerDTO patchCustomer(Long id, CustomerDTO customerDTO) {
        return customerRepository.findById(id).map(customer -> {

            if(customerDTO.getFirstname() != null){
                customer.setFirstName(customerDTO.getFirstname());
            }

            if(customerDTO.getLastname() != null){
                customer.setLastName(customerDTO.getLastname());
            }

            return saveAndReturnDTO(customer);
        }).orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public void deleteCustomerById(Long id){
        customerRepository.deleteById(id);
    };

    private String getCustomerUrl(Long id) {
        return CustomerController.BASE_URL + id;
    }
}
