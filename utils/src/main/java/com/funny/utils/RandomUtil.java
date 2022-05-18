package com.funny.utils;

import java.math.BigDecimal;
import java.util.Random;


public class RandomUtil {
    public static Random random = new Random();
    public static final String GREATER_THAN = "x must be greater than or equal 0";
    public static final String Y_GREATER_THAN_X = "y must be greater than x";
    private static final int DISPLAYABLE_MIN_INDEX = 33;
    private static final int DISPLAYABLE_MAX_INDEX = 127 - 1;
    private static final String ALL_CHAR = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String LETTER_CHAR = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String NUMBER_CHAR = "0123456789";
    private static final ThreadLocal<Random> local = new ThreadLocal<Random>() {
        @Override
        protected Random initialValue() {
            return new Random();
        }
    };




    public static String generateString(int length) {
        StringBuilder sb = new StringBuilder();
        for (int i=0;i<length;i++) {
            sb.append(ALL_CHAR.charAt(random.nextInt(ALL_CHAR.length())));
        }
        return sb.toString();
    }


    public static String generateMixString(int length) {
        StringBuilder sb = new StringBuilder();
        for (int i=0;i<length;i++) {
            sb.append(LETTER_CHAR.charAt(random.nextInt(LETTER_CHAR.length())));
        }
        return sb.toString();
    }


    public static String generateLowerString(int length) {
        return generateMixString(length).toLowerCase();
    }


    public static String generateUpperString(int length) {
        return generateMixString(length).toUpperCase();
    }


    public static String generateNumberString(int length) {
        StringBuilder sb = new StringBuilder();
        for (int i=0;i<length;i++) {
            sb.append(NUMBER_CHAR.charAt(random.nextInt(NUMBER_CHAR.length())));
        }
        return sb.toString();
    }


    public static Random getRandom() {
        return local.get();
    }


    public static int intSeed(int x) {
        return intSeed(0,x);
    }


    public static int intSeed(int x,int y) {
        if (x < 0) { // ensure x >= 0
            throw new IllegalArgumentException(Y_GREATER_THAN_X);
        }

        if (x >= y) { // ensure y > x
            throw new IllegalArgumentException("");
        }

        return x + getRandom().nextInt(y - x + 1);
    }


    public static long longSeed(long x) {
        return longSeed(0,x);
    }


    public static long longSeed(long x,long y) {
        if (x < 0) { // ensure x >= 0
            throw new IllegalArgumentException(GREATER_THAN);
        }

        if (x >= y) { // ensure y > x
            throw new IllegalArgumentException(Y_GREATER_THAN_X);
        }

        return Math.abs(getRandom().nextLong() % (y - x + 1)) + x;
    }


    public static float floatSeed(float x) {
        return ConverterUtil.toFloat(doubleSeed(0.,x));
    }


    public static float floatSeed(float x,float y) {
        return ConverterUtil.toFloat(doubleSeed(x,y));
    }


    public static double doubleSeed(double x) {
        return doubleSeed(0.,x);
    }


    public static double doubleSeed(double x,double y) {
        if (x < 0) { // ensure x >= 0
            throw new IllegalArgumentException(GREATER_THAN);
        }

        if (x >= y) { // ensure y > x
            throw new IllegalArgumentException(Y_GREATER_THAN_X);
        }

        BigDecimal dx = ConverterUtil.toBigDecimal(String.valueOf(x));
        BigDecimal dy = ConverterUtil.toBigDecimal(String.valueOf(y));

        double diff = dy.subtract(dx).doubleValue();
        dx = dx.add(ConverterUtil.toBigDecimal(String.valueOf(getRandom().nextDouble()*diff)));

        return dx.doubleValue();
    }


    public static boolean boolSeed() {
        return intSeed(0,1) != 0;
    }


    public static char charSeed(char x,char y) {
        if (x < DISPLAYABLE_MIN_INDEX) { // ensure x >= 33 ('!')
            throw new IllegalArgumentException("x must be greater than or equal '!'");
        }

        if (x >= y) { // ensure y > x
            throw new IllegalArgumentException("Y_GREATER_THAN_X");
        }

        if (y > DISPLAYABLE_MAX_INDEX) { // ensure y <= 126 ('~')
            throw new IllegalArgumentException("y must be less than or equal '~'");
        }

        return ConverterUtil.toCharacter(getRandom().nextInt(y-x+1)+x);
    }
}
