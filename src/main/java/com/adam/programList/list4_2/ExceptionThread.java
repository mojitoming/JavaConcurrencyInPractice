package com.adam.programList.list4_2;

public class ExceptionThread {
    public static void main(String[] args) {
        Runnable r = () -> {
            int x = 1 / 0;
        };

        Thread thd = new Thread(r);
        Thread.UncaughtExceptionHandler uceh;
        uceh = new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread t, Throwable e) {
                System.out.println("Caught throwable " + e + " for thread " + t);
            }
        };

        thd.setUncaughtExceptionHandler(uceh);

        uceh = new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread t, Throwable e) {
                System.out.println("Default uncaught exception handler");
                System.out.println("Caught throwable " + e + " for thread " + t);
            }
        };

        thd.setDefaultUncaughtExceptionHandler(uceh);

        thd.start();
    }
}
