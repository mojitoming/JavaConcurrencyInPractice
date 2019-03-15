package com.adam.programList.list2_4;

@SuppressWarnings("Duplicates")
public class ThreadStopping {
    public static void main(String[] args) {
        class StoppableThread extends Thread {
            private volatile boolean stopped; // defaults to false
            @Override
            public void run() {
                while (!stopped) {
                    System.out.println("running");
                }
            }

            void stopThread() {
                stopped = true;
            }
        }

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
