package com.funny.utils;

import com.funny.utils.constants.IOConstant;
import com.funny.utils.helper.ByteArray;
import com.funny.utils.helper.ByteArrayOutputStream;
import com.funny.utils.helper.FastByteArrayOutputStream;

import java.io.*;


public class InOutStreamUtil {
    public static void io(InputStream in,OutputStream out) throws IOException {
        io(in,out,-1);
    }


    public static void io(InputStream in,OutputStream out,int bufferSize) throws IOException {
        if (bufferSize == -1) {
            bufferSize = IOConstant.DEFAULT_BUFFER_SIZE;
        }

        byte[] buffer = new byte[bufferSize];
        int amount;

        while ((amount = in.read(buffer)) >= 0) {
            out.write(buffer,0,amount);
        }

        out.flush();
    }


    public static void io(Reader in,Writer out) throws IOException {
        io(in,out,-1);
    }


    public static void io(Reader in,Writer out,int bufferSize) throws IOException {
        if (bufferSize == -1) {
            bufferSize = IOConstant.DEFAULT_BUFFER_SIZE >> 1;
        }

        char[] buffer = new char[bufferSize];
        int amount;

        while ((amount = in.read(buffer)) >= 0) {
            out.write(buffer,0,amount);
        }

        out.flush();
    }


    public static void io(InputStream in,OutputStream out,boolean closeIn,boolean closeOut) throws IOException {
        try {
            io(in,out);
        }
        finally {
            if (closeIn) {
                close(in);
            }

            if (closeOut) {
                close(out);
            }
        }
    }


    public static void io(Reader in,Writer out,boolean closeIn,boolean closeOut) throws IOException {
        try {
            io(in,out);
        }
        finally {
            if (closeIn) {
                close(in);
            }

            if (closeOut) {
                close(out);
            }
        }
    }


    public static void io(InputStream in,File dest) throws IOException {
        OutputStream out = new FileOutputStream(dest);
        io(in,out);
    }


    public static void io(InputStream in,File dest,boolean closeIn,boolean closeOut) throws IOException {
        OutputStream out = new FileOutputStream(dest);
        try {
            io(in,out);
        }
        finally {
            if (closeIn) {
                close(in);
            }

            if (closeOut) {
                close(out);
            }
        }
    }


    public static void io(InputStream in,String dest) throws IOException {
        OutputStream out = new FileOutputStream(dest);
        io(in,out);
    }


    public static void io(InputStream in,String dest,boolean closeIn,boolean closeOut) throws IOException {
        OutputStream out = new FileOutputStream(dest);
        try {
            io(in,out);
        }
        finally {
            if (closeIn) {
                close(in);
            }

            if (closeOut) {
                close(out);
            }
        }
    }

    public static void io(Reader in,File dest) throws IOException {
        Writer out = new FileWriter(dest);
        io(in,out);
    }


    public static void io(Reader in,File dest,boolean closeIn,boolean closeOut) throws IOException {
        Writer out = new FileWriter(dest);

        try {
            io(in,out);
        }
        finally {
            if (closeIn) {
                close(in);
            }

            if (closeOut) {
                close(out);
            }
        }
    }


    public static void io(Reader in,String dest) throws IOException {
        Writer out = new FileWriter(dest);
        io(in,out);
    }


    public static void io(Reader in,String dest,boolean closeIn,boolean closeOut) throws IOException {
        Writer out = new FileWriter(dest);

        try {
            io(in,out);
        }
        finally {
            if (closeIn) {
                close(in);
            }

            if (closeOut) {
                close(out);
            }
        }
    }


    public static String streamToString(InputStream input) throws Exception {
        byte[] bytes = new byte[4096];
        StringBuilder result = new StringBuilder();

        for (int n; (n = input.read(bytes)) != -1;) {
            result.append(new String(bytes,0,n));
        }

        return result.toString();
    }


