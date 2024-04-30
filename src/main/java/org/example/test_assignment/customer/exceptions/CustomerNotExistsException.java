package org.example.test_assignment.customer.exceptions;

public class CustomerNotExistsException extends BaseException {

    public CustomerNotExistsException(String message) {
        super(message, "Not Found", 404);
    }

}
