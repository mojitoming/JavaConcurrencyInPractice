package com.adam.programList.list8_5;

/**
 * Annotation:
 * 表示矩阵的类
 *
 * @Author: Adam Ming
 * @Date: Mar 20, 2019 at 6:42:31 PM
 */
public class Matrix {
    private final int[][] matrix;

    public Matrix(int nrows, int ncols) {
        matrix = new int[nrows][ncols];
    }

    public int getCols() {
        return matrix[0].length;
    }

    public int getRows() {
        return matrix.length;
    }

    public int getValue(int row, int col) {
        return matrix[row][col];
    }

    public void setValue(int row, int col, int value) {
        matrix[row][col] = value;
    }
}
