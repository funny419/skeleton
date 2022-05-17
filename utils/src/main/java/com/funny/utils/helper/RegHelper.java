package com.funny.utils.helper;

import org.apache.commons.lang3.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class RegHelper {
    public static final Pattern ALNUM_PATTERN = Pattern.compile("\\p{Alnum}");
    public static final Pattern ALPHA_PATERN = Pattern.compile("\\p{Alpha}");
    public static final Pattern ASCII_PATTERN = Pattern.compile("\\p{ASCII}");
    public static final Pattern BLANK_PATTERN = Pattern.compile("\\p{Blank}");
    public static final Pattern CNTRL_PATTERN = Pattern.compile("\\p{Cntrl}");
    public static final Pattern DIGITS_PATTERN = Pattern.compile("\\p{Digit}");
    public static final Pattern GRAPH_PATTERN = Pattern.compile("\\p{Graph}");
    public static final Pattern LOWER_PATTERN = Pattern.compile("\\p{Lower}");
    public static final Pattern PRINT_PATTERN = Pattern.compile("\\p{Print}");
    public static final Pattern PUNCT_PATTERN = Pattern.compile("\\p{Punct}");
    public static final Pattern SPACE_PATTERN = Pattern.compile("\\p{Space}");
    public static final Pattern UPPER_PATTERN = Pattern.compile("\\p{Upper}");
    public static final Pattern XDIGIT_PATTERN = Pattern.compile("\\p{XDigit}");
    public static final Pattern SPACE_LINE_PATTERN = Pattern.compile("\\n\\s*\\r");
    public static final Pattern HTML_PATTERN = Pattern.compile("<(\\S*?)[^>]*>.*?</\\1>|<.*? />");
    public static final Pattern URL_PATTERN = Pattern.compile("^http://([w-]+.)+[w-]+(/[w-./?%&=]*)?$");
    public static final Pattern NUMBERIC_PATTERN = Pattern.compile("^[0-9\\-]+$");
    public static final Pattern IP_PATTERN = Pattern.compile("((2[0-4]\\d|25[0-5]|[01]?\\d\\d?)\\.){3}(2[0-4]\\d|25[0-5]|[01]?\\d\\d?)");
    public static final Pattern IP6_PATTERN = Pattern.compile("(([0-9a-fA-F]{1,4}:){7,7}[0-9a-fA-F]{1,4}|([0-9a-fA-F]{1,4}:){1,7}:|([0-9a-fA-F]{1,4}:){1,6}:[0-9a-fA-F]{1,4}|([0-9a-fA-F]{1,4}:){1,5}(:[0-9a-fA-F]{1,4}){1,2}|([0-9a-fA-F]{1,4}:){1,4}(:[0-9a-fA-F]{1,4}){1,3}|([0-9a-fA-F]{1,4}:){1,3}(:[0-9a-fA-F]{1,4}){1,4}|([0-9a-fA-F]{1,4}:){1,2}(:[0-9a-fA-F]{1,4}){1,5}|[0-9a-fA-F]{1,4}:((:[0-9a-fA-F]{1,4}){1,6})|:((:[0-9a-fA-F]{1,4}){1,7}|:)|fe80:(:[0-9a-fA-F]{0,4}){0,4}%[0-9a-zA-Z]{1,}|::(ffff(:0{1,4}){0,1}:){0,1}((25[0-5]|(2[0-4]|1{0,1}[0-9]){0,1}[0-9])\\.){3,3}(25[0-5]|(2[0-4]|1{0,1}[0-9]){0,1}[0-9])|([0-9a-fA-F]{1,4}:){1,4}:((25[0-5]|(2[0-4]|1{0,1}[0-9]){0,1}[0-9])\\.){3,3}(25[0-5]|(2[0-4]|1{0,1}[0-9]){0,1}[0-9]))");
    public static final Pattern FLOAT_NUMBERIC_PATTERN = Pattern.compile("^[0-9\\-\\.]+$");
    public static final Pattern ABC_PATTERN = Pattern.compile("^[a-z|A-Z]+$");
    public static final Pattern EMAIL_PATTERN = Pattern.compile("^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$");
    public static final Pattern EMOJI_PATTERN = Pattern.compile("[\\uD83C-\\uDBFF]+|[\\uDC00-\\uDFFF]+");
    public static final Pattern CASE_INSENSITIVE_PATTERN = Pattern.compile("<(/?)([^<>]*)?>",Pattern.CASE_INSENSITIVE);





    public static boolean isNumeric(String src) {
        return !StringUtils.isEmpty(src) && NUMBERIC_PATTERN.matcher(src).find();
    }


    public static boolean isABC(String src) {
        return !StringUtils.isEmpty(src) && ABC_PATTERN.matcher(src).find();
    }


    public static boolean isFloatNumeric(String src) {
        return !StringUtils.isEmpty(src) && FLOAT_NUMBERIC_PATTERN.matcher(src).find();
    }


    public static boolean isEmail(String email) {
        return !StringUtils.isEmpty(email) && EMAIL_PATTERN.matcher(email).find();
    }


    public static boolean isIP6(String ipAddress) {
        return !StringUtils.isEmpty(ipAddress) && IP6_PATTERN.matcher(ipAddress).find();
    }


    public static boolean isIP(String ipAddress) {
        return !StringUtils.isEmpty(ipAddress) && IP_PATTERN.matcher(ipAddress).find();
    }


    public static boolean isEmojiString(String input) {
        return !StringUtils.isEmpty(input) && EMOJI_PATTERN.matcher(input).find();
    }


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
}
