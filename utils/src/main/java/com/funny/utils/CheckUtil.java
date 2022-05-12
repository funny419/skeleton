package com.funny.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Map;


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
}
