package com.funny.utils;

import org.apache.commons.lang3.StringUtils;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;


public class Base64Util {
    private static final String DEFAULT_CHARSET = StandardCharsets.UTF_8.name();



    public static byte[] decode(String val) {
        if (StringUtils.isEmpty(val)) {
            return null;
        }

        return Base64.getDecoder().decode(val);
    }


    public static String decodeToString(String val) throws UnsupportedEncodingException {
        return decodeToString(val,DEFAULT_CHARSET);
    }


    public static String decodeToString(String val,String charset) throws UnsupportedEncodingException {
        if (StringUtils.isEmpty(val)) {
            return null;
        }

        byte[] decoded = decode(val);
        if (decoded == null) {
            return null;
        }

        return new String(decoded,charset);
    }


    public static String encode(byte[] bytes) {
        if (bytes == null) {
            return null;
        }

        return Base64.getEncoder().encodeToString(bytes);
    }


    public static String encode(String val) throws UnsupportedEncodingException {
        return encode(val,DEFAULT_CHARSET);
    }


    public static String encode(String val,String charset) throws UnsupportedEncodingException {
        if (StringUtils.isEmpty(val)) {
            return null;
        }

        byte[] bytes = val.getBytes(charset);
        return encode(bytes);
    }


    private Base64Util() {
        throw new IllegalStateException("THIS IS A UTILITY CLASS");
    }
}
