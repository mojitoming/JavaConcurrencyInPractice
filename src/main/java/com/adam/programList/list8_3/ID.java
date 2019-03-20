package com.adam.programList.list8_3;

import java.util.concurrent.atomic.AtomicLong;

public class ID {
    private static AtomicLong nextID = new AtomicLong(1);

    static long getNextId() {
        return nextID.getAndIncrement();
    }
}
