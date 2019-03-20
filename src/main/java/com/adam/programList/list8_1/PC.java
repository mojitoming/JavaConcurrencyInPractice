package com.adam.programList.list8_1;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Annotation:
 * 第 3 章 PC 应用程序阻塞队列的等价实现
 * modified
 *
 * @Author: Adam Ming
 * @Date: Mar 20, 2019 at 3:23:02 PM
 */
public class PC {
    public static void main(String[] args) {
        final BlockingQueue<Character> bq = new ArrayBlockingQueue<>(26);
        final ExecutorService executor = Executors.newFixedThreadPool(2);

        Runnable producer = () -> {
            for (char ch = 'A'; ch <= 'Z'; ch++) {
                try {
                    synchronized ("a") {
                        bq.put(ch);
                        System.out.printf("%c produced by producer.%n", ch);
                    }
                    Thread.sleep(1); // 睡眠 1 毫秒给操作其他任务的线程出现机会
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        Runnable consumer = () -> {
            char ch = '\0';
            do {
                try {
                    synchronized ("b") {
                        ch = bq.take();
                        System.out.printf("%c consumed by consumer.%n", ch);
                    }
                    Thread.sleep(1); // 睡眠 1 毫秒给操作其他任务的线程出现机会
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } while (ch != 'Z');
            executor.shutdownNow();
        };

        executor.execute(producer);
        executor.execute(consumer);
    }
}
