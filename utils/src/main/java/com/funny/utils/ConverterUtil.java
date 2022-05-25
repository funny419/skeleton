package com.funny.utils;

import com.funny.utils.constants.Emptys;
import com.funny.utils.convert.ConvertBean;
import okhttp3.HttpUrl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.NumberUtils;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;


/**
 * Converter를 편하게 사용하라고 만들었으나 성능적으로 과연 맞는지 확신이 들지 않음...
 */
public class ConverterUtil {
    public static final double HUNDRED = 100.0D;
    public static final double ZERO = 0.0D;
    public static final double TEN = 10.0D;
    protected static final ConvertBean convertBean = new ConvertBean();

    public static ConvertBean getInstance() {
        return convertBean;
    }




    public static BigDecimal toBigDecimal(Object value,BigDecimal defaultValue) {
        return convertBean.toBigDecimal(value,defaultValue);
    }


    public static Boolean toBoolean(Object value) {
        return convertBean.toBoolean(value);
    }


    public static Boolean toBoolean(Object value,Boolean defaultValue) {
        return convertBean.toBoolean(value,defaultValue);
    }


    public static boolean toBooleanValue(Object value,boolean defaultValue) {
        return convertBean.toBooleanValue(value,defaultValue);
    }


    public static boolean toBooleanValue(Object value) {
        return convertBean.toBooleanValue(value);
    }


    public static Integer toInteger(Object value) {
        return convertBean.toInteger(value);
    }


    public static Integer toInteger(Object value,Integer defaultValue) {
        return convertBean.toInteger(value,defaultValue);
    }


    public static int toIntValue(Object value,int defaultValue) {
        return convertBean.toIntValue(value,defaultValue);
    }


    public static int toIntValue(Object value) {
        return convertBean.toIntValue(value);
    }


    public static Long toLong(Object value) {
        return convertBean.toLong(value);
    }


    public static Long toLong(Object value,Long defaultValue) {
        return convertBean.toLong(value,defaultValue);
    }


    public static long toLongValue(Object value,long defaultValue) {
        return convertBean.toLongValue(value,defaultValue);
    }


    public static long toLongValue(Object value) {
        return convertBean.toLongValue(value);
    }


    public static Float toFloat(Object value) {
        return convertBean.toFloat(value);
    }


    public static Float toFloat(Object value,Float defaultValue) {
        return convertBean.toFloat(value,defaultValue);
    }


    public static float toFloatValue(Object value,float defaultValue) {
        return convertBean.toFloatValue(value,defaultValue);
    }


    public static float toFloatValue(Object value) {
        return convertBean.toFloatValue(value);
    }


    public static Double toDouble(Object value) {
        return convertBean.toDouble(value);
    }


    public static Double toDouble(Object value,Double defaultValue) {
        return convertBean.toDouble(value,defaultValue);
    }


    public static double toDoubleValue(Object value,double defaultValue) {
        return convertBean.toDoubleValue(value,defaultValue);
    }


    public static double toDoubleValue(Object value) {
        return convertBean.toDoubleValue(value);
    }


    public static Short toShort(Object value) {
        return convertBean.toShort(value);
    }


    public static Short toShort(Object value,Short defaultValue) {
        return convertBean.toShort(value,defaultValue);
    }


    public static short toShortValue(Object value,short defaultValue) {
        return convertBean.toShortValue(value,defaultValue);
    }


    public static short toShortValue(Object value) {
        return convertBean.toShortValue(value);
    }


    public static Character toCharacter(Object value) {
        return convertBean.toCharacter(value);
    }


    public static Character toCharacter(Object value,Character defaultValue) {
        return convertBean.toCharacter(value,defaultValue);
    }


    public static char toCharValue(Object value,char defaultValue) {
        return convertBean.toCharValue(value,defaultValue);
    }


    public static char toCharValue(Object value) {
        return convertBean.toCharValue(value);
    }


    public static Byte toByte(Object value) {
        return convertBean.toByte(value);
    }


    public static Byte toByte(Object value,Byte defaultValue) {
        return convertBean.toByte(value,defaultValue);
    }


    public static byte toByteValue(Object value,byte defaultValue) {
        return convertBean.toByteValue(value,defaultValue);
    }


    public static byte toByteValue(Object value) {
        return convertBean.toByteValue(value);
    }


    public static boolean[] toBooleanArray(Object value) {
        return convertBean.toBooleanArray(value);
    }


    public static int[] toIntegerArray(Object value) {
        return convertBean.toIntegerArray(value);
    }


    public static long[] toLongArray(Object value) {
        return convertBean.toLongArray(value);
    }


