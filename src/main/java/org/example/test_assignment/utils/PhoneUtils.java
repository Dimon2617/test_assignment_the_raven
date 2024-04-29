package org.example.test_assignment.utils;

import org.example.test_assignment.customer.exceptions.PhoneNotValidException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PhoneUtils {

    private static final Pattern PHONE_PATTERN;

    static {
        final String PHONE_PATTERN_REGEX = "^\\+[0-9]{5,13}[0-9]$";
        PHONE_PATTERN = Pattern.compile(PHONE_PATTERN_REGEX);
    }

    public static void validatePhone(String phone) {
        Matcher matcher = PHONE_PATTERN.matcher(phone);
        if (!matcher.matches()) {
            throw new PhoneNotValidException("Invalid phone");
        }
    }
}
