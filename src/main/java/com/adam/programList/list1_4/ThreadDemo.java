package com.adam.programList.list1_4;

public class ThreadDemo {
    public static void main(String[] args) {
        Runnable r = () -> {
            int count = 0;
            String name = Thread.currentThread().getName();
            while (!Thread.interrupted()) {
                System.out.println(name + ": " + (count++));
            }
        };

        Thread thA = new Thread(r);
        Thread thB = new Thread(r);

        thA.start();
        thB.start();

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        thA.interrupt();
        thB.interrupt();
    }
}
