package com.adam.programList.list6_5;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.Phaser;

/**
 * Annotation:
 * 使用一个 Phaser 来控制一个一次性动作，该动作作用于可变数量的线程上。
 *
 * @Author: Adam Ming
 * @Date: Mar 15, 2019 at 5:05:42 PM
 */
public class PhaserDemo {
    public static void main(String[] args) {
        List<Runnable> tasks = new ArrayList<>();
        Runnable runnable = () -> System.out.printf("%s running at %d%n", Thread.currentThread().getName(), System.currentTimeMillis());
        tasks.add(runnable);
        tasks.add(runnable);

        runTasks(tasks);
    }

    static void runTasks(List<Runnable> tasks) {
        final Phaser phaser = new Phaser(1); // "1" (register self)

        // create and start threads
        for (Runnable task : tasks) {
            phaser.register();

            Runnable r = () -> {
                try {
                    Thread.sleep(50 + (int) (Math.random() * 300));
                } catch (InterruptedException ie) {
                    System.out.println("interrupted thread");
                }
                phaser.arriveAndAwaitAdvance(); // await the ... creation of ... all tasks

                task.run();
            };

            Executors.newSingleThreadExecutor().execute(r);
        }

        // allow threads to start and deregister self
        phaser.arriveAndDeregister();
    }
}
