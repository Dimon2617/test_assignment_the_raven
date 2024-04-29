package org.example.test_assignment.customer.service.mapper;

import org.example.test_assignment.customer.dto.CustomerDTO;
import org.example.test_assignment.customer.entity.CustomerEntity;
import org.example.test_assignment.customer.request.CustomerRequest;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class CustomerMapper {

    public CustomerEntity toEntity(CustomerRequest customerDTO) {
        return CustomerEntity.builder()
                .full_name(customerDTO.getFull_name())
                .created(LocalDate.now())
                .email(customerDTO.getEmail())
                .phone(customerDTO.getPhone())
                .is_active(true)
                .build();
    }

    public CustomerDTO toDTO(CustomerEntity customerEntity) {
        return CustomerDTO.builder()
                .id(customerEntity.getId())
                .full_name(customerEntity.getFull_name())
                .email(customerEntity.getEmail())
                .phone(customerEntity.getPhone())
                .build();
    }

}
