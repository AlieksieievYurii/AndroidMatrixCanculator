package com.whitedeveloper.matrix.operationModules;

import org.junit.Test;

import static org.junit.Assert.*;

public class MultiplicationMatrixTest {
    private static final double[][] matrix = {
            {24, 34, 54},
            {65, 25, 64},
            {76, 22, 434}
    };

    private static final double[][] matrix2 = {
            {2, 34, -84, 11},
            {65, 2, 15, 4},
            {36, 22, 434, 0}
    };

    private static final double[][] matrixOfMultiplicationMatrixAndMatrix2 = {
                {4202, 2072, 21930, 400},
                {4059, 3668, 22691, 815},
                {17206, 12176, 182302, 924}
        };

    private static final double[] shouldResult1 =
            {24, 65, 76};
    private static final double[] shouldResult2 =
            {34, 25, 22};
    private static final double[] shouldResult3 =
            {54, 64, 434};

    @Test
    public void testOne() {
        assertArrayEquals(MultiplicationMatrix.getColumnFromMatrix(matrix, 0), shouldResult1, 0);
        assertArrayEquals(MultiplicationMatrix.getColumnFromMatrix(matrix, 1), shouldResult2, 0);
        assertArrayEquals(MultiplicationMatrix.getColumnFromMatrix(matrix, 2), shouldResult3, 0);
    }

    @Test
    public void testTwo() {
        double summa = MultiplicationMatrix.calculateMultiplicationColumnOnRow(MultiplicationMatrix.getColumnFromMatrix(matrix, 0),
                MultiplicationMatrix.getColumnFromMatrix(matrix, 1));

        assertEquals(summa, 4113.0, 0);
    }

    @Test
    public void testThree() {
        MultiplicationMatrix multiplicationMatrix = new MultiplicationMatrix(matrix, matrix2);

        assertTrue(multiplicationMatrix.isAvailableForAddition());

        assertArrayEquals(multiplicationMatrix.multiplicationMatrices(),matrixOfMultiplicationMatrixAndMatrix2);
    }
}