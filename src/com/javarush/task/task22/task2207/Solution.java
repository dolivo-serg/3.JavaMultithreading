package com.javarush.task.task22.task2207;

import java.io.*;
import java.util.LinkedList;
import java.util.List;

/*
Обращенные слова
*/

public class Solution {
    public static List<Pair> result = new LinkedList<>();

    public static void main(String[] args) throws IOException {
        try(BufferedReader address = new BufferedReader(new InputStreamReader(System.in));
            FileInputStream fis = new FileInputStream(address.readLine())){
            ByteArrayOutputStream boas = new ByteArrayOutputStream();
            int b;
            while((b = fis.read())!= -1){
                boas.write(b);
            }
            String[]array = boas.toString("UTF-8").split(" ");
            for (int index = 0; index < array.length; index++){
                StringBuilder reverse = new StringBuilder(array[index]).reverse();
                for (int i = 0; i < array.length; i++){
                    if(i==index) continue;
                    if(array[index].equals("")) continue;
                    if(array[i].equals(reverse.toString())) {
                        result.add(new Pair(array[index], reverse.toString()));
                        array[index] = "";
                        array[i] = "";
                        break;
                    }
                }
                array[index] = "";
            }
        }
        System.out.println(result);
    }

    public static class Pair {
        String first;
        String second;

        public Pair() {
        }

        public Pair(String word, String reversWord) {
            this.first = word;
            this.second = reversWord;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Pair pair = (Pair) o;

            if (first != null ? !first.equals(pair.first) : pair.first != null) return false;
            return second != null ? second.equals(pair.second) : pair.second == null;

        }

        @Override
        public int hashCode() {
            int result = first != null ? first.hashCode() : 0;
            result = 31 * result + (second != null ? second.hashCode() : 0);
            return result;
        }

        @Override
        public String toString() {
            return first == null && second == null ? "" :
                    first == null ? second :
                            second == null ? first :
                                    first.compareTo(second) < 0 ? first + " " + second : second + " " + first;

        }
    }

}
