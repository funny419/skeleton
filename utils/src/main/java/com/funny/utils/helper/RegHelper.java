package com.funny.utils.helper;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class RegHelper {
    public static final String REG_ALNUM = "\\p{Alnum}";
    public static final String REG_ALPHA = "\\p{Alpha}";
    public static final String REG_ASCII = "\\p{ASCII}";
    public static final String REG_BLANK = "\\p{Blank}";
    public static final String REG_CNTRL = "\\p{Cntrl}";
    public static final String REG_DIGITS = "\\p{Digit}";
    public static final String REG_GRAPH = "\\p{Graph}";
    public static final String REG_LOWER = "\\p{Lower}";
    public static final String REG_PRINT = "\\p{Print}";
    public static final String REG_PUNCT = "\\p{Punct}";
    public static final String REG_SPACE = "\\p{Space}";
    public static final String REG_UPPER = "\\p{Upper}";
    public static final String REG_XDIGIT = "\\p{XDigit}";
    public static final String REG_SPACE_LINE = "\\n\\s*\\r";
    public static final String REG_SPACE_POINT = "^\\s*|\\s*$";
    public static final String REG_HTML = "<(\\S*?)[^>]*>.*?</\\1>|<.*? />";
    public static final String REG_EMAIL = "^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$";
    public static final String REG_FIXED_TELEPHONE = "\\d{3}-\\d{8}|\\d{4}-\\d{7}";
    public static final String REG_POSTALCODE = "[1-9]\\d{5}(?!\\d)";
    public static final String REG_IDENTIFICATION_CARD = "\\d{15}|\\d{18}";
    public static final String REG_URL = "^http://([w-]+.)+[w-]+(/[w-./?%&=]*)?$";
    public static final String REG_MOBILE_TELEPHONE = "^(13[0-9]|14[5|7]|15[0|1|2|3|5|6|7|8|9]|18[0|1|2|3|5|6|7|8|9])\\d{8}$";
    public static final String REG_LEGAL_ACCOUNT = "^[a-zA-Z][a-zA-Z0-9_]{4,15}$";
    public static final String REG_IP = "((2[0-4]\\d|25[0-5]|[01]?\\d\\d?)\\.){3}(2[0-4]\\d|25[0-5]|[01]?\\d\\d?)";

    private static final Pattern numericPattern = Pattern.compile("^[0-9\\-]+$");
    private static final Pattern numericStringPattern = Pattern.compile("^[0-9\\-\\-]+$");
    private static final Pattern floatNumericPattern = Pattern.compile("^[0-9\\-\\.]+$");
    private static final Pattern abcPattern = Pattern.compile("^[a-z|A-Z]+$");




    public static boolean isNumeric(String src) {
        boolean value = false;
        if (src != null && src.length() > 0) {
            Matcher m = numericPattern.matcher(src);
            if (m.find()) {
                value = true;
            }
        }

        return value;
    }


    public static boolean isABC(String src) {
        boolean value = false;
        if (src != null && src.length() > 0) {
            Matcher m = abcPattern.matcher(src);
            if (m.find()) {
                value = true;
            }
        }

        return value;
    }


    public static boolean isFloatNumeric(String src) {
        boolean value = false;
        if (src != null && src.length() > 0) {
            Matcher m = floatNumericPattern.matcher(src);
            if (m.find()) {
                value = true;
            }
        }

        return value;
    }


    public static boolean isMatche(String str, String reg) {
        Pattern pattern = Pattern.compile(reg);
        Matcher isNum = pattern.matcher(str);
        return isNum.matches();
    }


    public static int countSubStrReg(String str, String reg) {
        Pattern p = Pattern.compile(reg);
        Matcher m = p.matcher(str);

        int i = 0;
        while (m.find()) {
            i++;
        }

        return i;
    }


    public static boolean isEmail(String email) {
        if (email == null || email.length() < 1 || email.length() > 256) {
            return false;
        }

        Pattern pattern = Pattern.compile(REG_EMAIL);
        return pattern.matcher(email).matches();
    }
}
