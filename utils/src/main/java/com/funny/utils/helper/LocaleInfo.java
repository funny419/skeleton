package com.funny.utils.helper;

import com.funny.utils.LocaleUtil;
import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.UnsupportedCharsetException;
import java.util.Locale;


public final class LocaleInfo implements Cloneable,Externalizable,Serializable {
    private static final long serialVersionUID = -2961413717509849223L;
    private Locale locale;
    private Charset charset;

    public static LocaleInfo parse(String name) {
        name = StringUtils.trimToNull(name);

        int index = name.indexOf(":");
        String localePart = name;
        String charsetPart = null;

        if (index >= 0) {
            localePart = name.substring(0,index);
            charsetPart = name.substring(index + 1);
        }

        Locale locale = LocaleUtil.parseLocale(localePart);
        String charset = StringUtils.trimToNull(charsetPart);

        return new LocaleInfo(locale,charset);
    }

    public LocaleInfo() {
        this.locale = Locale.getDefault();
        this.charset = Charset.defaultCharset();
    }

    public LocaleInfo(Locale locale) {
        this(locale,null,LocaleUtil.getDefault());
    }

    public LocaleInfo(Locale locale,String charset) {
        this(locale,charset,LocaleUtil.getDefault());
    }

    public LocaleInfo(Locale locale,String charset,LocaleInfo fallbackLocaleInfo) {
        Assert.assertNotNull("fallbackLocaleInfo",fallbackLocaleInfo);
        charset = StringUtils.trimToNull(charset);

        if (locale == null) {
            locale = fallbackLocaleInfo.getLocale();

            if (charset == null) {
                charset = fallbackLocaleInfo.getCharset().name();
            }
        } else {
            if (charset == null) {
                charset = "UTF-8";
            }
        }

        this.locale = locale;

        try {
            this.charset = Charset.forName(charset);
        } catch (UnsupportedCharsetException e) {
            this.charset = new UnknownCharset(charset);
        }
    }


    public Locale getLocale() {
        return locale;
    }


    public Charset getCharset() {
        return charset;
    }


    public boolean isCharsetSupported() {
        return !(charset instanceof UnknownCharset);
    }


    public LocaleInfo assertCharsetSupported() throws UnsupportedCharsetException {
        if (charset instanceof UnknownCharset) {
            throw new UnsupportedCharsetException(charset.name());
        }

        return this;
    }


    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }

        if (o == this) {
            return true;
        }

        if (!(o instanceof LocaleInfo)) {
            return false;
        }

        LocaleInfo otherLocaleInfo = (LocaleInfo) o;

        return locale.equals(otherLocaleInfo.locale) && charset.equals(otherLocaleInfo.charset);
    }


    @Override
    public int hashCode() {
        return charset.hashCode() * 31 + locale.hashCode();
    }


    @Override
    public Object clone() {
        try {
            return super.clone();
        } catch (CloneNotSupportedException e) {
            throw new UnsupportedOperationException();
        }
    }


    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeUTF(toString());
    }


    public void readExternal(ObjectInput in) throws IOException {
        LocaleInfo info = parse(in.readUTF());

        locale = info.getLocale();
        charset = info.getCharset();
    }


    @Override
    public String toString() {
        return locale + ":" + charset;
    }


    static class UnknownCharset extends Charset {
        public UnknownCharset(String name) {
            super(name,null);
        }


        @Override
        public boolean contains(Charset cs) {
            return false;
        }


        @Override
        public CharsetDecoder newDecoder() {
            throw new UnsupportedOperationException("Could not create decoder for unknown charset: " + name());
        }


        @Override
        public CharsetEncoder newEncoder() {
            throw new UnsupportedOperationException("Could not create encoder for unknown charset: " + name());
        }
    }
}
