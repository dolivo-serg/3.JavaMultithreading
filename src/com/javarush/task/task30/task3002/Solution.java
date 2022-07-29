package com.javarush.task.task30.task3002;

/* 
Осваиваем методы класса Integer
*/

public class Solution {

    public static void main(String[] args) {
        System.out.println(convertToDecimalSystem("0x16")); //22
        System.out.println(convertToDecimalSystem("012"));  //10
        System.out.println(convertToDecimalSystem("0b10")); //2
        System.out.println(convertToDecimalSystem("62"));   //62
    }

    public static String convertToDecimalSystem(String s) {
        if (s.charAt(0)=='0') {
            if (s.charAt(1) == 'x') { //16
                return "" + Integer.parseInt(s.substring(2), 16);
            } else if (s.charAt(1) == 'b') { // 2
                return "" + Integer.parseInt(s.substring(2), 2);
            } else { //8
                return "" + Integer.parseInt(s.substring(1), 8);
            }
        } else { //10
            return "" + s;
        }
    }
}
