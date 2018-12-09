package com.whitedeveloper.matrix.operationModules;

public class MultiplicationMatrixByNumber
{
    private double[][] matrix;

    public MultiplicationMatrixByNumber(double[][] matrix) {
        this.matrix = matrix;
    }

    public double[][] calculate(double k)
    {
        double[][] m = new double[matrix.length][matrix[0].length];

        for(int i = 0; i < m.length; i++)
            for(int j = 0; j < m[i].length; j++)
                m[i][j] =  matrix[i][j] * k;

        return m;
    }
}
