package com.funny.utils;

import org.apache.commons.lang3.StringUtils;

import java.io.ByteArrayOutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;


public class CharsetUtil {
    public static String toASCII(String str) throws UnsupportedEncodingException {
        return changeCharset(str,StandardCharsets.US_ASCII.name());
    }


    public static String toUTF8(String str) throws UnsupportedEncodingException {
        return changeCharset(str,StandardCharsets.UTF_8.name());
    }


    public static String toUTF16BE(String str) throws UnsupportedEncodingException {
        return changeCharset(str,StandardCharsets.UTF_16BE.name());
    }


    public static String toUTF16LE(String str) throws UnsupportedEncodingException {
        return changeCharset(str,StandardCharsets.UTF_16LE.name());
    }


    public static String toUTF16(String str) throws UnsupportedEncodingException {
        return changeCharset(str,StandardCharsets.UTF_16.name());
    }


    public static String changeCharset(String str,String charset) throws UnsupportedEncodingException {
        if (StringUtils.isNotEmpty(str)) {
            byte[] bytes = str.getBytes();
            return new String(bytes,charset);
        }

        return null;
    }


    public static String changeCharset(String str,String beforeCharset,String afterCharset) throws UnsupportedEncodingException {
        if (StringUtils.isNotEmpty(str)) {
            byte[] bytes = str.getBytes(beforeCharset);
            return new String(bytes,afterCharset);
        }

        return null;
    }


    public static String getDefaultCharset() {
        OutputStreamWriter writer = new OutputStreamWriter(new ByteArrayOutputStream(),StandardCharsets.UTF_8);
        return writer.getEncoding();
    }


    private CharsetUtil() {
        throw new IllegalStateException("THIS IS A UTILITY CLASS");
    }
}
