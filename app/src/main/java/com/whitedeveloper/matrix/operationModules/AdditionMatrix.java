package com.whitedeveloper.matrix.operationModules;

public class AdditionMatrix {

    private double[][] matrixA;
    private double[][] matrixB;

    public AdditionMatrix(double[][] matrixA, double[][] matrixB) {
        this.matrixA = matrixA;
        this.matrixB = matrixB;
    }

    public double[][] additionMatrix() {
        int rowMatrix = matrixA.length;
        int columnsMatrix = matrixA[0].length;

        final double[][] matrixResult = new double[rowMatrix][columnsMatrix];

        for (int i = 0; i < rowMatrix; i++)
            for (int j = 0; j < columnsMatrix; j++)
                matrixResult[i][j] = matrixA[i][j] + matrixB[i][j];

        return matrixResult;
    }

    public boolean isAvailableForAddition() {
        int rowMatrixA = matrixA.length;
        int columnsMatrixA = matrixA[0].length;

        int rowMatrixB = matrixB.length;
        int columnsMatrixB = matrixB[0].length;

        return (rowMatrixA == rowMatrixB || columnsMatrixA == columnsMatrixB);
    }

    public static double[][] doNegative(double[][] matrix) {
        for (int i = 0; i < matrix.length; i++)
            for (int j = 0; j < matrix[i].length; j++)
                matrix[i][j] = -matrix[i][j];
        return matrix;

    }
}
