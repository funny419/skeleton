package com.funny.utils.convert;

import java.lang.annotation.*;


public interface TypeConverter<T> {
    T toConvert(String value);

    String fromConvert(T value);

    T toConvert(Object value);

    @Inherited
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.TYPE)
    public @interface Convert {
    }
}
