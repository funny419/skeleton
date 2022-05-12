package com.funny.utils;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;


@Slf4j
public class PropertiesUtil {
    public static String key(String key) {
        return System.getProperty(key);
    }


    public static String getValueByKey(String filePath,String key) {
        try {
            InputStream inputStream = new BufferedInputStream(new FileInputStream(filePath));
            Properties properties = new Properties();
            properties.load(inputStream);
            return properties.getProperty(key);
        }
        catch (Exception e) {
            log.error("getValueByKey error : " + e.getMessage());
            return null;
        }
    }


    public static Map<String,String> properties(InputStream inputStream) {
        try {
            Properties properties = new Properties();
            properties.load(inputStream);

            Map<String,String> map = new HashMap<>();
            Enumeration enumeration = properties.propertyNames();
            while (enumeration.hasMoreElements()) {
                String key = (String) enumeration.nextElement();
                String value = properties.getProperty(key);
                map.put(key,value);
            }

            return map;
        }
        catch (Exception e) {
            log.error("load properties error : " + e.getMessage());
            return null;
        }
    }


    public static Map<String,String> getAllProperties(String filePath) {
        try {
            InputStream inputStream = new BufferedInputStream(new FileInputStream(filePath));
            return properties(inputStream);
        } catch (Exception e) {
            log.error("getAllProperties error : " + e.getMessage());
        }

        return new HashMap<>();
    }
}