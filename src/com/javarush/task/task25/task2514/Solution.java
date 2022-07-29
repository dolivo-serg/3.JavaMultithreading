package com.javarush.task.task25.task2514;

/* 
Первый закон Финэйгла: если эксперимент удался, что-то здесь не так...
*/

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Solution {
    public static class YieldRunnable implements Runnable {
        private int index;

        public YieldRunnable(int index) {
            this.index = index;
        }

        public void run() {
            System.out.println("begin-" + index);
            Thread.yield();
            Thread.yield();
            Thread.yield();
            Thread.yield();
            Thread.yield();
            System.out.println("end-" + index);

        }
    }

    public static void main(String[] args) {
        Thread thread1 = new Thread(new YieldRunnable(1));
        Thread thread2 = new Thread(new YieldRunnable(2));
        Thread thread3 = new Thread(new YieldRunnable(3));
        List<Thread> list = Stream.of(thread1, thread2, thread3).collect(Collectors.toList());
        list.forEach(Thread::start);
    }
}
