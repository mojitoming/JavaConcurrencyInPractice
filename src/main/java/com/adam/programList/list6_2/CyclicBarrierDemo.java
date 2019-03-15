package com.adam.programList.list6_2;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * Annotation:
 * 使用同步屏障把一个任务分级为多个子任务
 *
 * @Author: Adam Ming
 * @Date: Mar 14, 2019 at 10:51:49 AM
 */
public class CyclicBarrierDemo {
    public static void main(String[] args) {
        float[][] matrix = new float[3][3];
        int counter = 0;
        for (int row = 0; row < matrix.length; row++) {
            for (int col = 0; col < matrix[row].length; col++) {
                matrix[row][col] = counter++;
            }
        }
        dump(matrix);
        System.out.println();
        Solver solver = new Solver(matrix);
        System.out.println();
        dump(matrix);
    }

    static void dump(float[][] matrix) {
        for (int row = 0; row < matrix.length; row++) {
            for (int col = 0; col < matrix[row].length; col++) {
                System.out.print(matrix[row][col] + " ");
            }
            System.out.println();
        }
    }
}

class Solver {
    final int N;
    final float[][] data;
    final CyclicBarrier barrier;

    class Worker implements Runnable {
        int myRow;
        boolean done = false;

        Worker(int row) {
            myRow = row;
        }

        boolean done() {
            return done;
        }

        void processRow(int myRow) {
            System.out.println("Processing row: " + myRow);
            for (int i = 0; i < N; i++) {
                data[myRow][i] *= 10;
            }
            done = true;
        }

        @Override
        public void run() {
            while (!done()) {
                processRow(myRow);
            }

            try {
                barrier.await();
            } catch (InterruptedException ie) {
                return;
            } catch (BrokenBarrierException bbe) {
                return;
            }
        }
    }

    public Solver(float[][] matrix) {
        data = matrix;
        N = matrix.length;
        barrier = new CyclicBarrier(N, () -> mergeRows());

        for (int i = 0; i < N; i++) {
            new Thread(new Worker(i)).start();
        }

        waitUntilDone();
    }

    void mergeRows() {
        System.out.println("merging");
        synchronized ("abc") {
            "abc".notify();
        }
    }

    void waitUntilDone() {
        synchronized ("abc") {
            try {
                System.out.println("main thread waiting");
                "abc".wait();
                System.out.println("main thread notified");
            } catch (InterruptedException ie) {
                System.out.println("main thread interrupted");
            }
        }
    }
}
