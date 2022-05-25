package com.funny.utils;

import javax.xml.bind.DatatypeConverter;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class HashUtil {
    public static String md5(byte[] input) throws NoSuchAlgorithmException {
        return getHash(input,"MD5");
    }


    public static String md5(String input) throws NoSuchAlgorithmException {
        return getHash(input,"MD5");
    }


    public static String sha1(byte[] input) throws NoSuchAlgorithmException {
        return getHash(input,"SHA-1");
    }


    public static String sha1(String input) throws NoSuchAlgorithmException {
        return getHash(input,"SHA-1");
    }


    public static String sha256(byte[] input) throws NoSuchAlgorithmException {
        return getHash(input,"SHA-256");
    }


    public static String sha256(String input) throws NoSuchAlgorithmException {
        return getHash(input,"SHA-256");
    }


    public static String sha512(byte[] input) throws NoSuchAlgorithmException {
        return getHash(input,"SHA-512");
    }


    public static String sha512(String input) throws NoSuchAlgorithmException {
        return getHash(input,"SHA-512");
    }


    public static String getHash(String val,String algorithm) throws NoSuchAlgorithmException {
        byte[] bytes = val.getBytes(StandardCharsets.UTF_8);
        return getHash(bytes,algorithm);
    }


    public static String getHash(byte[] bytes,String algorithm) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance(algorithm);
        byte[] input = md.digest(bytes);
        return DatatypeConverter.printHexBinary(input).toLowerCase();
    }


    private HashUtil() {
        throw new IllegalStateException("THIS IS A UTILITY CLASS");
    }
}
