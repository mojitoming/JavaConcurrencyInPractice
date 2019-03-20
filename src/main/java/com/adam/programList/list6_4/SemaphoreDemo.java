package com.adam.programList.list6_4;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * Annotation:
 * 使用计数信号量去控制对一组条目的访问
 *
 * @Author: Adam Ming
 * @Date: Mar 15, 2019 at 3:43:54 PM
 */
public class SemaphoreDemo {
    public static void main(String[] args) {
        final Pool pool = new Pool();
        Runnable r = () -> {
            String name = Thread.currentThread().getName();

            try {
                while (true) {
                    String item;
                    System.out.println(name + " acquiring " + (item = pool.getItem()));
                    Thread.sleep(200 + (int) (Math.random() * 100));
                    System.out.println(name + " putting back " + item);
                    pool.putItem(item);
                }
            } catch (InterruptedException ie) {
                System.out.println(name + "interrupted");
            }
        };

        ExecutorService[] executors = new ExecutorService[Pool.MAX_AVAILABLE];
        for (ExecutorService executor : executors) {
            executor = Executors.newSingleThreadExecutor();
            executor.execute(r);
        }
    }
}

final class Pool {
    public static final int MAX_AVAILABLE = 10;
    private final Semaphore available = new Semaphore(MAX_AVAILABLE, true);
    private final String[] items;
    private final boolean[] used = new boolean[MAX_AVAILABLE];

    Pool() {
        items = new String[MAX_AVAILABLE];
        for (int i = 0; i < items.length; i++) {
            items[i] = "I" + i;
        }
    }

    String getItem() throws InterruptedException {
        available.acquire();

        return getNextAvailableItem();
    }

    void putItem(String item) {
        if (markAsUnused(item)) {
            available.release();
        }
    }

    private synchronized String getNextAvailableItem() {
        for (int i = 0; i < MAX_AVAILABLE; i++) {
            if (!used[i]) {
                used[i] = true;

                return items[i];
            }
        }

        return null;
    }

    private synchronized boolean markAsUnused(String item) {
        for (int i = 0; i < MAX_AVAILABLE; i++) {
            if (item.equals(items[i])) {
                if (used[i]) {
                    used[i] = false;

                    return true;
                } else {
                    return false;
                }
            }
        }

        return false;
    }
}