package org.example.test_assignment.customer.exceptions;

public class EmailNotValidException extends BaseException {

    public EmailNotValidException(String message) {
        super(message, "Conflict", 409);
    }

}
