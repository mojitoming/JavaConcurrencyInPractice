package com.adam;

/**
 * Annotation:
 * 使用 Java 监视器模式的线程安全计数器
 *
 * @Author: Adam Ming
 * @Date: 13/11/2017 3:11 PM
 */
public final class Counter {
    private long value = 0;

    public synchronized long getValue() {
        return value;
    }

    public synchronized long increment() {
        if (value == Long.MAX_VALUE) {
            throw new IllegalStateException("counter overflow");
        }

        return ++value;
    }
}