    public static byte[] streamToByte(InputStream input) throws Exception {
        byte[] bytes = new byte[1024];
        java.io.ByteArrayOutputStream baos = new java.io.ByteArrayOutputStream();

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
            fos.write(buffer,0,len);
        }
    }


    public static OutputStream synchronizedOutputStream(OutputStream out) {
        return new SynchronizedOutputStream(out);
    }


    public static OutputStream synchronizedOutputStream(OutputStream out,Object lock) {
        return new SynchronizedOutputStream(out,lock);
    }


    public static String readText(InputStream in) throws IOException {
        return readText(in,null,-1);
    }


    public static String readText(InputStream in,String encoding) throws IOException {
        return readText(in,encoding,-1);
    }


    public static String readText(InputStream in,String charset,int bufferSize) throws IOException {
        Reader reader = (charset == null) ? new InputStreamReader(in) : new InputStreamReader(in,charset);
        return readText(reader,bufferSize);
    }


    public static String readText(InputStream in,String charset,boolean closeIn) throws IOException {
        Reader reader = charset == null ? new InputStreamReader(in) : new InputStreamReader(in,charset);
        return readText(reader,closeIn);
    }


    public static String readText(Reader in,boolean closeIn) throws IOException {
        StringWriter out = new StringWriter();
        io(in,out,closeIn,true);
        return out.toString();
    }


    public static String readText(Reader reader) throws IOException {
        return readText(reader,-1);
    }


    public static String readText(Reader reader,int bufferSize) throws IOException {
        StringWriter writer = new StringWriter();
        io(reader,writer,bufferSize);
        return writer.toString();
    }


    public static ByteArray readBytes(InputStream in) throws IOException {
        com.funny.utils.helper.ByteArrayOutputStream out = new com.funny.utils.helper.ByteArrayOutputStream();
        io(in,out);
        return out.toByteArray();
    }


    public static ByteArray readBytes(InputStream in,boolean closeIn) throws IOException {
        com.funny.utils.helper.ByteArrayOutputStream out = new com.funny.utils.helper.ByteArrayOutputStream();
        io(in,out,closeIn,true);
        return out.toByteArray();
    }

    public static ByteArray readBytes(File file) throws IOException {
        com.funny.utils.helper.ByteArrayOutputStream out = new com.funny.utils.helper.ByteArrayOutputStream();
        io(new FileInputStream(file),out);
        return out.toByteArray();
    }


    public static ByteArray readBytes(File file,boolean closeIn) throws IOException {
        com.funny.utils.helper.ByteArrayOutputStream out = new ByteArrayOutputStream();
        io(new FileInputStream(file),out,closeIn,true);
        return out.toByteArray();
    }


    public static byte[] readBytesByFast(InputStream in) throws IOException {
        FastByteArrayOutputStream out = new FastByteArrayOutputStream();
        io(in,out);
        return out.toByteArray();
    }


    public static byte[] readBytesByFast(InputStream in,boolean closeIn) throws IOException {
        FastByteArrayOutputStream out = new FastByteArrayOutputStream();
        io(in,out,closeIn,true);
        return out.toByteArray();
    }


    public static void writeText(CharSequence chars,OutputStream out,String charset,boolean closeOut) throws IOException {
        Writer writer = charset == null ? new OutputStreamWriter(out) : new OutputStreamWriter(out,charset);
        writeText(chars,writer,closeOut);
    }


    public static void writeText(CharSequence chars,Writer out,boolean closeOut) throws IOException {
        try {
            out.write(chars.toString());
            out.flush();
        }
        finally {
            if (closeOut) {
                try {
                    out.close();
                } catch (IOException ignored) {}
            }
        }
    }


    public static void writeBytes(byte[] bytes,String filePath,boolean closeOut) throws IOException {
        writeBytes(new ByteArray(bytes),new FileOutputStream(filePath),closeOut);
    }


    public static void writeBytes(byte[] bytes,File file,boolean closeOut) throws IOException {
        writeBytes(new ByteArray(bytes),new FileOutputStream(file),closeOut);
    }


    public static void writeBytes(byte[] bytes,OutputStream out,boolean closeOut) throws IOException {
        writeBytes(new ByteArray(bytes),out,closeOut);
    }


    public static void writeBytes(ByteArray bytes,OutputStream out,boolean closeOut) throws IOException {
        try {
            out.write(bytes.getRawBytes(),bytes.getOffset(),bytes.getLength());
            out.flush();
        }
        finally {
            if (closeOut) {
                try {
                    out.close();
                } catch (IOException ignored) {}
            }
        }
    }


    public static void close(Closeable closed) {
        if (closed != null) {
            try {
                closed.close();
            } catch (IOException ignored) {}
        }
    }

    private static class SynchronizedOutputStream extends OutputStream {
        private OutputStream out;
        private Object lock;

        SynchronizedOutputStream(OutputStream out) {
            this(out,out);
        }

        SynchronizedOutputStream(OutputStream out,Object lock) {
            this.out = out;
            this.lock = lock;
        }

        public void write(int datum) throws IOException {
            synchronized (lock) {
                out.write(datum);
            }
        }

        public void write(byte[] data) throws IOException {
            synchronized (lock) {
                out.write(data);
            }
        }

        public void write(byte[] data,int offset,int length) throws IOException {
            synchronized (lock) {
                out.write(data,offset,length);
            }
        }

        public void flush() throws IOException {
            synchronized (lock) {
                out.flush();
            }
        }

        public void close() throws IOException {
            synchronized (lock) {
                out.close();
            }
        }
    }


    private InOutStreamUtil() {
        throw new IllegalStateException("THIS IS A UTILITY CLASS");
    }
}
