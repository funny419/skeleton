package com.funny.utils.helper;

import com.funny.utils.CollectionUtil;
import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;

import java.util.Collections;
import java.util.List;


public class GsonInclusion implements ExclusionStrategy {
    private List<String> inclusionFields;


    public GsonInclusion addInclusionFields(String... fieldName) {
        if (CollectionUtils.isEmpty(inclusionFields)) {
            inclusionFields = CollectionUtil.createArrayList();
        }

        if (ArrayUtils.isNotEmpty(fieldName)) {
            Collections.addAll(inclusionFields, fieldName);
        }

        return this;
    }


    @Override
    public boolean shouldSkipField(FieldAttributes f) {
        String fieldName = f.getName();
        return !inclusionFields.contains(fieldName);
    }


    @Override
    public boolean shouldSkipClass(Class<?> clazz) {
        return false;
    }


    public List<String> getinclusionFields() {
        return inclusionFields;
    }


    public void setinclusionFields(List<String> inclusionFields) {
        this.inclusionFields = inclusionFields;
    }
}
