package com.funny.utils;

import com.funny.utils.constants.PatternConstants;
import org.apache.commons.lang3.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class CheckUtil {
    public static boolean isDate(String date,String format) {
        SimpleDateFormat sf = new SimpleDateFormat(format);
        try {
            sf.parse(date);
            return true;
        } catch (ParseException ignore) {}

        return false;
    }


    public static boolean valid(String src) {
        return !(src == null || "".equals(src.trim()));
    }


    public static boolean valid(String[] strings) {
        for (String s : strings) {
            if (!valid(s)) {
                return false;
            }
        }

        return true;
    }


    public static boolean valid(Object obj) {
        return !(obj == null);
    }


    public static boolean valid(Object[] objects) {
        return objects != null && objects.length != 0;
    }


    public static boolean valid(Collection collection) {
        return !(collection == null || collection.isEmpty());
    }


    public static boolean valid(Collection... cols) {
        for (Collection c : cols) {
            if (!valid(c)) {
                return false;
            }
        }

        return true;
    }


    public static boolean valid (Map map) {
        return !(map == null || map.isEmpty());
    }


    public static boolean valid(Map... maps) {
        for (Map m : maps) {
            if (!valid(m)) {
                return false;
            }
        }

        return true;
    }


    public static boolean isPattern(String format, String txt, String... types) {
        if (StringUtils.isEmpty(txt)) {
            return true;
        }

        StringBuilder regx = new StringBuilder();
        if (StringUtils.isNoneEmpty(types)) {
            for (String p : types) {
                regx.append(PatternConstants.getConstantsFormat(p));

                if (PatternConstants.NOT_SPACE.equals(p)) {
                    format = format.replace("\\s", PatternConstants.getConstantsFormat(p));
                }
            }
        }
        return Pattern.matches(String.format(format, regx.toString()), txt);
    }


    public static boolean isAllowInput(String txt, String... types) {
        return isPattern("^[\\s%s]*$", txt, types);
    }


    public static boolean isNotAllowInput(String txt, String... types) {
        return isPattern("[^%s]+", txt, types);
    }


    public static boolean isEnterStr(final String str) {
        boolean isBool = false;

        if (!StringUtils.isBlank(str)) {
            final Pattern p = Pattern.compile(PatternConstants.ENTER_STR);
            final Matcher m = p.matcher(str);

            isBool = m.find();
        }

        return isBool;
    }


    public static boolean isEnterNotEmptyStr(final String str) {
        boolean isBool = false;

        if (StringUtils.isNotBlank(str)) {
            final String[] strs = StringUtil.excelEnterSplit(str);

            for (final String s : strs) {
                if (StringUtils.isBlank(s)) {
                    isBool = true;
                }
            }
        }

        return isBool;
    }


    public static boolean isValidMaxNumber(final long value, final long max) {
        return value <= max;
    }
}
