package com.whitedeveloper.matrix.operationModules;

public class DeterminantMatrix {
    private double[][] matrix;

    public DeterminantMatrix(double[][] matrix) {
        this.matrix = matrix;
    }

    private void getCantor(double mat[][], double temp[][], int p, int q, int n) {
        int i = 0, j = 0;

        for (int row = 0; row < n; row++) {
            for (int col = 0; col < n; col++) {

                if (row != p && col != q) {
                    temp[i][j++] = mat[row][col];

                    if (j == n - 1) {
                        j = 0;
                        i++;
                    }
                }
            }
        }
    }

    private double determinantOfMatrix(double mat[][], int n) {
        double D = 0;

        if (n == 1)
            return mat[0][0];

        double temp[][] = new double[n][n];

        int sign = 1;

        for (int f = 0; f < n; f++) {
            getCantor(mat, temp, 0, f, n);
            D += sign * mat[0][f]
                    * determinantOfMatrix(temp, n - 1);

            sign = -sign;
        }

        return D;
    }

    public double countDeterminant() {
        return determinantOfMatrix(matrix, matrix.length);
    }

}
