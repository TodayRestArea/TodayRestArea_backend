package com.todayrestarea.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidationRegex {

    public static boolean isRegexDate(String target) {
        String regex = "/^\\d{4}-(0[1-9]|1[012])-(0[1-9]|[12][0-9]|3[01])$/";
        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(target);
        return matcher.find();
    }

    public static boolean isRegexYearMonth(String target) {
        String regex = "\\d{4}-(0[1-9]|1[012])-(0[1-9]|[12][0-9]|3[01])\n";
        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(target);
        return matcher.find();
    }
}