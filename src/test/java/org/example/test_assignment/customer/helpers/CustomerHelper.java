package org.example.test_assignment.customer.helpers;

import org.example.test_assignment.customer.dto.CustomerDTO;
import org.example.test_assignment.customer.entity.CustomerEntity;
import org.example.test_assignment.customer.request.CustomerRequest;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class CustomerHelper {

    public CustomerEntity createCustomer() {
        return CustomerEntity.builder()
                .id(1L)
                .fullName("Steve Backer")
                .email("stevebacker@example.com")
                .created(System.currentTimeMillis())
                .phone("+3809876543210")
                .build();
    }

    public CustomerDTO createCustomerDTO() {
        return CustomerDTO.builder()
                .id(1L)
                .fullName("Bob Smith")
                .email("bobsmith@example.com")
                .phone("+3809876543210")
                .build();
    }

    public CustomerRequest createCustomerRequest() {
        return CustomerRequest.builder()
                .fullName("Bogdan Stetsuk")
                .email("bogdan@example.com")
                .phone("+3809876543210")
                .build();
    }

}
