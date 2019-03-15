package com.adam.programList.list4_1;

public class ExceptionThread {

    public static void main(String[] args) {
        Runnable r = () -> {
            int x = 1 / 0;
        };

        Thread thd = new Thread(r);
        thd.start();
    }
}
