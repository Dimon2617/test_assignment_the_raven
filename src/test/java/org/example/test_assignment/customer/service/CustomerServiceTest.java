package org.example.test_assignment.customer.service;

import org.example.test_assignment.customer.dto.CustomerDTO;
import org.example.test_assignment.customer.entity.CustomerEntity;
import org.example.test_assignment.customer.exceptions.*;
import org.example.test_assignment.customer.helpers.CustomerHelper;
import org.example.test_assignment.customer.repository.CustomerRepository;
import org.example.test_assignment.customer.request.CustomerRequest;
import org.example.test_assignment.customer.service.mapper.CustomerMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class CustomerServiceTest {

    @Autowired
    private CustomerHelper customerHelper;

    @Autowired
    private CustomerService customerService;

    @MockBean
    private CustomerRepository customerRepository;

    @Test
    public void testGetAllCustomersSuccess() {
        CustomerEntity customerEntity1 = customerHelper.createCustomer();
        CustomerEntity customerEntity2 = customerHelper.createCustomer();
        List<CustomerEntity> users = Arrays.asList(customerEntity1, customerEntity2);

        when(customerRepository.findAllActiveCustomers()).thenReturn(users);
        customerService.getAllCustomers();

        assertNotNull(users);
        assertThat(users.size()).isEqualTo(2);
        assertEquals(customerEntity1, users.get(0));
        assertEquals(customerEntity2, users.get(1));
    }

    @Test
    public void testGetCustomer() {
        CustomerEntity customerEntity = customerHelper.createCustomer();
        customerEntity.setActive(true);

        when(customerRepository.findById(customerEntity.getId())).thenReturn(Optional.of(customerEntity));

        CustomerDTO customer = customerService.getCustomer(customerEntity.getId());
        assertEquals(customer.getFullName(), customerEntity.getFullName());
        assertEquals(customer.getPhone(), customerEntity.getPhone());
        assertEquals(customer.getEmail(), customerEntity.getEmail());
    }

    @Test
    public void testGetCustomerNotActive() {
        CustomerEntity customerEntity = customerHelper.createCustomer();
        customerEntity.setActive(false);

        when(customerRepository.findById(customerEntity.getId())).thenReturn(Optional.of(customerEntity));

        assertThatThrownBy(() -> customerService.getCustomer(customerEntity.getId()))
                .isInstanceOf(CustomerNotActiveException.class)
                .hasMessageContaining("Customer with id " + customerEntity.getId() + " not active");
    }



    @Test
    public void testCreateCustomerSuccess() {
        CustomerRequest customerDTO = customerHelper.createCustomerRequest();

        customerService.createCustomer(customerDTO);

        ArgumentCaptor<CustomerEntity> userEntityArgumentCaptor = ArgumentCaptor.forClass(CustomerEntity.class);

        verify(customerRepository, times(1)).save(userEntityArgumentCaptor.capture());

        CustomerEntity capturedUser = userEntityArgumentCaptor.getValue();

        assertEquals(capturedUser.getFullName(), customerDTO.getFullName());
        assertEquals(capturedUser.getPhone(), customerDTO.getPhone());
        assertEquals(capturedUser.getEmail(), customerDTO.getEmail());
    }

    @Test
    public void testCreateCustomerInvalidPhone() {
        CustomerRequest customerDTO = customerHelper.createCustomerRequest();
        customerDTO.setPhone("123456789");

        assertThatThrownBy(() -> customerService.createCustomer(customerDTO))
                .isInstanceOf(PhoneNotValidException.class)
                .hasMessageContaining("Invalid phone");
    }

    @Test
    public void testCreateCustomerInvalidFullName() {
        CustomerRequest customerDTO = customerHelper.createCustomerRequest();
        customerDTO.setFullName("d");

        assertThatThrownBy(() -> customerService.createCustomer(customerDTO))
                .isInstanceOf(EmailNotValidException.class)
                .hasMessageContaining("Invalid full name");
    }

    @Test
    public void testCreateCustomerNullFullName() {
        CustomerRequest customerDTO = customerHelper.createCustomerRequest();
        customerDTO.setFullName(null);

        assertThatThrownBy(() -> customerService.createCustomer(customerDTO))
                .isInstanceOf(EmailNotValidException.class)
                .hasMessageContaining("Full name is null or empty");

        customerDTO.setFullName("");
        assertThatThrownBy(() -> customerService.createCustomer(customerDTO))
                .isInstanceOf(EmailNotValidException.class)
                .hasMessageContaining("Full name is null or empty");
    }

    @Test
    public void testCreateCustomerEmailIsNullOrEmpty() {
        CustomerRequest customerDTO = customerHelper.createCustomerRequest();
        customerDTO.setEmail(null);

        assertThatThrownBy(() -> customerService.createCustomer(customerDTO))
                .isInstanceOf(EmailNotValidException.class)
                .hasMessageContaining("Email is null or empty");

        customerDTO.setEmail("");
        assertThatThrownBy(() -> customerService.createCustomer(customerDTO))
                .isInstanceOf(EmailNotValidException.class)
                .hasMessageContaining("Email is null or empty");
    }

    @Test
    public void testCreateCustomerInvalidEmail() {
        CustomerRequest customerDTO = customerHelper.createCustomerRequest();
        customerDTO.setEmail("test@test..com");

        assertThatThrownBy(() -> customerService.createCustomer(customerDTO))
                .isInstanceOf(EmailNotValidException.class)
                .hasMessageContaining("Invalid email");

    }

    @Test
    public void testCreateCustomerEmailIsTaken() {
        CustomerRequest customerDTO = customerHelper.createCustomerRequest();

        when(customerRepository.existsByEmail(customerDTO.getEmail())).thenReturn(true);

        assertThatThrownBy(() -> customerService.createCustomer(customerDTO))
                .isInstanceOf(EmailIsTakenException.class)
                .hasMessageContaining("Customer with email " + customerDTO.getEmail() + " already exists");

    }

    @Test
    public void testUpdateCustomer() {
        CustomerRequest customerRequest = customerHelper.createCustomerRequest();
        CustomerEntity existingCustomer = customerHelper.createCustomer();

        when(customerRepository.findById(existingCustomer.getId())).thenReturn(Optional.of(existingCustomer));

        CustomerDTO customerDTO = customerService.updateCustomer(existingCustomer.getId(), customerRequest);

        verify(customerRepository, times(1)).findById(existingCustomer.getId());
        verify(customerRepository, times(1)).save(existingCustomer);

        assertEquals(customerDTO.getFullName(), existingCustomer.getFullName());
        assertEquals(customerDTO.getPhone(), existingCustomer.getPhone());
        assertEquals(customerDTO.getEmail(), existingCustomer.getEmail());
    }

    @Test
    public void testDeleteCustomerSuccess() {
        CustomerEntity existingUser = customerHelper.createCustomer();

        when(customerRepository.findById(existingUser.getId())).thenReturn(Optional.of(existingUser));

        customerService.deleteCustomer(existingUser.getId());

        assertFalse(existingUser.isActive());
    }

    @Test
    public void testDeleteCustomerCollapse() {
        Long userId = 1L;

        assertThatThrownBy(() -> customerService.deleteCustomer(userId))
                .isInstanceOf(CustomerNotExistsException.class)
                .hasMessageContaining("Customer by id " + userId + " not found");
    }

}
