package com.whitedeveloper.matrix.operationModules;

import org.junit.Test;

import static org.junit.Assert.*;

public class InversionMatrixTest {

    private static final double[][] matrix = {
            {1,2},
            {3,4}};

    public static final double[][] res = {
            {-2,1},
            {1.5,-0.5}
    };

    @Test
    public void invert()
    {
        InversionMatrix inversionMatrix = new InversionMatrix(matrix);

        assertArrayEquals(inversionMatrix.inversionMatrix(),res);
    }
}