package com.whitedeveloper.matrix.operationModules;

public class InversionMatrix {
    private double[][] matrix;


    public InversionMatrix(final double[][] matrix) {
        this.matrix = copyInstance(matrix);
    }

    private static double[][] copyInstance(double[][] matrix) {
        double[][] matrixCopy = new double[matrix.length][matrix[0].length];

        for (int i = 0; i < matrix.length; i++)
            if (matrix[i].length >= 0) System.arraycopy(matrix[i], 0, matrixCopy[i], 0, matrix[i].length);

        return matrixCopy;
    }

    public boolean canBeInverted(double[][] matrix)
    {
        DeterminantMatrix determinantMatrix = new DeterminantMatrix(matrix);

        return !(determinantMatrix.countDeterminant() == 0);
    }

    public double[][] inversionMatrix() {
        return roundMatrix(invert());
    }

    private double[][] invert() {
        int n = matrix.length;
        double x[][] = new double[n][n];
        double b[][] = new double[n][n];
        int index[] = new int[n];
        for (int i = 0; i < n; ++i)
            b[i][i] = 1;

        gaussian(matrix, index);

        for (int i = 0; i < n - 1; ++i)
            for (int j = i + 1; j < n; ++j)
                for (int k = 0; k < n; ++k)
                    b[index[j]][k]
                            -= matrix[index[j]][i] * b[index[i]][k];

        for (int i = 0; i < n; ++i) {
            x[n - 1][i] = b[index[n - 1]][i] / matrix[index[n - 1]][n - 1];
            for (int j = n - 2; j >= 0; --j) {
                x[j][i] = b[index[j]][i];
                for (int k = j + 1; k < n; ++k) {
                    x[j][i] -= matrix[index[j]][k] * x[k][i];
                }
                x[j][i] /= matrix[index[j]][j];
            }
        }
        return x;
    }


    private void gaussian(double a[][], int index[]) {
        int n = index.length;
        double c[] = new double[n];

        for (int i = 0; i < n; ++i)
            index[i] = i;

        for (int i = 0; i < n; ++i) {
            double c1 = 0;
            for (int j = 0; j < n; ++j) {
                double c0 = Math.abs(a[i][j]);
                if (c0 > c1) c1 = c0;
            }
            c[i] = c1;
        }

        int k = 0;
        for (int j = 0; j < n - 1; ++j) {
            double pi1 = 0;
            for (int i = j; i < n; ++i) {
                double pi0 = Math.abs(a[index[i]][j]);
                pi0 /= c[index[i]];
                if (pi0 > pi1) {
                    pi1 = pi0;
                    k = i;
                }
            }

            int itmp = index[j];
            index[j] = index[k];
            index[k] = itmp;
            for (int i = j + 1; i < n; ++i) {
                double pj = a[index[i]][j] / a[index[j]][j];

                a[index[i]][j] = pj;

                for (int l = j + 1; l < n; ++l)
                    a[index[i]][l] -= pj * a[index[j]][l];
            }
        }
    }

    static double[][] roundMatrix(double[][] matrix) {
        double[][] matrixRounded = new double[matrix.length][matrix[0].length];

        for (int i = 0; i < matrix.length; i++)
            for (int j = 0; j < matrix[i].length; j++)
                matrixRounded[i][j] = round(matrix[i][j]);
        return matrixRounded;
    }

    private static double round(double number) {
        return (double) Math.round(number * 10d) / 10d;
    }


}
