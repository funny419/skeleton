package com.funny.utils.helper;

import com.funny.utils.DateUtil;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class DateConverter extends TypeAdapter<Date> {
    private String pattern = DateUtil.FULL_KOREAN_PATTERN;





    public DateConverter(String pattern) {
        if (StringUtils.isNotEmpty(pattern)) {
            this.pattern = pattern;
        }
    }


    @Override
    public void write(JsonWriter out,Date value) throws IOException {
        if (value == null) {
            out.nullValue();
            return;
        }

        String dateFormatAsString = DateUtil.formatDate(value,pattern);
        out.value(dateFormatAsString);
    }


    @Override
    public Date read(JsonReader in) throws IOException {
        if (in.peek() == JsonToken.NULL) {
            in.nextNull();
            return null;
        }

        String json = in.nextString();
        if (json.matches("\\d{4}-\\d{1,2}-\\d{1,2}")) {
            pattern = DateUtil.FULL_KOREAN_PATTERN;
        }

        Date date = null;
        try {
            date = new SimpleDateFormat(pattern).parse(json);
        } catch (ParseException ignored) {}

        return date;
    }
}
