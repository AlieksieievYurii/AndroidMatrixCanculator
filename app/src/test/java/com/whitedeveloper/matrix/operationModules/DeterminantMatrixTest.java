package com.whitedeveloper.matrix.operationModules;

import org.junit.Test;

import static org.junit.Assert.*;

public class DeterminantMatrixTest {
    private static final double[][] matrixOne = {
            {1,2},
            {3,4}
    };
    private static final int determinantOne = -2;

    @Test
    public void countDeterminant() {
        DeterminantMatrix determinantMatrix = new DeterminantMatrix(matrixOne);

        assertEquals(determinantMatrix.countDeterminant(), determinantOne,0);
    }
}