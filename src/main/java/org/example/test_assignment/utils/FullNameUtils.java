package org.example.test_assignment.utils;

import org.apache.commons.lang3.StringUtils;
import org.example.test_assignment.customer.exceptions.EmailNotValidException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FullNameUtils {

    private static final Pattern FULL_NAME_PATTERN;

    static {
        final String FULL_NAME_PATTERN_REGEX = "^\\s*(?=.{2,50}$)[A-Za-z\\s]+$";
        FULL_NAME_PATTERN = Pattern.compile(FULL_NAME_PATTERN_REGEX);
    }

    public static void validateFullName(String fullName) {
        if (StringUtils.isBlank(fullName)) {
            throw new EmailNotValidException("Full name is null or empty");
        }
        Matcher matcher = FULL_NAME_PATTERN.matcher(fullName);
        if (!matcher.matches()) {
            throw new EmailNotValidException("Invalid full name");
        }
    }

}
