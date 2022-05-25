package com.funny.utils;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import java.util.Collection;
import java.util.Map;


public class CheckUtil {
    public static boolean valid(String src) {
        return !StringUtils.isEmpty(src);
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
        return !CollectionUtils.isEmpty(collection);
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
        return !MapUtils.isEmpty(map);
    }


    public static boolean valid(Map... maps) {
        for (Map m : maps) {
            if (!valid(m)) {
                return false;
            }
        }

        return true;
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


    private CheckUtil() {
        throw new IllegalStateException("THIS IS A UTILITY CLASS");
    }
}
