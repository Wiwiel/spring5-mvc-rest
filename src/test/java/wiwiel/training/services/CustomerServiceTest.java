package wiwiel.training.services;

import wiwiel.training.api.v1.mapper.CustomerMapper;
import wiwiel.training.api.v1.model.CustomerDTO;
import wiwiel.training.controllers.v1.CustomerController;
import wiwiel.training.domain.Customer;
import wiwiel.training.repositories.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class CustomerServiceTest {
    public static final String FIRSTNAME = "Joe";
    public static final String LASTNAME = "Doe";
    public static final long ID = 1L;

    @Mock
    CustomerRepository customerRepository;

    CustomerService customerService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        customerService = new CustomerServiceImpl(customerRepository, CustomerMapper.INSTANCE);
    }

    @Test
    void getAllCustomers() {
        List<Customer> customers = Arrays.asList(new Customer(), new Customer(), new Customer());
        when(customerRepository.findAll()).thenReturn(customers);

        List<CustomerDTO> customerDTOS = customerService.getAllCustomers();

        assertEquals(3, customerDTOS.size());
    }

    @Test
    void getCustomerById() {
        Customer customer = new Customer();
        customer.setId(ID);
        customer.setFirstName(FIRSTNAME);
        customer.setLastName(LASTNAME);

        when(customerRepository.findById(anyLong())).thenReturn(Optional.of(customer));

        CustomerDTO customerDTO = customerService.getCustomerById(ID);

        assertEquals(ID, customerDTO.getId());
        assertEquals(FIRSTNAME, customerDTO.getFirstname());
        assertEquals(LASTNAME, customerDTO.getLastname());
        assertEquals(CustomerController.BASE_URL + "1", customerDTO.getCustomerUrl());
    }


    @Test
    void createNewCustomer() {
        CustomerDTO customer = new CustomerDTO();
        customer.setFirstname(FIRSTNAME);
        customer.setLastname(LASTNAME);

        Customer savedCustomer = new Customer();
        savedCustomer.setFirstName(customer.getFirstname());
        savedCustomer.setLastName(customer.getLastname());
        savedCustomer.setId(ID);

        when(customerRepository.save(any(Customer.class))).thenReturn(savedCustomer);

        CustomerDTO savedDto = customerService.createNewCustomer(customer);

        assertEquals(FIRSTNAME, savedDto.getFirstname());
        assertEquals(CustomerController.BASE_URL + "1", savedDto.getCustomerUrl());
    }

    @Test
    public void saveCustomerByDTO() throws Exception {

        //given
        CustomerDTO customer = new CustomerDTO();
        customer.setFirstname(FIRSTNAME);
        customer.setLastname(LASTNAME);

        Customer savedCustomer = new Customer();
        savedCustomer.setFirstName(customer.getFirstname());
        savedCustomer.setLastName(customer.getLastname());
        savedCustomer.setId(ID);

        when(customerRepository.save(any(Customer.class))).thenReturn(savedCustomer);

        //when
        CustomerDTO savedDto = customerService.saveCustomerByDTO(1L, customer);

        //then
        assertEquals(customer.getFirstname(), savedDto.getFirstname());
        assertEquals(CustomerController.BASE_URL + "1", savedDto.getCustomerUrl());
    }

    @Test
    void deleteCustomerById() {
        customerService.deleteCustomerById(ID);
        verify(customerRepository, times(1)).deleteById(anyLong());
    }
}