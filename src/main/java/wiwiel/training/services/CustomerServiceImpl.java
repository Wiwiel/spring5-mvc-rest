package wiwiel.training.services;

import wiwiel.training.api.v1.mapper.CustomerMapper;
import wiwiel.training.api.v1.model.CustomerDTO;
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
                    CustomerDTO customerDTO = customerMapper.customerToCustomerDTO(customer);
                    customerDTO.setCustomerUrl("/api/v1/customers/" + customer.getId());
                    return customerDTO;
                })
                .collect(Collectors.toList());
    }

    @Override
    public CustomerDTO getCustomerById(Long id) {
        CustomerDTO customer = customerMapper.customerToCustomerDTO(customerRepository.findById(id).orElse(null));
        if(customer != null)
            customer.setCustomerUrl("/api/v1/customers/" + customer.getId());
        return customer;
    }
}