    public static float[] toFloatArray(Object value) {
        return convertBean.toFloatArray(value);
    }


    public static double[] toDoubleArray(Object value) {
        return convertBean.toDoubleArray(value);
    }


    public static short[] toShortArray(Object value) {
        return convertBean.toShortArray(value);
    }


    public static char[] toCharacterArray(Object value) {
        return convertBean.toCharacterArray(value);
    }


    public static String toString(Object value) {
        return convertBean.toString(value);
    }


    public static String toSafeString(Object value) {
        String result = convertBean.toString(value);
        return result != null ? result : Emptys.EMPTY_STRING;
    }


    public static String toString(Object value,String defaultValue) {
        return convertBean.toString(value,defaultValue);
    }


    public static String[] toStringArray(Object value) {
        return convertBean.toStringArray(value);
    }


    public static Class<?> toClass(Object value) {
        return convertBean.toClass(value);
    }


    public static Class<?>[] toClassArray(Object value) {
        return convertBean.toClassArray(value);
    }


    public static BigInteger toBigInteger(Object value) {
        return convertBean.toBigInteger(value);
    }


    public static BigInteger toBigInteger(Object value,BigInteger defaultValue) {
        return convertBean.toBigInteger(value,defaultValue);
    }


    public static BigDecimal toBigDecimal(Object value) {
        return convertBean.toBigDecimal(value);
    }


    public static <T extends Number> T stringToNumber(String str,Class<T> type) {
        if (!StringUtils.isBlank(str)) {
            return NumberUtils.parseNumber(str,type);
        } else {
            return null;
        }
    }


    public static long convertToAmount(int rate,long basePrice) {
        return cutUnits(Math.round(basePrice * (rate / HUNDRED)));
    }


    public static double convertToRate(double divided,double divisor,double numberOfDecimalPlace) {
        if (divisor <= 0) {
            return ZERO;
        }

        double power = Math.pow(TEN,numberOfDecimalPlace);
        return Math.round(divided / divisor * power)/power;
    }


    public static Date toDate(Object value) {
        return convertBean.toDate(value);
    }


    public static Date toDate(Object value, Date defaultValue) {
        return convertBean.toDate(value, defaultValue);
    }


    public static Calendar toCalendar(Object value) {
        return convertBean.toCalendar(value);
    }


    public static Calendar toCalendar(Object value, Calendar defaultValue) {
        return convertBean.toCalendar(value, defaultValue);
    }


    public static long cutUnits(long price) {
        return toLong(Math.floor(toDouble(price)/10.0D)*10L);
    }


    public static long cutTenBasicUnits(long price) {
        return toLong(Math.floor(toDouble(price)/100.0D)* 100L);
    }


    public static HttpUrl rebuildEmptyQueryParam(String url) {
        return rebuildEmptyQueryParam(HttpUrl.get(url));
    }


    public static HttpUrl rebuildEmptyQueryParam(HttpUrl httpUrl) {
        HttpUrl.Builder urlBuilder = httpUrl.newBuilder();
        AtomicBoolean isChanged = new AtomicBoolean(false);

        httpUrl.queryParameterNames().forEach(name -> {
            List<String> values = httpUrl.queryParameterValues(name);

            if (values.contains(null)) {
                urlBuilder.setQueryParameter(name,"");
                isChanged.set(true);
            }
        });

        return isChanged.get() ? urlBuilder.build() : httpUrl;
    }


    public static String[] copyNewArray(String[] source,int length) {
        String[] newArray = new String[length];
        for (int i=0;i<length;i++) {
            if (i > length - 1) {
                break;
            }

            newArray[i] = source[i];
        }

        return newArray;
    }


    public static boolean[] copyNewArray(boolean[] source,int length) {
        boolean[] newArray = new boolean[length];
        for (int i=0;i<length;i++) {
            if (i > length - 1) {
                break;
            }

            newArray[i] = source[i];
        }

        return newArray;
    }


    public static int[] copyNewArray(int[] source,int length) {
        int[] newArray = new int[length];
        for (int i=0;i<length;i++) {
            if (i > length - 1) {
                break;
            }

            newArray[i] = source[i];
        }

        return newArray;
    }


    public static short[] copyNewArray(short[] source,int length) {
        short[] newArray = new short[length];
        for (int i=0;i<length;i++) {
            if (i > length - 1) {
                break;
            }

            newArray[i] = source[i];
        }

        return newArray;
    }


    private ConverterUtil() {
        throw new IllegalStateException("THIS IS A UTILITY CLASS");
    }
}
