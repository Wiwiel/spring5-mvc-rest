package wiwiel.training.api.v1.mapper;

import wiwiel.training.api.v1.model.CustomerDTO;
import wiwiel.training.domain.Customer;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CustomerMapperTest {

    public static final String FIRSTNAME = "Joe";
    public static final String LASTNAME = "Doe";
    public static final long ID = 1L;
    CustomerMapper customerMapper = CustomerMapper.INSTANCE;

    @Test
    void customerToCustomerDTO() {
        //given
        Customer customer = new Customer();
        customer.setFirstName(FIRSTNAME);
        customer.setLastName(LASTNAME);
        customer.setId(ID);

        //when
        CustomerDTO customerDTO = customerMapper.customerToCustomerDTO(customer);

        //then
        assertEquals(ID, customerDTO.getId());
        assertEquals(FIRSTNAME, customerDTO.getFirstname());
        assertEquals(LASTNAME, customerDTO.getLastname());
    }
}