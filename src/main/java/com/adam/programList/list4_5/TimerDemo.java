package com.adam.programList.list4_5;

import java.util.Timer;
import java.util.TimerTask;

public class TimerDemo {
    public static void main(String[] args) {
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                System.out.println("alarm going off");
                System.exit(0);
            }
        };

        Timer timer = new Timer();
        timer.schedule(task, 2000);
    }
}