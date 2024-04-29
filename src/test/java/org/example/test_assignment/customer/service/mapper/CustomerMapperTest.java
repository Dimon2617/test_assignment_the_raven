package org.example.test_assignment.customer.service.mapper;

import org.example.test_assignment.customer.dto.CustomerDTO;
import org.example.test_assignment.customer.entity.CustomerEntity;
import org.example.test_assignment.customer.helpers.CustomerHelper;
import org.example.test_assignment.customer.request.CustomerRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class CustomerMapperTest {

    @Autowired
    private CustomerHelper customerHelper;

    @Autowired
    private CustomerMapper customerMapper;

    @Test
    public void testToEntity() {
        CustomerRequest customerDTO = customerHelper.createCustomerRequest();

        CustomerEntity customerEntity = customerMapper.toEntity(customerDTO);

        assertNotNull(customerEntity);
        assertEquals(customerDTO.getFullName(), customerEntity.getFullName());
        assertEquals(customerDTO.getPhone(), customerEntity.getPhone());
        assertEquals(customerDTO.getEmail(), customerEntity.getEmail());
    }

    @Test
    public void testToDTO() {
        CustomerEntity customerEntity = customerHelper.createCustomer();

        CustomerDTO customerDTO = customerMapper.toDTO(customerEntity);

        assertNotNull(customerDTO);
        assertEquals(customerEntity.getId(), customerDTO.getId());
        assertEquals(customerEntity.getFullName(), customerDTO.getFullName());
        assertEquals(customerEntity.getEmail(), customerDTO.getEmail());
        assertEquals(customerEntity.getPhone(), customerDTO.getPhone());
    }

}
