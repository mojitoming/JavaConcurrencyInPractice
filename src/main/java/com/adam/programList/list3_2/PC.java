package com.adam.programList.list3_2;

/**
 * Annotation:
 * 生产者 消费者 better
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
 * 增加同步块，保证打印正常
 *
 * 生产者和消费者通过将各自的方法对包装到一个同步块的方式修正，这个同步块
 * 同步在 s 所引用的 Shared 对象之上。相同的对象，相同的锁。
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
            synchronized (s) {
                s.setSharedChar(ch);
                System.out.println(ch + " produced by producer.");
            }
        }
    }
}

/*
 * Annotation:
 * 消费者
 *
 * 增加同步块，保证打印正常
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
            synchronized (s) {
                ch = s.getSharedChar();
                System.out.println(ch + " consumed by consumer.");
            }
        } while (ch != 'Z');
    }
}

