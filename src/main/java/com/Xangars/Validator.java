package com.Xangars;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validator {

    //mobile validation

    public static boolean validateMobileNumber(String s)
    {
        if (s == null) return false;
        Pattern p = Pattern.compile("[0-9]{10}");
        Matcher m = p.matcher(s);
        return (m.find() && m.group().equals(s));

    }

    //Email Address Validation

    public static boolean validateEmailAddress(String email)
    {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";

        Pattern pat = Pattern.compile(emailRegex);
        if (email == null)
            return false;
        return pat.matcher(email).matches();
    }

}
