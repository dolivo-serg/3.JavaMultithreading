package com.javarush.task.task22.task2212;

/* 
Проверка номера телефона
*/

public class Solution {
    public static boolean checkTelNumber(String telNumber) {
        if(telNumber == null)
            return false;
//        //номер может содержать только цифры, '+', '(' и ')'
//        if(telNumber.matches(".*[^+0-9()].*"))
//            return false;
//        //если номер начинается с '+', то он содержит 12 цифр +
//        if(telNumber.startsWith("+") &&
//                telNumber.replaceAll("[^0-9]", "").toCharArray().length != 12){
//            return false;
//        }
//        //если номер начинается с цифры или открывающей скобки, то он содержит 10 цифр
//        if(telNumber.matches("\\b([0-9].*)|\\b([(].*)") &&
//                telNumber.replaceAll("[^0-9]", "").toCharArray().length != 10)
//            return false;
//
//        if(telNumber.matches(".*\\(.*")){
//            if(telNumber.matches("^(\\d*)(\\(\\d{3}\\))(\\d*)$")||
//                    telNumber.matches("^(\\+)(\\d*)(\\(\\d{3}\\))(\\d*)$"))
//                return true;
//        }
//        if(!telNumber.matches(".*\\(.*")){
//            if(telNumber.matches("^\\d{10}$")||
//                    telNumber.matches("^(\\+)(\\d{12})$"))
//                return true;
//        }
        return (telNumber.matches("^\\+(\\d[()]?){12}$") || telNumber.matches("^([()]?\\d){10}$"))
                && telNumber.matches("^(\\+)?(\\d+)?(\\(\\d{3}\\))?\\d+$");

    }

    public static void main(String[] args) {
        System.out.println(checkTelNumber("+380501234567"));
    }
}
