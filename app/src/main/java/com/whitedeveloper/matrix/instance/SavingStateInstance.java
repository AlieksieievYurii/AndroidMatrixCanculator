package com.whitedeveloper.matrix.instance;

import android.content.Context;
import android.content.SharedPreferences;
import com.whitedeveloper.matrix.tags.Action;
import com.whitedeveloper.matrix.tags.TagKeys;
import org.json.JSONException;
import org.json.JSONObject;

import static com.whitedeveloper.matrix.tags.TagKeys.*;

public class SavingStateInstance {


    private int spRowsMatrixAPosition = 0;
    private int spColumnsMatrixAPosition = 0;
    private int spColumnsMatrixBPosition = 0;

    private int spDimensionMatrix = 0;

    private JSONObject jsonObjectMatrixA;
    private JSONObject jsonObjectMatrixB;


    private Action action;

    private boolean isCalculated;

    private Context context;

    public SavingStateInstance(Context context) {
        this.context = context;
    }

    public SavingStateInstance setSpDimensionMatrix(int spDimensionMatrix) {
        this.spDimensionMatrix = spDimensionMatrix;
        return this;
    }

    public SavingStateInstance setSpRowsMatrixAPosition(int spRowsMatrixAPosition) {
        this.spRowsMatrixAPosition = spRowsMatrixAPosition;
        return this;
    }

    public SavingStateInstance setSpColumnsMatrixAPosition(int spColumnsMatrixAPosition) {
        this.spColumnsMatrixAPosition = spColumnsMatrixAPosition;
        return this;
    }

    public SavingStateInstance setSpColumnsMatrixBPosition(int spColumnsMatrixBPosition) {
        this.spColumnsMatrixBPosition = spColumnsMatrixBPosition;
        return this;
    }

    public SavingStateInstance setJsonObjectMatrixA(JSONObject jsonObjectMatrixA) {
        this.jsonObjectMatrixA = jsonObjectMatrixA;
        return this;
    }

    public SavingStateInstance setJsonObjectMatrixB(JSONObject jsonObjectMatrixB) {
        this.jsonObjectMatrixB = jsonObjectMatrixB;
        return this;
    }


    public SavingStateInstance setAction(Action action) {
        this.action = action;
        return this;
    }

    public SavingStateInstance setCalculated(boolean calculated) {
        isCalculated = calculated;
        return this;
    }


    public void commit() throws Exception {
        switch (action) {
            case ADDITION:
                saveStateBasicOperation();
                break;
            case SUBTRACTION:
                saveStateBasicOperation();
                break;
            case TRANSPOSING:
                saveStateTranspose();
                break;
            case DETERMINATION:
                saveStateDeterminant();
                break;
            case INVERSION:
                saveStateInverse();
                break;
            case MULTIPLICATION:
                saveStateMultiplication();
                break;
        }
    }

    private void saveStateInverse() throws JSONException {
        SharedPreferences.Editor editor = context.getSharedPreferences(KEY_STATE_INSTANCE, Context.MODE_PRIVATE).edit();

        JSONObject jsonObject = new JSONObject();

        jsonObject.put(KEY_SHARED_DIMENSION_MATRIX, spDimensionMatrix);
        jsonObject.put(TagKeys.KEY_SHARED_MATRIX_A, jsonObjectMatrixA.toString());
        jsonObject.put(KEY_SHARED_IS_CALCULATED, isCalculated);

        editor.putString(KEY_SAVE_STATE_INVERSE, jsonObject.toString());
        editor.apply();
    }

    private void saveStateDeterminant() throws JSONException {
        SharedPreferences.Editor editor = context.getSharedPreferences(KEY_STATE_INSTANCE, Context.MODE_PRIVATE).edit();

        JSONObject jsonObject = new JSONObject();

        jsonObject.put(KEY_SHARED_DIMENSION_MATRIX, spDimensionMatrix);
        jsonObject.put(TagKeys.KEY_SHARED_MATRIX_A, jsonObjectMatrixA.toString());
        jsonObject.put(KEY_SHARED_IS_CALCULATED, isCalculated);

        editor.putString(KEY_SAVE_STATE_DETERMINANT, jsonObject.toString());
        editor.apply();
    }

    private void saveStateTranspose() throws JSONException {
        SharedPreferences.Editor editor = context.getSharedPreferences(KEY_STATE_INSTANCE, Context.MODE_PRIVATE).edit();

        JSONObject jsonObject = new JSONObject();

        jsonObject.put(KEY_SHARED_ROWS_MATRIX, spRowsMatrixAPosition);
        jsonObject.put(KEY_SHARED_COLUMNS_MATRIX, spColumnsMatrixAPosition);
        jsonObject.put(TagKeys.KEY_SHARED_MATRIX_A, jsonObjectMatrixA.toString());
        jsonObject.put(KEY_SHARED_IS_CALCULATED, isCalculated);

        editor.putString(KEY_SAVE_STATE_TRANSPOSE, jsonObject.toString());

        editor.apply();
    }

    private void saveStateMultiplication() throws Exception {
        SharedPreferences.Editor editor = context.getSharedPreferences(KEY_STATE_INSTANCE, Context.MODE_PRIVATE).edit();

        JSONObject jsonObject = new JSONObject();

        jsonObject.put(KEY_SHARED_rows_matrix_a, spRowsMatrixAPosition);
        jsonObject.put(KEY_SHARED_columns_matrix_a, spColumnsMatrixAPosition);
        jsonObject.put(KEY_SHARED_columns_matrix_b, spColumnsMatrixBPosition);
        jsonObject.put(TagKeys.KEY_SHARED_MATRIX_A, jsonObjectMatrixA.toString());
        jsonObject.put(TagKeys.KEY_SHARED_MATRIX_B, jsonObjectMatrixB.toString());
        jsonObject.put(TagKeys.KEY_SHARED_ACTION, action.toString());
        jsonObject.put(KEY_SHARED_IS_CALCULATED, isCalculated);

        editor.putString(KEY_SAVE_STATE_MULTIPLICATION, jsonObject.toString());
        editor.apply();
    }

    private void saveStateBasicOperation() throws Exception {
        SharedPreferences.Editor editor = context.getSharedPreferences(KEY_STATE_INSTANCE, Context.MODE_PRIVATE).edit();

        JSONObject jsonObject = new JSONObject();

        jsonObject.put(KEY_SHARED_columns_matrix_a, spColumnsMatrixAPosition);
        jsonObject.put(KEY_SHARED_rows_matrix_a, spRowsMatrixAPosition);
        jsonObject.put(TagKeys.KEY_SHARED_MATRIX_A, jsonObjectMatrixA.toString());
        jsonObject.put(TagKeys.KEY_SHARED_MATRIX_B, jsonObjectMatrixB.toString());
        jsonObject.put(TagKeys.KEY_SHARED_ACTION, action.toString());
        jsonObject.put(KEY_SHARED_IS_CALCULATED, isCalculated);

        editor.putString(KEY_SAVE_STATE_BASIC_OPERATIONS, jsonObject.toString());
        editor.apply();
    }
}
