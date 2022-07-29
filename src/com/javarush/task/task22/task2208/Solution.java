package com.javarush.task.task22.task2208;

import java.util.LinkedHashMap;
import java.util.Map;

/* 
Формируем WHERE
*/

public class Solution {
    public static void main(String[] args) {
        Map<String, String> map = new LinkedHashMap<>();
        map.put("key1", "value1");
        map.put("key2", "value2");
        map.put("key3", "value3");
        System.out.println(getQuery(map));
    }

    public static String getQuery(Map<String, String> params) {
        StringBuilder result = new StringBuilder();
        for (Map.Entry<String, String> pair : params.entrySet()){
            if(pair.getValue()!=null){
                result.append(String.format("%s = '%s' and ", pair.getKey(), pair.getValue()));
            }
        }
        if(result.length() == 0) return "";
        return result.toString().substring(0, result.lastIndexOf(" and "));
    }
}
