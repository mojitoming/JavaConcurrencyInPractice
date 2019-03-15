package com.adam.programList.list3_1;

/**
 * Annotation:
 * 生产者 消费者
 *
 * @Author: Adam Ming
 * @Date: Mar 12, 2019 at 3:48:41 PM
 */
public class PC {
    public static void main(String[] args) {
        Shared s = new Shared();
        new Producer(s).start();
        new Consumer(s).start();
    }
}

/*
 * Annotation:
 * 共享对象
 *
 * @Author: Adam Ming
 * @Date: Mar 12, 2019 at 3:19:26 PM
 */
@SuppressWarnings("Duplicates")
class Shared {
    private char c;
    private volatile boolean writable = true;

    synchronized void setSharedChar(char c) {
        while (!writable) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        this.c = c;
        writable = false;
        notify();
    }

    synchronized char getSharedChar() {
        while (writable) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        writable = true;
        notify();

        return c;
    }
}

/*
 * Annotation:
 * 生产者
 *
 * @Author: Adam Ming
 * @Date: Mar 12, 2019 at 3:22:29 PM
 */
class Producer extends Thread {
    private final Shared s;

    Producer(Shared s) {
        this.s = s;
    }

    @Override
    public void run() {
        for (char ch = 'A'; ch <= 'Z'; ch++) {
            s.setSharedChar(ch);
            System.out.println(ch + " produced by producer.");
        }
    }
}

/*
 * Annotation:
 * 消费者
 *
 * @Author: Adam Ming
 * @Date: Mar 12, 2019 at 3:29:32 PM
 */
class Consumer extends Thread {
    private final Shared s;

    Consumer(Shared s) {
        this.s = s;
    }

    @Override
    public void run() {
        char ch;
        do {
            ch = s.getSharedChar();
            System.out.println(ch + " consumed by consumer.");
        } while (ch != 'Z');
    }
}

