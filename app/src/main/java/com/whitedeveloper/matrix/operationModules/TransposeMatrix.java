package com.whitedeveloper.matrix.operationModules;

public class TransposeMatrix
{
    private double[][] matrix;

    public TransposeMatrix(double[][] matrix) {
        this.matrix = matrix;
    }

    public double[][] transposeMatrix()
    {
        double[][] transposedMatrix = new double[matrix[0].length][matrix.length];

        for(int i = 0; i < transposedMatrix.length; i++)
            for(int j = 0; j < transposedMatrix[i].length;j++)
                transposedMatrix[i][j] = matrix[j][i];

        return transposedMatrix;
    }
}
