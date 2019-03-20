package com.adam.programList.list8_2;

/**
 * Annotation:
 * 借助 synchronized，以线程安全的方式返回唯一的标识符
 *
 * @Author: Adam Ming
 * @Date: Mar 20, 2019 at 4:55:27 PM
 */
public class ID {
    private static volatile long nextID = 1;

    static synchronized long getNextID() {
        return nextID++;
    }
}
