package com.perpus.go.util;

import java.util.regex.Pattern;

public class Util {
    // src: https://www.baeldung.com/java-email-validation-regex
    public static boolean patternNotMatches(String str, String regexPattern) {
        return !Pattern.compile(regexPattern)
                .matcher(str)
                .matches();
    }
}
