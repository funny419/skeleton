package com.funny.utils.extend;

import com.funny.utils.ConverterUtil;
import org.apache.commons.lang3.StringUtils;


public class BCConvert {
    static final char DBC_CHAR_START = 33;
    static final char DBC_CHAR_END = 126;
    static final char SBC_CHAR_START = 65281;
    static final char SBC_CHAR_END = 65374;
    static final int CONVERT_STEP = 65248;
    static final char SBC_SPACE = 12288;
    static final char DBC_SPACE = ' ';




    public static String bjToqj(String input) {
        if (StringUtils.isEmpty(input)) {
            return "";
        }

        char[] ca = input.toCharArray();
        StringBuilder result = new StringBuilder(input.length());

        for (char t : ca) {
            if (t == DBC_SPACE) {
                result.append(SBC_SPACE);
            } else if ((t >= DBC_CHAR_START) && (t <= DBC_CHAR_END)) {
                result.append(ConverterUtil.toCharacter(t + CONVERT_STEP));
            } else {
                result.append(t);
            }
        }

        return result.toString();
    }


    public static String qjTobj(String input) {
        if (StringUtils.isEmpty(input)) {
            return "";
        }

        char[] ca = input.toCharArray();
        StringBuilder result = new StringBuilder(input.length());

        for (int i=0,cnt=input.length();i<cnt;i++) {
            if (ca[i] >= SBC_CHAR_START && ca[i] <= SBC_CHAR_END) {
                result.append(ConverterUtil.toCharacter(ca[i] - CONVERT_STEP));
            } else if (ca[i] == SBC_SPACE) {
                result.append(DBC_SPACE);
            } else {
                result.append(ca[i]);
            }
        }

        return result.toString();
    }
}
