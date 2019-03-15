package com.adam.programList.list1_1;

/**
 * Annotation:
 * 清单 1-1  线程基础示例
 *
 * @Author: Adam Ming
 * @Date: Sep 14, 2018 at 4:36:57 PM
 */
public class ThreadDemo {
    public static void main(String[] args) {
        boolean isDaemon = args.length != 0;

        /*Runnable r = new Runnable() { // Runnable r = () -> {};
            @Override
            public void run() {
                Thread thd = Thread.currentThread();
                while (true) {
                    System.out.printf("%s is %salive and in %s.\n", thd.getName(), thd.isAlive() ? "" : "not ", thd.getState());
                }
            }
        };*/

        Runnable r = () -> {
            Thread thd = Thread.currentThread();
            while (true) {
                System.out.printf("%s is %salive and in %s state%n", thd.getName(), thd.isAlive() ? "" : "not ", thd.getState());
            }
        };

        Thread t1 = new Thread(r, "thd1");
        if (isDaemon) t1.setDaemon(true);
        System.out.printf("%s is %salive and in %s state%n", t1.getName(), t1.isAlive() ? "" : "not ", t1.getState());

        Thread t2 = new Thread(r);
        t2.setName("thd2");
        if (isDaemon) t2.setDaemon(true);
        System.out.printf("%s is %salive and in %s state%n", t2.getName(), t2.isAlive() ? "" : "not ", t2.getState());

        t1.start();
        t2.start();
    }
}
