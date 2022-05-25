package com.funny.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.*;
import com.google.gson.stream.JsonReader;
import org.apache.commons.lang3.StringUtils;
import java.io.IOException;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;


public class JsonUtil {
    private static final ObjectMapper JSON_MAPPER = new ObjectMapper();
    private static final TypeAdapter<JsonObject> GSON_ADAPTER = new Gson().getAdapter(JsonObject.class);

    static {
        JSON_MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false);
    }




    public static String objectToJson(Object object) throws JsonProcessingException {
        if (object == null) {
            return null;
        }

        return JSON_MAPPER.writeValueAsString(object);
    }


    public static <T> T jsonToObject(String json,Class<T> tClass) throws IOException {
        if (StringUtils.isEmpty(json)) {
            return null;
        }

        return JSON_MAPPER.readValue(json,tClass);
    }


    public static <T> T utf8BytesToBean(byte[] contents,Class<T> tClass) throws IOException {
        if (contents == null) {
            return null;
        }

        return JSON_MAPPER.readValue(new String(contents,StandardCharsets.UTF_8.name()),tClass);
    }


    public static <T> T jsonToObject(String json, TypeReference<T> typeReference) throws IOException {
        if (StringUtils.isEmpty(json)) {
            return null;
        }

        return (T) JSON_MAPPER.readValue(json,typeReference);
    }


    public static String filter(String json,String node) throws IOException {
        JsonNode jsonNode = JSON_MAPPER.readTree(json);
        return jsonNode.get(node).toString();
    }


    public static JsonObject parseJsonStrict(String json) throws IOException {
        JsonReader reader = new JsonReader(new StringReader(json));
        JsonObject object = GSON_ADAPTER.read(reader);
        reader.hasNext();
        return object;
    }


    public static <T extends JsonNode> T parseJsonStrict(String json,Class<T> tClass) throws JsonProcessingException {
        if (StringUtils.isEmpty(json)) {
            return null;
        }

        return tClass.cast(JSON_MAPPER.readTree(json));
    }


    public static String toPrettyGson(String json) throws IOException {
        if (StringUtils.isEmpty(json)) {
            return null;
        }

        JsonReader reader = new JsonReader(new StringReader(json));
        JsonElement element = new Gson().getAdapter(JsonElement.class).read(reader);
        return new GsonBuilder().setPrettyPrinting().create().toJson(element);
    }


    public static String toPrettyJson(String json) throws JsonProcessingException {
        JsonNode node = parseJsonStrict(json,JsonNode.class);
        return node == null ? null : node.toPrettyString();
    }


    private JsonUtil() {
        throw new IllegalStateException("THIS IS A UTILITY CLASS");
    }
}
