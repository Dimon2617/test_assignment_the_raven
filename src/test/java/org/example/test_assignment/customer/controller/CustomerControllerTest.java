package org.example.test_assignment.customer.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.test_assignment.customer.dto.CustomerDTO;
import org.example.test_assignment.customer.helpers.CustomerHelper;
import org.example.test_assignment.customer.request.CustomerRequest;
import org.example.test_assignment.customer.service.CustomerService;
import org.example.test_assignment.customer.service.mapper.CustomerMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc(addFilters = false)
public class CustomerControllerTest {

    @MockBean
    CustomerMapper customerMapper;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CustomerHelper customerHelper;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @MockBean
    private CustomerService customerService;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void testGetAllCustomers() throws Exception {
        CustomerDTO customerDTO = customerHelper.createCustomerDTO();
        List<CustomerDTO> customerDTOList = new ArrayList<>();
        customerDTOList.add(customerDTO);

        when(customerService.getAllCustomers()).thenReturn(customerDTOList);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/customers")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(customerDTO.getId()))
                .andExpect(jsonPath("$[0].fullName").value(customerDTO.getFullName()))
                .andExpect(jsonPath("$[0].email").value(customerDTO.getEmail()))
                .andExpect(jsonPath("$[0].phone").value(customerDTO.getPhone()));
    }

    @Test
    public void testGetCustomer() throws Exception {
        CustomerDTO customerDTO = customerHelper.createCustomerDTO();

        when(customerService.getCustomer(1L)).thenReturn(customerDTO);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/customers/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(customerDTO.getId()))
                .andExpect(jsonPath("$[0].fullName").value(customerDTO.getFullName()))
                .andExpect(jsonPath("$[0].email").value(customerDTO.getEmail()))
                .andExpect(jsonPath("$[0].phone").value(customerDTO.getPhone()));
    }

    @Test
    public void testCreateCustomer() throws Exception {
        CustomerRequest customerDTO = customerHelper.createCustomerRequest();

        mockMvc.perform(MockMvcRequestBuilders.post("/api/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(customerDTO)))
                .andExpect(status().isOk()).andReturn();
    }

    @Test
    public void testUpdateCustomer() throws Exception {
        CustomerRequest customerDTO = customerHelper.createCustomerRequest();

        mockMvc.perform(MockMvcRequestBuilders.put("/api/customers/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(customerDTO)))
                .andExpect(status().isOk()).andReturn();

        ArgumentCaptor<CustomerRequest> customerCaptor = ArgumentCaptor.forClass(CustomerRequest.class);
        verify(customerService).updateCustomer(eq(1L), customerCaptor.capture());
        CustomerRequest capturedDTO = customerCaptor.getValue();

        assertNotNull(capturedDTO);
    }

    @Test
    public void testDeleteCustomer() throws Exception {
        CustomerRequest customerDTO = customerHelper.createCustomerRequest();

        customerService.createCustomer(customerDTO);

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/customers/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

}
