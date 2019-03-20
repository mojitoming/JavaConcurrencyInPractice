package com.adam.programList.list7_2;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Annotation:
 * 用 Lock 和 Condition 获得同步功能
 *
 * @Author: Adam Ming
 * @Date: Mar 19, 2019 at 4:01:06 PM
 */
public class PC {
    public static void main(String[] args) {
        Shared s = new Shared();
        new Producer(s).start();
        new Consumer(s).start();
    }
}

class Shared {
    private char c;
    private volatile boolean available;
    private final Lock lock;
    private final Condition condition;

    Shared() {
        available = false;
        lock = new ReentrantLock();
        condition = lock.newCondition();
    }

    Lock getLock() {
        return lock;
    }

    char getSharedChar() {
        lock.lock();
        try {
            while (!available) { // 规避假唤醒情况，使用 while 代替 if
                condition.await();
            }

            available = false;
            condition.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }

        return c;
    }

    void setSharedChar(char c) {
        lock.lock();
        try {
            while (available) {
                condition.await();
            }
            this.c = c;
            available = true;
            condition.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}

class Producer extends Thread {
    private final Lock l;
    private final Shared s;

    Producer(Shared s) {
        this.s = s;
        l = s.getLock();
    }

    @Override
    public void run() {
        for (char ch = 'A'; ch <= 'Z'; ch++) {
            l.lock(); // 防止输出信息分离
            s.setSharedChar(ch);
            System.out.println(ch + " produced by producer.");
            l.unlock();
        }
    }
}

class Consumer extends Thread {
    private final Shared s;
    private final Lock l;

    Consumer(Shared s) {
        this.s = s;
        l = s.getLock();
    }

    @Override
    public void run() {
        char ch;
        do {
            l.lock(); // 防止输出信息分离
            ch = s.getSharedChar();
            System.out.println(ch + " consumed by consumer.");
            l.unlock();
        } while (ch != 'Z');
    }
}