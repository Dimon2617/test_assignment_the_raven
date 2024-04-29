package org.example.test_assignment.customer.exceptions;

public class PhoneNotValidException extends BaseException {

    public PhoneNotValidException(String message) {
        super(message, "Conflict", 409);
    }

}
