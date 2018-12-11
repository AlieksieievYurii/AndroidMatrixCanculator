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

        return InversionMatrix.roundMatrix(matrixResult);
    }


    public static double[][] doNegative(double[][] matrix) {

        double[][] matrixNewInstance = new double[matrix.length][matrix[0].length];

        for (int i = 0; i < matrix.length; i++)
            for (int j = 0; j < matrix[i].length; j++)
                matrixNewInstance[i][j] = -matrix[i][j];
        return matrixNewInstance;

    }
}
