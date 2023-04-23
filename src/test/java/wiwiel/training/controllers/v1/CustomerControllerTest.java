package wiwiel.training.controllers.v1;

import wiwiel.training.api.v1.model.CustomerDTO;
import wiwiel.training.services.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class CustomerControllerTest {
    public static final String FIRSTNAME = "Joe";
    public static final String LASTNAME = "Doe";
    public static final long ID = 1L;

    @Mock
    CustomerService customerService;

    @InjectMocks
    CustomerController customerController;

    MockMvc mockMvc;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(customerController).build();
    }

    @Test
    public void testListCategories() throws Exception {
        CustomerDTO customer1 = new CustomerDTO();
        customer1.setId(ID);
        customer1.setFirstname(FIRSTNAME);
        customer1.setLastname(LASTNAME);
        customer1.setCustomerUrl("/api/v1/customer/1");

        CustomerDTO customer2 = new CustomerDTO();
        customer2.setId(2L);
        customer2.setFirstname("Bob");
        customer2.setLastname("Bobby");
        customer1.setCustomerUrl("/api/v1/customer/2");

        List<CustomerDTO> customers = Arrays.asList(customer1, customer2);

        when(customerService.getAllCustomers()).thenReturn(customers);

        mockMvc.perform(get("/api/v1/customers/")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customers", hasSize(2)));
    }

    @Test
    public void testGetByIdCustomers() throws Exception {
        CustomerDTO customer1 = new CustomerDTO();
        customer1.setId(ID);
        customer1.setFirstname(FIRSTNAME);
        customer1.setLastname(LASTNAME);
        customer1.setCustomerUrl("/api/v1/customer/1");

        when(customerService.getCustomerById(anyLong())).thenReturn(customer1);

        mockMvc.perform(get("/api/v1/customers/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstname", equalTo(FIRSTNAME)));
    }
}