package com.funny.utils;

import java.io.*;


public class InputStreamUtil {
    public static String streamToString(InputStream input) throws Exception {
        byte[] bytes = new byte[4096];
        StringBuilder result = new StringBuilder();

        for (int n; (n = input.read(bytes)) != -1; ) {
            result.append(new String(bytes,0,n));
        }

        return result.toString();
    }


    public static byte[] streamToByte(InputStream input) throws Exception {
        byte[] bytes = new byte[1024];
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        int len = 0;
        while ((len = input.read(bytes,0,bytes.length)) != -1) {
            baos.write(bytes,0,len);
        }

        return baos.toByteArray();
    }


    public static byte[] inputStreamToByte(InputStream input) throws Exception {
        int count = 0;
        while (count == 0) {
            count = input.available();
        }

        byte[] bytes = new byte[count];
        input.read(bytes);
        return bytes;
    }


    public static InputStream byteToInputStream(byte[] bytes) throws Exception {
        return new ByteArrayInputStream(bytes);
    }


    public static void streamSaveAsFile(InputStream input,File file) throws Exception {
        byte[] buffer = new byte[1024];
        FileOutputStream fos = new FileOutputStream(file);

        int len;
        while ((len = input.read(buffer)) > 0) {
            fos.write(buffer, 0, len);
        }
    }
}
