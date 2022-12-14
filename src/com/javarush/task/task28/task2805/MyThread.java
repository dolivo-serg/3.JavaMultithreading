package com.javarush.task.task28.task2805;

public class MyThread extends Thread {
    private static int count;

    {
        if (count == 0) {
            super.setPriority(Thread.MIN_PRIORITY);
            count++;
        }
        if (count > 10) {
            super.setPriority(1);
            count = 1;
        }

        if (count != 0)
            super.setPriority(count++);
    }

    public MyThread() {
    }

    public MyThread(Runnable target) {
        super(target);
    }

    public MyThread(ThreadGroup group, Runnable target) {
        super(group, target);
    }

    public MyThread(String name) {
        super(name);
    }

    public MyThread(ThreadGroup group, String name) {
        super(group, name);
    }

    public MyThread(Runnable target, String name) {
        super(target, name);
    }

    public MyThread(ThreadGroup group, Runnable target, String name) {
        super(group, target, name);
    }

    public MyThread(ThreadGroup group, Runnable target, String name, long stackSize) {
        super(group, target, name, stackSize);
    }
}
