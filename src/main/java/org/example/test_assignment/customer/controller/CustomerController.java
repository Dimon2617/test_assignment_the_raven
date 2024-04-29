package org.example.test_assignment.customer.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.test_assignment.customer.dto.CustomerDTO;
import org.example.test_assignment.customer.request.CustomerRequest;
import org.example.test_assignment.customer.service.CustomerService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @GetMapping
    public List<CustomerDTO> getAllCustomers() {
        return customerService.getAllCustomers();
    }

    @GetMapping("{id}")
    public CustomerDTO getCustomer(@PathVariable Long id) {
        return customerService.getCustomer(id);
    }

    @PostMapping
    public CustomerDTO createCustomer(@Valid @RequestBody CustomerRequest customer) {
        return customerService.createCustomer(customer);
    }

    @PutMapping("/{id}")
    public CustomerDTO updateCustomer(@PathVariable Long id, @RequestBody CustomerRequest customer) {
        return customerService.updateCustomer(id, customer);
    }

    @DeleteMapping("/{id}")
    public void deleteCustomer(@PathVariable Long id) {
        customerService.deleteCustomer(id);
    }

}
