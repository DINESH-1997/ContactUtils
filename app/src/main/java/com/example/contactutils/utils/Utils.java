package com.example.contactutils.utils;

public class Utils {

    public static String formatNumber(String number) {
        if(!(number == null && number.isEmpty())){
            return number.replace("+91", "").replace(" ", "");
        }
        return "";
    }
}
