package com.whitedeveloper.matrix.operationModules;

public class MultiplicationMatrix {

    private double[][] matrixA;
    private double[][] matrixB;

    public MultiplicationMatrix(double[][] matrixA, double[][] matrixB) {
        this.matrixA = matrixA;
        this.matrixB = matrixB;
    }

    public double[][] multiplicationMatrices() {
        double[][] result = new double[matrixA[0].length][matrixB[0].length];

        for (int i = 0; i < result.length; i++)
            for (int j = 0; j < result[i].length; j++) {
                result[i][j] = calculateMultiplicationColumnOnRow(matrixA[i], getColumnFromMatrix(matrixB, j));
            }
        return result;
    }

    public static double[] getColumnFromMatrix(double[][] matrix, int numColumn) {
        double[] column = new double[matrix.length];

        for (int i = 0; i < column.length; i++)
            column[i] = matrix[i][numColumn];

        return column;
    }


    public static double calculateMultiplicationColumnOnRow(double[] column, double[] row) {
        double sum = 0;

        for (int i = 0; i < column.length; i++)
            sum += column[i] * row[i];
        return sum;
    }

    public boolean isAvailableForAddition() {
        int columnsMatrixA = matrixA[0].length;

        int rowMatrixB = matrixB.length;

        return (columnsMatrixA == rowMatrixB);
    }

}
