package com.whitedeveloper.matrix.ListView;

import com.whitedeveloper.matrix.Action;

public class ItemMatrices
{
    private double[][] matrixA;
    private double[][] matrixB;
    private double[][] matrixResult;
    private double determination;
    private Action action;

    public ItemMatrices(double[][] matrixA) {
        this.matrixA = matrixA;
    }

    public ItemMatrices() {
    }

    public ItemMatrices(double[][] matrixA, double[][] matrixResult, Action action) {
        this.matrixA = matrixA;
        this.matrixResult = matrixResult;
        this.action = action;
    }

    public ItemMatrices(double[][] matrixA, double[][] matrixB, double[][] matrixResult, Action action) {
        this.matrixA = matrixA;
        this.matrixB = matrixB;
        this.matrixResult = matrixResult;
        this.action = action;
    }

    public double[][] getMatrixA() {
        return matrixA;
    }

    public ItemMatrices setMatrixA(double[][] matrixA) {
        this.matrixA = matrixA;
        return this;
    }

    public double[][] getMatrixB() {
        return matrixB;
    }

    public ItemMatrices setMatrixB(double[][] matrixB) {
        this.matrixB = matrixB;
        return this;
    }

    public double[][] getMatrixResult() {
        return matrixResult;
    }

    public ItemMatrices setMatrixResult(double[][] matrixResult) {
        this.matrixResult = matrixResult;
        return this;
    }

    public double getDetermination() {
        return determination;
    }

    public ItemMatrices setDetermination(double determination) {
        this.determination = determination;
        return this;
    }

    public Action getAction() {
        return action;
    }

    public ItemMatrices setAction(Action action) {
        this.action = action;
        return this;
    }
}
