package com.funny.utils;

import org.springframework.util.Base64Utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;


public class CompressUtil {
    public static byte[] compress(String src) throws IOException {
        byte[] dataByte = src.getBytes();
        Deflater deflater = new Deflater();

        deflater.setLevel(Deflater.BEST_COMPRESSION);
        deflater.setInput(dataByte);
        deflater.finish();

        ByteArrayOutputStream bao = new ByteArrayOutputStream(dataByte.length);
        byte[] buf = new byte[1024];

        while (!deflater.finished()) {
            int compByte = deflater.deflate(buf);
            bao.write(buf, 0, compByte);
        }

        bao.close();
        deflater.end();
        return bao.toByteArray();
    }


    public static byte[] decompress(byte[] data) throws IOException, DataFormatException {
        Inflater inflater = new Inflater();
        inflater.setInput(data);

        ByteArrayOutputStream bao = new ByteArrayOutputStream();
        byte[] buf = new byte[1024];

        while (!inflater.finished()) {
            int compByte = inflater.inflate(buf);
            bao.write(buf, 0, compByte);
        }
        bao.close();
        inflater.end();

        return bao.toByteArray();
    }


    public static String compressEncode(final String source) throws IOException {
        return Base64Utils.encodeToString(CompressUtil.compress(source));
    }


    public static String decompressDecode(final String compressedEncodeValue) throws IOException, DataFormatException {
        byte[] data = Base64Utils.decodeFromString(compressedEncodeValue);
        return new String(CompressUtil.decompress(data));
    }
}
