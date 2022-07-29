package com.javarush.task.task22.task2211;

import java.io.*;

/* 
Смена кодировки
*/

public class Solution {
    public static void main(String[] args) throws IOException {
        if (args.length > 0) {
            try(FileInputStream input = new FileInputStream(args[0]);
                FileOutputStream output = new FileOutputStream(args[1])) {
                byte[] buffer = new byte[input.available()];
                    input.read(buffer);

                String s = new String(buffer, "Windows-1251");
                output.write(s.getBytes("UTF-8"));
            }
        }
    }
}
