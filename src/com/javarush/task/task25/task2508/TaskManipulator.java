package com.javarush.task.task25.task2508;

public class TaskManipulator implements Runnable, CustomThreadManipulator {
    Thread thread;

    @Override
    public void run() {
        try {
            while (!thread.getState().equals(Thread.State.TERMINATED)) {
                System.out.println(thread.getName());
                Thread.sleep(100);
            }
        } catch (InterruptedException e) {
        }
    }

    @Override
    public void start(String threadName) {
        Thread thread = new Thread(this, threadName);
        this.thread = thread;
        thread.start();
    }

    @Override
    public void stop() {
        thread.interrupt();
    }
}
