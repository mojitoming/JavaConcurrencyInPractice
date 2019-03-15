package com.adam.programList.list2_3;

@SuppressWarnings("Duplicates")
public class ThreadStopping {
    public static void main(String[] args) {
        class StoppableThread extends Thread {
            private boolean stopped; // defaults to false

            @Override
            public void run() {
                synchronized (this) {
                    while (!stopped) {
                        System.out.println("running");
                    }
                }
            }

            synchronized void stopThread() {
                stopped = true;
            }
        }

        /*
         * Annotation:
         * 由于正在执行循环的这个线程已经获得当前ThreadStopping对象（通过synchronized(this)）的锁，
         * 这个循环不会终止。因为默认的主线程需要获得相同的锁，所有它在该对象上调用stopThread()方法的
         * 任意尝试都会导致自己被阻塞住。
         *
         * @Author: Adam Ming
         * @Date: Mar 11, 2019 at 8:16:14 PM
         */

        StoppableThread thd = new StoppableThread();
        thd.start();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        thd.stopThread();
    }
}
