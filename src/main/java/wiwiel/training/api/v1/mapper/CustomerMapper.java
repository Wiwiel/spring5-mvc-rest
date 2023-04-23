package wiwiel.training.api.v1.mapper;

import wiwiel.training.api.v1.model.CustomerDTO;
import wiwiel.training.domain.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CustomerMapper {
    CustomerMapper INSTANCE = Mappers.getMapper(CustomerMapper.class);

    @Mapping(source = "firstName", target = "firstname")
    @Mapping(source = "lastName", target = "lastname")
    CustomerDTO customerToCustomerDTO(Customer customer);
}
