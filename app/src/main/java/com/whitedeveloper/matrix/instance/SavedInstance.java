package com.whitedeveloper.matrix.instance;

import android.content.Context;
import android.content.SharedPreferences;
import com.whitedeveloper.matrix.Action;
import com.whitedeveloper.matrix.operationModules.JsonMatrix;
import org.json.JSONObject;

public class SavedInstance {
    private Context context;
    private String nameSaving;
    private Action action;
    private double[][] matrixA = null;
    private double[][] matrixB = null;
    private double[][] matrixResult = null;
    private double determinant;

    private JSONObject jsonObject;

    public SavedInstance(Context context, String nameSaving) throws Exception {
        this.context = context;
        this.nameSaving = nameSaving;
        readingFromSharedPref();
    }


    private void readingFromSharedPref() throws Exception {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SavingInstance.KEY_SHARED_MATRICES, Context.MODE_PRIVATE);
        jsonObject = new JSONObject(sharedPreferences.getString(nameSaving, ""));

        action = Action.findByName(jsonObject.getString(SavingInstance.KEY_SHARED_ACTION));

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
        }
    }

    private void loadDefault() throws Exception {
        matrixA = JsonMatrix.getMatrixFromJsonObject(new JSONObject(jsonObject.getString(SavingInstance.KEY_SHARED_MATRIX_A)));
        matrixB = JsonMatrix.getMatrixFromJsonObject(new JSONObject(jsonObject.getString(SavingInstance.KEY_SHARED_MATRIX_B)));
        matrixResult = JsonMatrix.getMatrixFromJsonObject(new JSONObject(jsonObject.getString(SavingInstance.KEY_SHARED_MATRIX_RESULT)));
    }


    private void loadDeterminant() throws Exception {
        matrixA = JsonMatrix.getMatrixFromJsonObject(new JSONObject(jsonObject.getString(SavingInstance.KEY_SHARED_MATRIX_A)));
        determinant = jsonObject.getDouble(SavingInstance.KEY_SHARED_DETERMINANT);
    }

    private void loadMatrixAndMatrixResult() throws Exception {
        matrixA = JsonMatrix.getMatrixFromJsonObject(new JSONObject(jsonObject.getString(SavingInstance.KEY_SHARED_MATRIX_A)));
        matrixResult = JsonMatrix.getMatrixFromJsonObject(new JSONObject(jsonObject.getString(SavingInstance.KEY_SHARED_MATRIX_RESULT)));
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

    public double getDeterminant()
    {
        return determinant;
    }

    public String getNameSaving() {
        return nameSaving;
    }


}
