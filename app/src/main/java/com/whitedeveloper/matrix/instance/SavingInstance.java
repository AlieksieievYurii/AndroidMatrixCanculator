package com.whitedeveloper.matrix.instance;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import com.whitedeveloper.matrix.Action;
import com.whitedeveloper.matrix.operationModules.JsonMatrix;
import org.json.JSONObject;

public class SavingInstance {
    static final String KEY_SHARED_MATRICES = "matrices";
    static final String KEY_SHARED_ACTION = "action";
    static final String KEY_SHARED_MATRIX_A = "matrix_a";
    static final String KEY_SHARED_MATRIX_B = "matrix_b";
    static final String KEY_SHARED_MATRIX_RESULT = "matrix_result";
    static final String KEY_SHARED_DETERMINANT = "matrix_determinant";

    private Context context;
    private String nameSaving;
    private Action action;
    private double determinant;
    private double[][] matrixA;
    private double[][] matrixB;
    private double[][] matrixResult;

    public SavingInstance(Context context) {
        this.context = context;
    }

    public SavingInstance() {
    }

    public SavingInstance setNameSaving(String nameSaving) {
        this.nameSaving = nameSaving;
        return this;
    }


    public SavingInstance setContext(Context context) {
        this.context = context;
        return this;
    }

    public String getNameSaving() {
        return nameSaving;
    }

    public SavingInstance setAction(Action action) {
        this.action = action;
        return this;
    }

    public SavingInstance setMatrixA(double[][] matrixA) {
        this.matrixA = matrixA;
        return this;
    }

    public SavingInstance setMatrixB(double[][] matrixB) {
        this.matrixB = matrixB;
        return this;
    }

    public SavingInstance setMatrixResult(double[][] matrixResult) {
        this.matrixResult = matrixResult;
        return this;
    }
    public double getDeterminant() {
          return determinant;
      }

      public SavingInstance setDeterminant(double determinant) {
          this.determinant = determinant;
          return this;
      }


    public void commit() throws Exception {
        switch (action) {
            case ADDITION:
                saveAddition();
                break;
            case SUBTRACTION:
                saveSubtraction();
                break;
            case MULTIPLICATION:
                saveMultiplication();
                break;
            case INVERSION:
                saveInversion();
                break;
            case DETERMINATION:
                saveDetermination();
                break;
            case TRANSPOSING:
                saveTransposing();
                break;
        }
    }

    private void saveTransposing() throws Exception {
        SharedPreferences.Editor sharedPreferences = context.getSharedPreferences(KEY_SHARED_MATRICES, Context.MODE_PRIVATE).edit();

        JSONObject jsonObject = new JSONObject();
        jsonObject.put(KEY_SHARED_ACTION, Action.TRANSPOSING.toString());
        jsonObject.put(KEY_SHARED_MATRIX_A, JsonMatrix.getJsonFromMatrix(matrixA));
        jsonObject.put(KEY_SHARED_MATRIX_RESULT, JsonMatrix.getJsonFromMatrix(matrixResult));

        Log.i("TEST", jsonObject.toString());

        sharedPreferences.putString(nameSaving, jsonObject.toString());
        sharedPreferences.apply();

    }

    private void saveDetermination() throws Exception {
        SharedPreferences.Editor sharedPreferences = context.getSharedPreferences(KEY_SHARED_MATRICES, Context.MODE_PRIVATE).edit();

        JSONObject jsonObject = new JSONObject();
        jsonObject.put(KEY_SHARED_ACTION, Action.DETERMINATION.toString());
        jsonObject.put(KEY_SHARED_MATRIX_A, JsonMatrix.getJsonFromMatrix(matrixA));
        jsonObject.put(KEY_SHARED_DETERMINANT,determinant);

        sharedPreferences.putString(nameSaving, jsonObject.toString());
        sharedPreferences.apply();
    }

    private void saveInversion() throws Exception {
        SharedPreferences.Editor sharedPreferences = context.getSharedPreferences(KEY_SHARED_MATRICES, Context.MODE_PRIVATE).edit();

        JSONObject jsonObject = new JSONObject();
        jsonObject.put(KEY_SHARED_ACTION, Action.INVERSION.toString());
        jsonObject.put(KEY_SHARED_MATRIX_A, JsonMatrix.getJsonFromMatrix(matrixA));
        jsonObject.put(KEY_SHARED_MATRIX_RESULT, JsonMatrix.getJsonFromMatrix(matrixResult));


        sharedPreferences.putString(nameSaving, jsonObject.toString());
        sharedPreferences.apply();
    }

    private void saveMultiplication() throws Exception {
        SharedPreferences.Editor sharedPreferences = context.getSharedPreferences(KEY_SHARED_MATRICES, Context.MODE_PRIVATE).edit();

        JSONObject jsonObject = new JSONObject();
        jsonObject.put(KEY_SHARED_ACTION, Action.MULTIPLICATION.toString());
        jsonObject.put(KEY_SHARED_MATRIX_A, JsonMatrix.getJsonFromMatrix(matrixA));
        jsonObject.put(KEY_SHARED_MATRIX_B, JsonMatrix.getJsonFromMatrix(matrixB));
        jsonObject.put(KEY_SHARED_MATRIX_RESULT, JsonMatrix.getJsonFromMatrix(matrixResult));


        sharedPreferences.putString(nameSaving, jsonObject.toString());
        sharedPreferences.apply();
    }

    private void saveSubtraction() throws Exception {
        SharedPreferences.Editor sharedPreferences = context.getSharedPreferences(KEY_SHARED_MATRICES, Context.MODE_PRIVATE).edit();

        JSONObject jsonObject = new JSONObject();
        jsonObject.put(KEY_SHARED_ACTION, Action.SUBTRACTION.toString());
        jsonObject.put(KEY_SHARED_MATRIX_A, JsonMatrix.getJsonFromMatrix(matrixA));
        jsonObject.put(KEY_SHARED_MATRIX_B, JsonMatrix.getJsonFromMatrix(matrixB));
        jsonObject.put(KEY_SHARED_MATRIX_RESULT, JsonMatrix.getJsonFromMatrix(matrixResult));


        sharedPreferences.putString(nameSaving, jsonObject.toString());
        sharedPreferences.apply();
    }

    private void saveAddition() throws Exception {
        SharedPreferences.Editor sharedPreferences = context.getSharedPreferences(KEY_SHARED_MATRICES, Context.MODE_PRIVATE).edit();

        JSONObject jsonObject = new JSONObject();
        jsonObject.put(KEY_SHARED_ACTION, Action.ADDITION.toString());
        jsonObject.put(KEY_SHARED_MATRIX_A, JsonMatrix.getJsonFromMatrix(matrixA));
        jsonObject.put(KEY_SHARED_MATRIX_B, JsonMatrix.getJsonFromMatrix(matrixB));
        jsonObject.put(KEY_SHARED_MATRIX_RESULT, JsonMatrix.getJsonFromMatrix(matrixResult));


        sharedPreferences.putString(nameSaving, jsonObject.toString());
        sharedPreferences.apply();

    }

}
