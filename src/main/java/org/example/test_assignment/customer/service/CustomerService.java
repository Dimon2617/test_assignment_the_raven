package org.example.test_assignment.customer.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.test_assignment.customer.dto.CustomerDTO;
import org.example.test_assignment.customer.entity.CustomerEntity;
import org.example.test_assignment.customer.exceptions.CustomerNotActiveException;
import org.example.test_assignment.customer.exceptions.EmailIsTakenException;
import org.example.test_assignment.customer.exceptions.CustomerNotExistsException;
import org.example.test_assignment.customer.repository.CustomerRepository;
import org.example.test_assignment.customer.request.CustomerRequest;
import org.example.test_assignment.customer.service.mapper.CustomerMapper;
import org.example.test_assignment.utils.EmailUtils;
import org.example.test_assignment.utils.FullNameUtils;
import org.example.test_assignment.utils.PhoneUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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

    public CustomerDTO createCustomer(CustomerRequest customer) {
        EmailUtils.validateEmail(customer.getEmail());
        PhoneUtils.validateEmail(customer.getPhone());
        FullNameUtils.validateEmail(customer.getFull_name());
        validateEmailTaken(customer.getEmail());

        CustomerEntity customerEntity = customerMapper.toEntity(customer);
        customerRepository.save(customerEntity);

        return customerMapper.toDTO(customerEntity);
    }

    @Transactional
    public CustomerDTO updateCustomer(Long userId, CustomerRequest customer) {
        CustomerEntity customerEntity = customerRepository.findById(userId)
                .orElseThrow(() -> new CustomerNotExistsException("User by id " + userId + " not found"));

        PhoneUtils.validateEmail(customer.getPhone());
        EmailUtils.validateEmail(customer.getEmail());
        FullNameUtils.validateEmail(customer.getFull_name());
        validateEmailTaken(customer.getEmail());

        customerEntity.setFull_name(customer.getFull_name());
        customerEntity.setEmail(customer.getEmail());
        customerEntity.setPhone(customer.getPhone());
        customerEntity.setUpdated(LocalDate.now());
        customerRepository.save(customerEntity);

        return customerMapper.toDTO(customerEntity);
    }

    @Transactional
    public void deleteCustomer(Long userId) {
        CustomerEntity customerEntity = customerRepository.findById(userId)
                .orElseThrow(() -> new CustomerNotExistsException("User by id " + userId + " not found"));

        customerEntity.set_active(false);
        customerRepository.save(customerEntity);
    }

    private void validateEmailTaken(String email) {
        if (customerRepository.existsByEmail(email)) {
            throw new EmailIsTakenException("User with email " + email + " already exists");
        }
    }

    public CustomerDTO getCustomer(Long id) {
        CustomerEntity customerEntity = customerRepository.findById(id)
                .orElseThrow(() -> new CustomerNotExistsException("Customer with id " + id + " not found"));
        if(customerEntity.is_active()){
            return customerMapper.toDTO(customerEntity);
        }
        throw new CustomerNotActiveException("Customer with id " + id + " not active");
    }

}
