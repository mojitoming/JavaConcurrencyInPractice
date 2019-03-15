package com.adam.programList.list1_2;

/**
 * Annotation:
 * 清单 1-2  线程中断示例
 *
 * @Author: Adam Ming
 * @Date: 2018/9/14 5:44 PM
 */
public class ThreadDemo {
    public static void main(String[] args) {
        Runnable r = () -> {
            String name = Thread.currentThread().getName();
            int count = 0;
            while (!Thread.interrupted()) {
                System.out.println(name + ": " + count++);
            }

        };

        Thread thdA = new Thread(r);
        Thread thdB = new Thread(r);

        thdA.start();
        thdB.start();

        while (true) {
            double n = Math.random();
            if (n >= 0.49999999 && n <= 0.50000001) break;
        }

        thdA.interrupt();
        thdB.interrupt();
    }
}
