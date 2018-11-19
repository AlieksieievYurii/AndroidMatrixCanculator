package com.whitedeveloper.matrix.operationModules;

import org.junit.Test;

import static org.junit.Assert.*;

public class TransposeMatrixTest {

    private static final double[][] matrix = {
            {1,2,3,4,5},
            {11,12,13,14,15},
            {-1,-2,-3,-4,-5}};

    private static final double[][] finishMatrix = {
            {1,11,-1},
            {2,12,-2},
            {3,13,-3},
            {4,14,-4},
            {5,15,-5}
    };
    @Test
    public void transposeMatrix()
    {
        TransposeMatrix transposeMatrix = new TransposeMatrix(matrix);

        assertArrayEquals(transposeMatrix.transposeMatrix(),finishMatrix);
    }
}