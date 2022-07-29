package com.javarush.task.task26.task2601;

import java.util.Arrays;
import java.util.Comparator;

public class Solution {

    public static void main(String[] args) {
    }

    public static Integer[] sort(Integer[] array) {
        //сортировать по порядку
        Arrays.sort(array);

        //найти медиану
        int median;
        if(array.length%2 != 0)
            median = array[array.length/2];
        else
            median = ((array[array.length/2-1]) + (array[array.length/2])) / 2;

        //сортировать от медианы
        Comparator<Integer> medianComparator = new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return (o1 - median) + (o2 - median);
            }
        };
        Arrays.sort(array, medianComparator);
        return array;
    }
}
