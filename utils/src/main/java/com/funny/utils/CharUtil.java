package com.funny.utils;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;


public final class CharUtil {
    public static char toChar(byte b) {
        return ConverterUtil.toCharacter(b & 0xFF);
    }


    public static byte[] toSimpleByteArray(char[] values) {
        byte[] temp = new byte[values.length];
        for (int i=0,cnt=values.length;i<cnt;i++) {
            temp[i] = ConverterUtil.toByte(values[i]);
        }

        return temp;
    }


    public static char[] toSimpleCharArray(byte[] values) {
        char[] temp = new char[values.length];
        for (int i=0,cnt=values.length;i<cnt;i++) {
            temp[i] = ConverterUtil.toCharacter(values[i] & 0xFF);
        }

        return temp;
    }


    public static int toAscii(char c) {
        return (c <= 0xFF) ? c : 0x3F;
    }


    public static byte[] toAsciiByteArray(char[] chars) {
        byte[] temp = new byte[chars.length];
        for (int i=0,cnt=chars.length;i<cnt;i++) {
            temp[i] = ConverterUtil.toByte(toAscii(ConverterUtil.toCharacter(temp[i])));
        }

        return temp;
    }


    public static byte[] toAsciiByteArray(CharSequence sequence) {
        byte[] temp = new byte[sequence.length()];
        for (int i=0,cnt=sequence.length();i<cnt;i++) {
            char c = sequence.charAt(i);
            temp[i] = ConverterUtil.toByte(toAscii(c));
        }

        return temp;
    }


    public static byte[] toRawByteArray(char[] chars) {
        byte[] temp = new byte[chars.length << 1];
        for (int i=0,pos=0,cnt=chars.length;i<cnt;i++) {
            char c = chars[i];
            temp[pos++] = ConverterUtil.toByte((c & 0xFF00) >> 8);
            temp[pos++] = ConverterUtil.toByte(c & 0x00FF);
        }

        return temp;
    }


    public static char[] toRawCharArray(byte[] bytes) {
        int length = bytes.length >> 1;
        if (length << 1 < bytes.length) {
            length++;
        }

        char[] temp = new char[length];
        int i = 0, j = 0;
        while (i < bytes.length) {
            char c = ConverterUtil.toCharacter(bytes[i] << 8);
            i++;

            if (i != bytes.length) {
                c += bytes[i] & 0xFF;
                i++;
            }

            temp[j++] = c;
        }

        return temp;
    }


    public static byte[] toByteArray(char[] chars) throws UnsupportedEncodingException {
        return new String(chars).getBytes(StandardCharsets.UTF_8);
    }


    public static byte[] toByteArray(char[] chars,String charset) throws UnsupportedEncodingException {
        return new String(chars).getBytes(charset);
    }


    public static char[] toCharArray(byte[] bytes) throws UnsupportedEncodingException {
        return new String(bytes,StandardCharsets.UTF_8).toCharArray();
    }


    public static char[] toCharArray(byte[] bytes,String charset) throws UnsupportedEncodingException {
        return new String(bytes,charset).toCharArray();
    }


    public static boolean equalsOne(char c,char[] match) {
        for (char item : match) {
            if (equalsOne(c,item)) {
                return true;
            }
        }

        return false;
    }


    public static boolean equalsOne(char source,char target) {
        return source == target;
    }


    public static int findFirstEqual(char[] source,int index,char[] match) {
        for (int i=index,cnt=source.length;i<cnt;i++) {
            if (equalsOne(source[i],match)) {
                return i;
            }
        }

        return -1;
    }


    public static int findFirstEqual(char[] source,int index,char match) {
        for (int i=index,cnt=source.length;i<cnt;i++) {
            if (equalsOne(source[i],match)) {
                return i;
            }
        }

        return -1;
    }


    public static int findFirstDiff(char[] source,int index,char[] match) {
        for (int i=index,cnt=source.length;i<cnt;i++) {
            if (!equalsOne(source[i],match)) {
                return i;
            }
        }

        return -1;
    }


    public static int findFirstDiff(char[] source,int index,char match) {
        for (int i=index,cnt=source.length;i<cnt;i++) {
            if (!equalsOne(source[i],match)) {
                return i;
            }
        }

        return -1;
    }


    public static boolean isWhitespace(char c) {
        return c <= ' ';
    }


    public static boolean isLowercaseAlpha(char c) {
        return c >= 'a' && c <= 'z';
    }


    public static boolean isUppercaseAlpha(char c) {
        return c >= 'A' && c <= 'Z';
    }


    public static boolean isAlphaOrDigit(char c) {
        return isDigit(c) || isAlpha(c);
    }


    public static boolean isWordChar(char c) {
        return isDigit(c) || isAlpha(c) || c == '_';
    }


    public static boolean isPropertyNameChar(char c) {
        return isDigit(c) || isAlpha(c) || c == '_' || c == '.' || c == '[' || c == ']';
    }


    public static boolean isAlpha(char c) {
        return isLowercaseAlpha(c) || isUppercaseAlpha(c);
    }


    public static boolean isDigit(char c) {
        return c >= '0' && c <= '9';
    }


    public static boolean isHexDigit(char c) {
        return isDigit(c) || c >= 'a' && c <= 'f' || c >= 'A' && c <= 'F';
    }


    public static boolean isGenericDelimiter(char c) {
        switch (c) {
            case ':':
            case '/':
            case '?':
            case '#':
            case '[':
            case ']':
            case '@':
                return true;
            default:
                return false;
        }
    }


    public static boolean isSubDelimiter(char c) {
        switch (c) {
            case '!':
            case '$':
            case '&':
            case '\'':
            case '(':
            case ')':
            case '*':
            case '+':
            case ',':
            case ';':
            case '=':
                return true;
            default:
                return false;
        }
    }


    public static boolean isReserved(char c) {
        return isGenericDelimiter(c) || isSubDelimiter(c);
    }


    public static boolean isUnreserved(char c) {
        return isAlpha(c) || isDigit(c) || c == '-' || c == '.' || c == '~';
    }


    public static boolean isPchar(char c) {
        return isUnreserved(c) || isSubDelimiter(c) || c == ':' || c == '@';
    }


    public static char toUpperAscii(char c) {
        if (isLowercaseAlpha(c)) {
            c -= ConverterUtil.toCharacter(0x20);
        }

        return c;
    }


    public static char toLowerAscii(char c) {
        if (isUppercaseAlpha(c)) {
            c += ConverterUtil.toCharacter(0x20);
        }

        return c;
    }


    private CharUtil() {
        throw new IllegalStateException("THIS IS A UTILITY CLASS");
    }
}
