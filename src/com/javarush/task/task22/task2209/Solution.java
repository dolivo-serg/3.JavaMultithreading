package com.javarush.task.task22.task2209;

import java.io.*;
import java.util.*;

/*
Составить цепочку слов
*/

public class Solution {
    public static void main(String[] args) throws IOException {
        List<String> list = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
             BufferedReader fileReader = new BufferedReader(new InputStreamReader(new FileInputStream(reader.readLine())))) {
            while (fileReader.ready()) {
                list.add(fileReader.readLine());
            }
        }
        List<String> resultList = new ArrayList<>();
        for (String line : list) {
            StringTokenizer tokenizer = new StringTokenizer(line);
            while (tokenizer.hasMoreTokens()) {
                resultList.add(tokenizer.nextToken());
            }
        }
        StringBuilder result = getLine(resultList.toArray(new String[0]));
        System.out.println(result.toString());
    }

    public static StringBuilder getLine(String... words) {
        List<Integer> list = new ArrayList<>();
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < words.length; i++) {
            list.add(i);
            if (getResult(list, words)) {
                for (Integer index : list) {
                    result.append(words[index]).append(" ");
                }
                return result;
            }
            list.remove(Integer.valueOf(i));
        }
        return result;
    }

    //поиск следуещего слова и следующего и следующего...
        private static boolean getResult(List<Integer> list, String... words) {
            if (list.size() == words.length) {
                return true;
            }
            for (int i = 0; i < words.length; i++) {
                if (isRightNextWord(list, words[i], words)) {
                    list.add(i);
                    if (getResult(list, words)) {
                        return true;
                    }
                    list.remove(Integer.valueOf(i));
                }
            }
            return false;
        }

    //поиск подходящего следующего слова
        private static boolean isRightNextWord(List<Integer> list, String word, String... words) {
            for (Integer integer : list) {
                if (words[integer].equals(word)) {
                    return false;
                }
            }
            return isMatch(words[getLastElement(list)], word);
        }

    //метод для проверки последней и первой буквы 2х слов
    private static boolean isMatch(String firstWord, String secondWord){
        char lastFofFirstWord = firstWord.toLowerCase().charAt(firstWord.length()-1);
        char firstFofSecondWord = secondWord.toLowerCase().charAt(0);
        return lastFofFirstWord == firstFofSecondWord;
    }

    //какая-то магия
    private static <T> T getLastElement(List<? extends T> list) {
        return list.get(list.size() - 1);
    }
}
