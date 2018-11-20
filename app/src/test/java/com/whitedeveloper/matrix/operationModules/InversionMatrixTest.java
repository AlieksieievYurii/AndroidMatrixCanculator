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

    @Test
    public void testRounding()
    {
        double number = -1.9999999999999998;
        double expected = -2;

        assertEquals(expected,InversionMatrix.round(number),0);
    }
}