package com.adam.programList.list6_3;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Exchanger;

/**
 * Annotation:
 * 使用一个交换器来交换缓冲区
 *
 * @Author: Adam Ming
 * @Date: Mar 14, 2019 at 2:56:39 PM
 */
public class ExchangerDemo {
    final static Exchanger<DataBuffer> exchanger = new Exchanger<>();
    final static DataBuffer initialEmptyBuffer = new DataBuffer();
    final static DataBuffer initialFullBuffer = new DataBuffer("I");

    public static void main(String[] args) {
        class FillingLoop implements Runnable {
            int count = 0;

            @Override
            public void run() {
                DataBuffer currentBuffer = initialEmptyBuffer;

                try {
                    while (true) {
                        addToBuffer(currentBuffer);
                        if (currentBuffer.isFull()) {
                            System.out.println("filling thread wants to exchange");
                            currentBuffer = exchanger.exchange(currentBuffer); // 交换缓冲区
                            System.out.println("filling thread receives exchange");
                        }
                    }
                } catch (InterruptedException ie) {
                    System.out.println("filling thread interrupted");
                }
            }

            void addToBuffer(DataBuffer buffer) {
                String item = "NI" + count++;
                System.out.println("Adding: " + item);
                buffer.add(item);
            }
        }

        class EmptyingLoop implements Runnable {
            @Override
            public void run() {
                DataBuffer currentBuffer = initialFullBuffer;

                try {
                    while (true) {
                        takeFromBuffer(currentBuffer);
                        if (currentBuffer.isEmpty()) {
                            System.out.println("emptying thread wants to exchange");
                            currentBuffer = exchanger.exchange(currentBuffer); // 交换缓冲区
                            System.out.println("emptying thread receives exchange");
                        }
                    }
                } catch (InterruptedException ie) {
                    System.out.println("emptying thread interrupted");
                }
            }

            void takeFromBuffer(DataBuffer buffer) {
                System.out.println("taking: " + buffer.remove());
            }
        }

        new Thread(new EmptyingLoop()).start();
        new Thread(new FillingLoop()).start();
    }
}

/*
 * Annotation:
 * 缓冲区
 *
 * @Author: Adam Ming
 * @Date: Mar 14, 2019 at 2:56:25 PM
 */
class DataBuffer {
    private final static int MAX_ITEMS = 10;
    private final List<String> items = new ArrayList<>();

    public DataBuffer() {
    }

    public DataBuffer(String prefix) {
        for (int i = 0; i < MAX_ITEMS; i++) {
            String item = prefix + i;
            System.out.printf("Adding %s%n", item);
            items.add(item);
        }
    }

    synchronized void add(String s) {
        if (!isFull()) {
            items.add(s);
        }
    }

    synchronized String remove() {
        if (!isEmpty()) {
            return items.remove(0);
        }

        return null;
    }

    synchronized boolean isFull() {
        return items.size() == MAX_ITEMS;
    }

    synchronized boolean isEmpty() {
        return items.size() == 0;
    }
}
