package com.funny.utils;

import okhttp3.HttpUrl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.NumberUtils;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;


public class ConvertUtil {
    public static final double HUNDRED = 100.0D;
    public static final double ZERO = 0.0D;
    public static final double TEN = 10.0D;




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


    public static long cutUnits(long price) {
        return (long) (Math.floor((double) price / 10.0D) * 10L);
    }


    public static long cutTenBasicUnits(long price) {
        return (long) (Math.floor((double) price / 100.0D) * 100L);
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
}
