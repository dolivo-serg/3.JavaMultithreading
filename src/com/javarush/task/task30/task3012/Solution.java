package com.javarush.task.task30.task3012;

/* 
Получи заданное число
*/

public class Solution {
    public static void main(String[] args) {
        Solution solution = new Solution();
        solution.createExpression(74);
    }

    public void createExpression(int number) {
        String[] allowedNumbers = {"1", "3", "9", "27", "81", "243", "729", "2187"};
        StringBuilder result = new StringBuilder(number + " = ");
        String inTernarySymmetric = convertToTernarySymmetric(number);
        for (int i = 0; i < inTernarySymmetric.length(); i++) {
            if (inTernarySymmetric.charAt(i) != '0') {
                result.append(inTernarySymmetric.charAt(i));
                result.append(" ");
                result.append(allowedNumbers[i]);
                result.append(" ");
            }
        }
        System.out.println(result.toString().trim());
    }

    private String convertToTernarySymmetric(int number) {
        StringBuilder sb = new StringBuilder();
        while (number >= 2) {
            if (number % 3 == 0) {
                sb.append("0");
                number /= 3;
            } else if (number % 3 == 1) {
                sb.append("+");
                number /= 3;
            } else {
                sb.append("-");
                number = (number / 3) + 1;
            }
        }
        if (number == 1) {
            sb.append("+");
        }
        return sb.toString();
    }

}