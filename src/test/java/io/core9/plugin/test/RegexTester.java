package io.core9.plugin.test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexTester {

    public static void main(String[] args) {
        String regex = "/pet/[0-9]/owner";
        String text = "/pet/1/owner";
        System.out.println(IsMatch(text,regex));
}

    private static boolean IsMatch(String s, String pattern) {
        try {
            Pattern patt = Pattern.compile(pattern);
            Matcher matcher = patt.matcher(s);
            return matcher.matches();
        } catch (RuntimeException e) {
        return false;
    }       
}   
}