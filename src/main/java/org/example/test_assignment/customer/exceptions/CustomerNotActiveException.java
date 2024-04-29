package org.example.test_assignment.customer.exceptions;

public class CustomerNotActiveException extends BaseException {

    public CustomerNotActiveException(String message) {
        super(message, "Not Found", 404);
    }

}
