package org.example.test_assignment.utils;

import org.apache.commons.lang3.StringUtils;
import org.example.test_assignment.customer.exceptions.EmailNotValidException;
import org.example.test_assignment.customer.exceptions.PhoneNotValidException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PhoneUtils {

    private static final Pattern PHONE_PATTERN;

    static {
        final String PHONE_PATTERN_REGEX = "^\\+[0-9]{1,13}[0-9]$";
        PHONE_PATTERN = Pattern.compile(PHONE_PATTERN_REGEX);
    }

    public static void validateEmail(String email) {
        if (StringUtils.isBlank(email)) {
            throw new PhoneNotValidException("Phone is null or empty");
        }
        Matcher matcher = PHONE_PATTERN.matcher(email);
        if (!matcher.matches()) {
            throw new PhoneNotValidException("Invalid phone");
        }
    }
}
