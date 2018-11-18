package com.whitedeveloper.matrix.operationModules;

public class AdditionMatrix {

    private double[][] matrixA;

    public AdditionMatrix(double[][] matrixA, double[][] matrixB) {
        this.matrixA = matrixA;
        this.matrixB = matrixB;
    }

    private double[][] matrixB;

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
}
