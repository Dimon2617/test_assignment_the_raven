package org.example.test_assignment.utils;

import org.apache.commons.lang3.StringUtils;
import org.example.test_assignment.customer.exceptions.EmailNotValidException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FullNameUtils {
    private static final Pattern FULLNAME_PATTERN;

    static {
        final String FULLNAME_PATTERN_REGEX = "^\\s*(?=.{2,50}$)[A-Za-z\\s]+$";
        FULLNAME_PATTERN = Pattern.compile(FULLNAME_PATTERN_REGEX);
    }

    public static void validateEmail(String email) {
        if (StringUtils.isBlank(email)) {
            throw new EmailNotValidException("Full name is null or empty");
        }
        Matcher matcher = FULLNAME_PATTERN.matcher(email);
        if (!matcher.matches()) {
            throw new EmailNotValidException("Invalid full name");
        }
    }
}
