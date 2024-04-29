package org.example.test_assignment.customer.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.test_assignment.customer.dto.CustomerDTO;
import org.example.test_assignment.customer.entity.CustomerEntity;
import org.example.test_assignment.customer.exceptions.CustomerNotActiveException;
import org.example.test_assignment.customer.exceptions.CustomerNotExistsException;
import org.example.test_assignment.customer.exceptions.EmailIsTakenException;
import org.example.test_assignment.customer.repository.CustomerRepository;
import org.example.test_assignment.customer.request.CustomerRequest;
import org.example.test_assignment.customer.service.mapper.CustomerMapper;
import org.example.test_assignment.utils.EmailUtils;
import org.example.test_assignment.utils.FullNameUtils;
import org.example.test_assignment.utils.PhoneUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    public List<CustomerDTO> getAllCustomers() {
        return customerRepository.findAllActiveCustomers()
                .stream()
                .map(customerMapper::toDTO)
                .toList();
    }

    public CustomerDTO getCustomer(Long id) {
        CustomerEntity customerEntity = customerRepository.findById(id)
                .orElseThrow(() -> new CustomerNotExistsException("Customer with id " + id + " not found"));

        if (!customerEntity.isActive()) {
            throw new CustomerNotActiveException("Customer with id " + id + " not active");
        }

        return customerMapper.toDTO(customerEntity);
    }

    public CustomerDTO createCustomer(CustomerRequest customer) {
        EmailUtils.validateEmail(customer.getEmail());
        validateEmailTaken(customer.getEmail());
        PhoneUtils.validatePhone(customer.getPhone());
        FullNameUtils.validateFullName(customer.getFullName());

        CustomerEntity customerEntity = customerMapper.toEntity(customer);
        customerEntity.setActive(true);

        customerRepository.save(customerEntity);

        return customerMapper.toDTO(customerEntity);
    }

    @Transactional
    public CustomerDTO updateCustomer(Long id, CustomerRequest customer) {
        CustomerEntity customerEntity = customerRepository.findById(id)
                .orElseThrow(() -> new CustomerNotExistsException("Customer by id " + id + " not found"));

        PhoneUtils.validatePhone(customer.getPhone());
        FullNameUtils.validateFullName(customer.getFullName());

        customerEntity.setPhone(customer.getPhone());
        customerEntity.setFullName(customer.getFullName());

        customerRepository.save(customerEntity);

        return customerMapper.toDTO(customerEntity);
    }

    @Transactional
    public void deleteCustomer(Long id) {
        CustomerEntity customerEntity = customerRepository.findById(id)
                .orElseThrow(() -> new CustomerNotExistsException("Customer by id " + id + " not found"));

        customerEntity.setActive(false);
        customerRepository.save(customerEntity);
    }

    private void validateEmailTaken(String email) {
        if (customerRepository.existsByEmail(email)) {
            throw new EmailIsTakenException("Customer with email " + email + " already exists");
        }
    }

}
