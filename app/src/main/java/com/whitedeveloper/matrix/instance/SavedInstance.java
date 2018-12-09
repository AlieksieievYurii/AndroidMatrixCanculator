package com.whitedeveloper.matrix.instance;

import android.content.Context;
import android.content.SharedPreferences;
import com.whitedeveloper.matrix.tags.Action;
import com.whitedeveloper.matrix.tags.TagKeys;
import com.whitedeveloper.matrix.operationModules.JsonMatrix;
import org.json.JSONObject;

import java.util.Arrays;

public class SavedInstance {
    private Context context;
    private String nameSaving;
    private Action action;
    private double[][] matrixA = null;
    private double[][] matrixB = null;
    private double[][] matrixResult = null;
    private double[][] matrixL = null;
    private double[][] matrixU = null;
    private double determinant;
    private double numberK;

    private JSONObject jsonObject;

    public SavedInstance(Context context, String nameSaving) throws Exception {
        this.context = context;
        this.nameSaving = nameSaving;
        readingFromSharedPref();
    }


    private void readingFromSharedPref() throws Exception {
        SharedPreferences sharedPreferences = context.getSharedPreferences(TagKeys.KEY_SHARED_MATRICES, Context.MODE_PRIVATE);
        jsonObject = new JSONObject(sharedPreferences.getString(nameSaving, ""));

        action = Action.findByName(jsonObject.getString(TagKeys.KEY_SHARED_ACTION));

        assert action != null;
        switch (action) {
            case ADDITION:
                loadDefault();
                break;
            case SUBTRACTION:
                loadDefault();
                break;
            case DETERMINATION:
                loadDeterminant();
                break;
            case MULTIPLICATION:
                loadDefault();
                break;
            case TRANSPOSING:
                loadMatrixAndMatrixResult();
                break;
            case INVERSION:
                loadMatrixAndMatrixResult();
                break;
            case SEPARATION:
                loadSeparationLU();
                break;
            case MULTIPLICATION_BY_NUMBER:
                loadMatrixAndMatrixResultAndNumberK();
                break;
        }
    }

    private void loadMatrixAndMatrixResultAndNumberK() throws Exception
    {
        matrixA = JsonMatrix.getMatrixFromJsonObject(new JSONObject(jsonObject.getString(TagKeys.KEY_SHARED_MATRIX_A)));
        matrixResult = JsonMatrix.getMatrixFromJsonObject(new JSONObject(jsonObject.getString(TagKeys.KEY_SHARED_MATRIX_RESULT)));
        numberK = jsonObject.getDouble(TagKeys.KEY_SHARED_K);
    }

    private void loadSeparationLU() throws Exception {
        matrixA = JsonMatrix.getMatrixFromJsonObject(new JSONObject(jsonObject.getString(TagKeys.KEY_SHARED_MATRIX_A)));
        matrixL = JsonMatrix.getMatrixFromJsonObject(new JSONObject(jsonObject.getString(TagKeys.KEY_SHARED_MATRIX_L)));
        matrixU = JsonMatrix.getMatrixFromJsonObject(new JSONObject(jsonObject.getString(TagKeys.KEY_SHARED_MATRIX_U)));
    }

    private void loadDefault() throws Exception {
        matrixA = JsonMatrix.getMatrixFromJsonObject(new JSONObject(jsonObject.getString(TagKeys.KEY_SHARED_MATRIX_A)));
        matrixB = JsonMatrix.getMatrixFromJsonObject(new JSONObject(jsonObject.getString(TagKeys.KEY_SHARED_MATRIX_B)));
        matrixResult = JsonMatrix.getMatrixFromJsonObject(new JSONObject(jsonObject.getString(TagKeys.KEY_SHARED_MATRIX_RESULT)));
    }


    private void loadDeterminant() throws Exception {
        matrixA = JsonMatrix.getMatrixFromJsonObject(new JSONObject(jsonObject.getString(TagKeys.KEY_SHARED_MATRIX_A)));
        determinant = jsonObject.getDouble(TagKeys.KEY_SHARED_DETERMINANT);
    }

    private void loadMatrixAndMatrixResult() throws Exception {
        matrixA = JsonMatrix.getMatrixFromJsonObject(new JSONObject(jsonObject.getString(TagKeys.KEY_SHARED_MATRIX_A)));
        matrixResult = JsonMatrix.getMatrixFromJsonObject(new JSONObject(jsonObject.getString(TagKeys.KEY_SHARED_MATRIX_RESULT)));
    }


    public double[][] getMatrixA() {
        return matrixA;
    }

    public double[][] getMatrixB() {
        return matrixB;
    }

    public double[][] getMatrixResult() {
        return matrixResult;
    }

    public Action getAction() {
        return action;
    }

    public double getDeterminant() {
        return determinant;
    }

    public double getNumberK() {
        return numberK;
    }

    public String getNameSaving() {
        return nameSaving;
    }

    public double[][] getMatrixL() {
        return matrixL;
    }

    public double[][] getMatrixU() {
        return matrixU;
    }

    @Override
    public String toString() {
        return "SavedInstance{" +
                "context=" + context +
                ", nameSaving='" + nameSaving + '\'' +
                ", action=" + action +
                ", matrixA=" + Arrays.toString(matrixA) +
                ", matrixB=" + Arrays.toString(matrixB) +
                ", matrixResult=" + Arrays.toString(matrixResult) +
                ", matrixL=" + Arrays.toString(matrixL) +
                ", matrixU=" + Arrays.toString(matrixU) +
                ", determinant=" + determinant +
                ", jsonObject=" + jsonObject +
                '}';
    }
}
