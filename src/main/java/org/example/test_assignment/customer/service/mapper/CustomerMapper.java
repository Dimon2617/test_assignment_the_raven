package org.example.test_assignment.customer.service.mapper;

import org.example.test_assignment.customer.dto.CustomerDTO;
import org.example.test_assignment.customer.entity.CustomerEntity;
import org.example.test_assignment.customer.request.CustomerRequest;
import org.springframework.stereotype.Component;

@Component
public class CustomerMapper {

    public CustomerEntity toEntity(CustomerRequest customerDTO) {
        return CustomerEntity.builder()
                .fullName(customerDTO.getFullName())
                .email(customerDTO.getEmail())
                .phone(customerDTO.getPhone())
                .build();
    }

    public CustomerDTO toDTO(CustomerEntity customerEntity) {
        return CustomerDTO.builder()
                .id(customerEntity.getId())
                .fullName(customerEntity.getFullName())
                .email(customerEntity.getEmail())
                .phone(customerEntity.getPhone())
                .build();
    }

}
