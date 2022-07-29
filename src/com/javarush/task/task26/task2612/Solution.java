package com.javarush.task.task26.task2612;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/* 
Весь мир играет комедию
*/

public class Solution {
    private Lock lock = new ReentrantLock();

    public void someMethod() {
        try {
            if (lock.tryLock()) {
                actionIfLockIsFree();
            } else {
                actionIfLockIsBusy();
                return;
            }
        }catch (Exception ignore){}
            lock.unlock();
    }

    public void actionIfLockIsFree() {
    }

    public void actionIfLockIsBusy() {
    }
}
