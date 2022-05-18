package com.funny.utils.helper;

import com.funny.utils.ConverterUtil;
import com.funny.utils.constants.IOConstant;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


public class ByteArrayOutputStream extends OutputStream {
    private byte[] buffer;
    private int index;
    private int capacity;

    private boolean closed;
    private boolean shared;

    public ByteArrayOutputStream() {
        this(IOConstant.DEFAULT_BUFFER_SIZE);
    }

    public ByteArrayOutputStream(int initialBufferSize) {
        capacity = initialBufferSize;
        buffer = new byte[capacity];
    }




    @Override
    public void write(int datum) throws IOException {
        if (closed) {
            throw new IOException("Stream closed");
        }

        if (index >= capacity) {
            capacity = capacity * 2 + 1;

            byte[] tmp = new byte[capacity];
            System.arraycopy(buffer, 0, tmp, 0, index);
            buffer = tmp;
            shared = false;
        }

        buffer[index++] = ConverterUtil.toByte(datum);
    }


    @Override
    public void write(byte[] data,int offset,int length) throws IOException {
        if (data == null) {
            throw new NullPointerException();
        }

        if (offset < 0 || offset + length > data.length || length < 0) {
            throw new IndexOutOfBoundsException();
        }

        if (closed) {
            throw new IOException("Stream closed");
        }

        if (index + length > capacity) {
            capacity = capacity * 2 + length;

            byte[] tmp = new byte[capacity];
            System.arraycopy(buffer,0,tmp,0,index);

            buffer = tmp;
            shared = false;
        }

        System.arraycopy(data,offset,buffer,index,length);
        index += length;
    }


    @Override
    public void close() {
        closed = true;
    }


    public void writeTo(OutputStream out) throws IOException {
        out.write(buffer,0,index);
    }


    public ByteArray toByteArray() {
        shared = true;
        return new ByteArray(buffer, 0, index);
    }


    public InputStream toInputStream() {
        shared = true;
        return new ByteArrayInputStream(buffer, 0, index);
    }


    public void reset() throws IOException {
        if (closed) {
            throw new IOException("Stream closed");
        }

        if (shared) {
            buffer = new byte[capacity];
            shared = false;
        }

        index = 0;
    }
}
