package com.funny.utils.helper;

import com.funny.utils.CollectionUtil;
import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;

import java.util.Arrays;
import java.util.List;


public class GsonExclusion implements ExclusionStrategy {
    private List<String> exclusionFields;
    private List<Class> exclusionClasses;




    public GsonExclusion addExclusionField(String... filedName) {
        if (CollectionUtils.isEmpty(exclusionFields)) {
            exclusionFields = CollectionUtil.createArrayList();
        }

        if (ArrayUtils.isNotEmpty(filedName)) {
            for (int i=0,cnt=filedName.length;i<cnt;i++) {
                exclusionFields.add(filedName[i]);
            }
        }

        return this;
    }


    public GsonExclusion addExclusionClass(Class<?>... classes) {
        if (CollectionUtils.isEmpty(exclusionClasses)) {
            exclusionClasses = CollectionUtil.createArrayList();
        }

        exclusionClasses.addAll(Arrays.asList(classes));
        return this;
    }


    @Override
    public boolean shouldSkipField(FieldAttributes f) {
        if (CollectionUtils.isEmpty(exclusionFields)) {
            return false;
        }

        String fieldName = f.getName();
        return exclusionFields.contains(fieldName);
    }


    @Override
    public boolean shouldSkipClass(Class<?> clazz) {
        if (CollectionUtils.isEmpty(exclusionClasses)) {
            return false;
        }

        return exclusionClasses.contains(clazz);
    }


    public List<String> getExclusionFields() {
        return exclusionFields;
    }


    public void setExclusionFields(List<String> exclusionFields) {
        this.exclusionFields = exclusionFields;
    }
}
