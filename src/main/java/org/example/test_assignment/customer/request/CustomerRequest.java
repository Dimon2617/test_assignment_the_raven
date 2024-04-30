package org.example.test_assignment.customer.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CustomerRequest {
    private String fullName;
    private String email;
    private String phone;
}
