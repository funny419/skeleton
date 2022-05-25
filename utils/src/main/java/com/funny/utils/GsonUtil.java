package com.funny.utils;

import com.funny.utils.helper.DateConverter;
import com.funny.utils.helper.GsonExclusion;
import com.funny.utils.helper.GsonInclusion;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.ArrayUtils;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.security.InvalidParameterException;
import java.util.Date;
import java.util.HashMap;


public class GsonUtil {
    public static String toJson(Object obj) {
        return toJsonExclude(obj);
    }


    public static String toJsonExclude(Object obj,String... exclusionFields) {
        validateJsonObject(obj);
        GsonBuilder builder = new GsonBuilder();
        builder.setDateFormat(DateUtil.FULL_KOREAN_PATTERN);

        if (ArrayUtils.isNotEmpty(exclusionFields)) {
            GsonExclusion gsonFilter = new GsonExclusion();
            gsonFilter.addExclusionField(exclusionFields);
            builder.setExclusionStrategies(gsonFilter);
        }

        Gson gson = builder.create();
        return gson.toJson(obj);
    }


    public static String toJsonInclude(Object obj,String... includeFields) {
        validateJsonObject(obj);
        GsonBuilder builder = new GsonBuilder();
        builder.setDateFormat(DateUtil.FULL_KOREAN_PATTERN);

        if (ArrayUtils.isNotEmpty(includeFields)) {
            GsonInclusion gsonFilter = new GsonInclusion();
            gsonFilter.addInclusionFields(includeFields);
            builder.setExclusionStrategies(gsonFilter);
        }

        Gson gson = builder.create();
        return gson.toJson(obj);
    }


    public static void printJson(HttpServletResponse response,String json) {
        PrintWriter writer = null;
        try {
            response.setContentType("application/json");
            writer = response.getWriter();
            writer.print(json);
        } catch (IOException ignored) {
        } finally {
            if (writer != null) {
                writer.close();
            }
        }
    }


    public static HashMap convertJson2Map(String json) {
        if (json == null) return null;
        Gson gson = new GsonBuilder().create();
        return gson.fromJson(json,HashMap.class);
    }


    public static void printJson(HttpServletResponse response,String key,String value) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(key,value);
        printJson(response,jsonObject.toString());
    }


    public static void printJson(HttpServletResponse response,String key,Integer value) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(key,value);
        printJson(response,jsonObject.toString());
    }


    public static void printJson(HttpServletResponse response,String key,Float value) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(key,value);
        printJson(response,jsonObject.toString());
    }


    public static void printJson(HttpServletResponse response,String key,Double value) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(key,value);
        printJson(response,jsonObject.toString());
    }


    public static void printJson(HttpServletResponse response,String key,Boolean value) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(key,value);
        printJson(response,jsonObject.toString());
    }


    public static void printJsonObject(HttpServletResponse response,Object obj) {
        if (obj == null) {
            throw new NullPointerException();
        }

        if (obj instanceof String) {
            printJson(response,(String) obj);
            return;
        }

        String json = toJson(obj);
        printJson(response,json);
    }


    public static void printError(HttpServletResponse response,String reason) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("error",reason);
        printJson(response,jsonObject.toString());
    }


    private static void validateJsonObject(Object obj) {
        if (obj == null) {
            throw new NullPointerException();
        }

        if (obj instanceof String || obj instanceof Number || obj instanceof Boolean) {
            throw new InvalidParameterException();
        }
    }


    public static <T> T wrapDataToEntity(HttpServletRequest request,Class<T> clazz,String... excludeFields) {
        if (request == null || clazz == null) {
            throw new NullPointerException();
        }

        String data = null;
        try {
            data = IOUtils.toString(request.getInputStream(),StandardCharsets.UTF_8);
        } catch (IOException ignored) {}

        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Date.class,new DateConverter(StandardCharsets.UTF_8.name()));

        if (ArrayUtils.isNotEmpty(excludeFields)) {
            GsonExclusion exclusions = new GsonExclusion();
            exclusions.addExclusionField(excludeFields);
            builder.setExclusionStrategies(exclusions);
        }

        Gson gson = builder.create();
        return gson.fromJson(data,clazz);
    }


    public static void printSuccess(HttpServletResponse response) {
        printJson(response,"success",true);
    }


    private GsonUtil() {
        throw new IllegalStateException("THIS IS A UTILITY CLASS");
    }
}
