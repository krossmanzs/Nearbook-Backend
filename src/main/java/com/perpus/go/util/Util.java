package com.perpus.go.util;

import java.util.regex.Pattern;

public class Util {
    // src: https://www.baeldung.com/java-email-validation-regex
    public static boolean patternNotMatches(String str, String regexPattern) {
        return !Pattern.compile(regexPattern)
                .matcher(str)
                .matches();
    }

    public static String generateVerificationCode() {
        return String.format("%06d", (int)Math.floor(Math.random()*(999999 + 1)+0));
    }
}
