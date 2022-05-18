package com.funny.utils.convert;

import com.funny.utils.ClassUtil;
import java.lang.reflect.Array;


public class ConverterManagerBean {
    private static ConverterManagerBean instance = new ConverterManagerBean();

    private ConverterManagerBean() {}




    public static ConverterManagerBean getInstance() {
        return instance;
    }


    public void register(Class<?> type,TypeConverter<?> converter) {
        ObjectConverter.typeConverters.put(type,converter);
    }


    public void unregister(Class<?> type) {
        ObjectConverter.typeConverters.remove(type);
    }


    public TypeConverter<?> lookup(Class<?> type) {
        return ObjectConverter.typeConverters.get(type);
    }


    public <T> T convertType(Object value,Class<T> destinationType) {
        TypeConverter<?> converter = lookup(destinationType);

        if (converter != null) {
            return (T) converter.toConvert(value);
        }

        if (value == null) {
            return null;
        }

        if (ClassUtil.isInstance(destinationType,value)) {
            return (T) value;
        }

        if (destinationType.isArray()) {
            return convertArray(value,destinationType);
        }

        if (destinationType.isEnum()) {
            Object[] enums = destinationType.getEnumConstants();
            String valStr = value.toString();
            for (Object object : enums) {
                if (object.toString().equals(valStr)) {
                    return (T) object;
                }
            }
        }

        throw new ClassCastException("Unable to cast to type: " + destinationType.getName());
    }


    private <T> T convertArray(Object value,Class<T> destinationType) {
        Class<?> componentType = destinationType.getComponentType();

        if (!value.getClass().isArray()) {
            T[] result = (T[]) Array.newInstance(componentType,1);
            result[0] = (T) convertType(value,componentType);
            return (T) result;
        }

        Object[] array = (Object[]) value;
        T[] result = (T[]) Array.newInstance(componentType,array.length);
        for (int i=0,cnt=array.length;i<cnt;i++) {
            result[i] = (T) convertType(array[i],componentType);
        }

        return (T) result;
    }
}
