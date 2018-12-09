package com.whitedeveloper.matrix.operationModules;

public class MatrixOnLU {
    private double[][] matrix;
    private double[][] matrixL;
    private double[][] matrixU;
    private int dimension;

    public MatrixOnLU(double[][] matrix) {
        this.matrix = matrix;
        dimension = matrix.length;
        matrixL = new double[dimension][dimension];
        matrixU = new double[dimension][dimension];
        calculate();
    }

    public double[][] getMatrixL() {
        return InversionMatrix.roundMatrix(matrixL);
    }

    public double[][] getMatrixU() {
        return InversionMatrix.roundMatrix(matrixU);
    }

    private void calculate() {
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                if (j < i)
                    matrixL[j][i] = 0;
                else {
                    matrixL[j][i] = matrix[j][i];
                    for (int k = 0; k < i; k++) {
                        matrixL[j][i] = matrixL[j][i] - matrixL[j][k] * matrixU[k][i];
                    }
                }
            }
            for (int j = 0; j < dimension; j++) {
                if (j < i)
                    matrixU[i][j] = 0;
                else if (j == i)
                    matrixU[i][j] = 1;
                else {
                    matrixU[i][j] = matrix[i][j] / matrixL[i][i];
                    for (int k = 0; k < i; k++) {
                        matrixU[i][j] = matrixU[i][j] - ((matrixL[i][k] * matrixU[k][j]) / matrixL[i][i]);
                    }
                }
            }
        }
    }
}