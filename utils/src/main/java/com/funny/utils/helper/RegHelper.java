package com.funny.utils.helper;

import org.apache.commons.lang3.StringUtils;

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
    public static final String REG_URL = "^http://([w-]+.)+[w-]+(/[w-./?%&=]*)?$";
    public static final String REG_IP = "((2[0-4]\\d|25[0-5]|[01]?\\d\\d?)\\.){3}(2[0-4]\\d|25[0-5]|[01]?\\d\\d?)";
    public static final String REG_IP6 = "(([0-9a-fA-F]{1,4}:){7,7}[0-9a-fA-F]{1,4}|([0-9a-fA-F]{1,4}:){1,7}:|([0-9a-fA-F]{1,4}:){1,6}:[0-9a-fA-F]{1,4}|([0-9a-fA-F]{1,4}:){1,5}(:[0-9a-fA-F]{1,4}){1,2}|([0-9a-fA-F]{1,4}:){1,4}(:[0-9a-fA-F]{1,4}){1,3}|([0-9a-fA-F]{1,4}:){1,3}(:[0-9a-fA-F]{1,4}){1,4}|([0-9a-fA-F]{1,4}:){1,2}(:[0-9a-fA-F]{1,4}){1,5}|[0-9a-fA-F]{1,4}:((:[0-9a-fA-F]{1,4}){1,6})|:((:[0-9a-fA-F]{1,4}){1,7}|:)|fe80:(:[0-9a-fA-F]{0,4}){0,4}%[0-9a-zA-Z]{1,}|::(ffff(:0{1,4}){0,1}:){0,1}((25[0-5]|(2[0-4]|1{0,1}[0-9]){0,1}[0-9])\\.){3,3}(25[0-5]|(2[0-4]|1{0,1}[0-9]){0,1}[0-9])|([0-9a-fA-F]{1,4}:){1,4}:((25[0-5]|(2[0-4]|1{0,1}[0-9]){0,1}[0-9])\\.){3,3}(25[0-5]|(2[0-4]|1{0,1}[0-9]){0,1}[0-9]))";

    public static final Pattern numericPattern = Pattern.compile("^[0-9\\-]+$");
    public static final Pattern numericStringPattern = Pattern.compile("^[0-9\\-\\-]+$");
    public static final Pattern floatNumericPattern = Pattern.compile("^[0-9\\-\\.]+$");
    public static final Pattern abcPattern = Pattern.compile("^[a-z|A-Z]+$");
    public static final Pattern emojiPattern = Pattern.compile("[\\uD83C-\\uDBFF]+|[\\uDC00-\\uDFFF]+");




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


    /**
     * method 내 에서 pattern을 매번 실행하는게 성능상 이점이 없을것 같음
     * 이렇게 isMatch를 사용하는곳도 별로 없을것 같고
     *
     * @param str
     * @param reg
     * @return
     */
    public static boolean isMatche(String str,String reg) {
        Pattern pattern = Pattern.compile(reg);
        Matcher matcher = pattern.matcher(str);
        return matcher.matches();
    }


    public static int countSubStrReg(String str,String reg) {
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


    public static boolean isIP6(String ipAddress) {
        if (StringUtils.isEmpty(ipAddress)) {
            return false;
        }

        return isMatche(ipAddress,REG_IP6);
    }


    public static boolean isIP(String ipAddress) {
        if (StringUtils.isEmpty(ipAddress)) {
            return false;
        }

        return isMatche(ipAddress,REG_IP);
    }


    public static boolean isEmojiString(String input) {
        return emojiPattern.matcher(input).find();
    }
}
