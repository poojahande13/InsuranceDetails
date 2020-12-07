package com.Xangars;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GenerateOTP {

    static int OTP_LENGTH = 6;
    static  int EXPIRE_TIME = 5; // in Minutes;

        // A Function to generate a unique OTP everytime
        static String generateOTP(int len)
        {


            // All possible characters of my OTP
            /*String str = "abcdefghijklmnopqrstuvwxyzABCD"
                    +"EFGHIJKLMNOPQRSTUVWXYZ0123456789";*/

            String str ="0123456789";
            int n = str.length();

            // String to hold my OTP
            String OTP="";

            for (int i = 1; i <= len; i++)
                OTP += (str.charAt((int) ((Math.random()*10) % n)));

            return(OTP);
        }

    public static String getDateTime() {

        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyMMdd");
        SimpleDateFormat timeFormatter = new SimpleDateFormat("hhmmss");
        String strDate = formatter.format(date);
        timeFormatter.setTimeZone(TimeZone.getTimeZone("IST"));
        String Time = timeFormatter.format(date);
        String datetime = strDate + "" + Time;
        return datetime;

    }

    public static String ExpiryTime() {

        Date date = new Date(System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(EXPIRE_TIME));
        SimpleDateFormat formatter = new SimpleDateFormat("yyMMdd");
        SimpleDateFormat timeFormatter = new SimpleDateFormat("hhmmss");
        String strDate = formatter.format(date);
        timeFormatter.setTimeZone(TimeZone.getTimeZone("IST"));
        String Time = timeFormatter.format(date);
        String datetime = strDate + "" + Time;
        return datetime;

    }






}


