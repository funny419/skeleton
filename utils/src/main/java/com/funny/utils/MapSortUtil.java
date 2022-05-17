package com.funny.utils;

import java.util.*;


public class MapSortUtil {
    public Map<String,Integer> getMapSortedByKey(Map<String,Integer> map) {
        Map<String,Integer> treeMap = new TreeMap<>();
        treeMap.putAll(map);
        return treeMap;
    }


    public List<Map.Entry<String,Integer>> getEntrysSortedByValue(Map<String,Integer> map) {
        List<Map.Entry<String,Integer>> entrys = new ArrayList(map.entrySet());
        Collections.sort(entrys,new CustomComparator());
        return entrys;
    }


    static class CustomComparator implements Comparator<Map.Entry<String,Integer>> {
        @Override
        public int compare(Map.Entry<String,Integer> o1,Map.Entry<String,Integer> o2) {
            return o1.getValue() - o2.getValue();
        }
    }
}
