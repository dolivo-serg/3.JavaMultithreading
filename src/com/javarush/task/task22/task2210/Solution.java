package com.javarush.task.task22.task2210;

/*
StringTokenizer
*/

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Solution {
    public static void main(String[] args) {

    }

    public static String[] getTokens(String query, String delimiter) {
        List<String> list = new ArrayList<>();
        StringTokenizer stringTokenizer = new StringTokenizer(query, delimiter);
        while (stringTokenizer.hasMoreTokens()){
            list.add(stringTokenizer.nextToken());
        }
        return list.toArray(new String[0]);
    }
}
