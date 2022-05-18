package com.funny.utils.convert;

import com.funny.utils.ClassLoaderUtil;
import com.funny.utils.CollectionUtil;
import com.funny.utils.PackageUtil;

import java.io.IOException;
import java.util.List;
import java.util.Map;


public class ObjectConverter<T> {
    protected static Map<Class<?>,TypeConverter<?>> typeConverters = CollectionUtil.createHashMap();
    private final static ObjectConverter<Object> instance = createInstance();

    static {
        try {
            init();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static <T> ObjectConverter<T> createInstance() {
        return new ObjectConverter<T>();
    }

    public static ObjectConverter<Object> getInstance() {
        return instance;
    }




    private static void init() throws IOException,ClassNotFoundException,InstantiationException,IllegalAccessException {
        String packageName = PackageUtil.getPackage(ObjectConverter.class).getName() + ".*";
        List<String> classNames = PackageUtil.getClassesInPackage(packageName);

        for (String classname : classNames) {
            Class<?> clazz = ClassLoaderUtil.loadClass(classname);
            if (clazz.getAnnotation(TypeConverter.Convert.class) != null) {
                clazz.newInstance();
            }
        }
    }


    public T toConvert(String value,Class<?> clazz) {
        if (value == null) {
            return null;
        }

        TypeConverter<T> converter = (TypeConverter<T>) typeConverters.get(clazz);
        return (converter == null) ? null : converter.toConvert(value);
    }


    public String fromConvert(T value) {
        if (value == null) {
            return null;
        }

        TypeConverter<T> converter = (TypeConverter<T>) typeConverters.get(value.getClass());
        return (converter == null) ? null : converter.fromConvert(value);
    }


    protected void register(Class<T> clazz) {
        typeConverters.put(clazz,(TypeConverter<?>) this);
    }


    public T toConvert(Object value,Class<?> clazz) {
        if (value == null) {
            return null;
        }

        TypeConverter<T> converter = (TypeConverter<T>) typeConverters.get(clazz);
        return (converter == null) ? null : converter.toConvert(value);
    }
}
