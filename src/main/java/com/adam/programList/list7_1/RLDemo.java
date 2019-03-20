package com.adam.programList.list7_1;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Annotation:
 * 用重入锁获得同步功能
 *
 * @Author: Adam Ming
 * @Date: Mar 19, 2019 at 4:00:16 PM
 */
public class RLDemo {
    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(2);
        final ReentrantLock lock = new ReentrantLock();

        class Worker implements Runnable {
            private final String name;

            Worker(String name) {
                this.name = name;
            }

            @Override
            public void run() {
                lock.lock();
                try {
                    if (lock.isHeldByCurrentThread()) {
                        System.out.printf("Thread %s entered critical section.%n", name);
                    }
                    System.out.printf("Thread %s performing work.%n", name);
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException ie) {
                        ie.printStackTrace();
                    }

                    System.out.printf("Thread %s finished working.%n", name);
                } finally {
                    lock.unlock();
                }
            }
        }

        executor.execute(new Worker("ThdA"));
        executor.execute(new Worker("ThdB"));

        executor.shutdown();
        try {
            while (!executor.awaitTermination(2, TimeUnit.SECONDS)) {
                System.out.println("Services is still working...");
            }
            System.out.println("All services have done!");
        } catch (InterruptedException ie) {
            ie.printStackTrace();
        }
        /*
        try {
            executor.awaitTermination(5, TimeUnit.SECONDS); // 主线程等待5s。原始代码，个人感觉 so so
        } catch (InterruptedException ie) {
            ie.printStackTrace();
        }

        executor.shutdownNow();
        */
    }
}
