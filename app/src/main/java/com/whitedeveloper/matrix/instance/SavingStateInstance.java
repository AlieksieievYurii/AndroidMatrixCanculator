package com.whitedeveloper.matrix.instance;

import android.content.Context;
import android.content.SharedPreferences;
import com.whitedeveloper.matrix.Action;
import org.json.JSONException;
import org.json.JSONObject;

public class SavingStateInstance {
    public static final String KEY_STATE_INSTANCE = "save_state_instance";
    public static final String KEY_SAVE_STATE_BASIC_OPERATIONS = "save_state_basic_operation";
    public static final String KEY_SAVE_STATE_MULTIPLICATION = "save_state_multiplication";
    public static final String KEY_SAVE_STATE_TRANSOPSE = "save_state_transpose";
    public static final String KEY_SAVE_STATE_DETERMINANT = "save_state_determinant";
    public static final String KEY_SAVE_STATE_INVERSE = "save_state_INVERSE";


    public static final String KEY_SHARED_rows_matrix_a = "key_shared_rows_matrix_a_position";
    public static final String KEY_SHARED_columns_matrix_b = "key_shared_columns_matrix_b_position";
    public static final String KEY_SHARED_columns_matrix_a = "key_shared_columns_matrix_a_position";
    public static final String KEY_SHARED_DIMENSION_MATRIX = "key_shared_dimension_matrix";
    public static final String KEY_SHARED_ROWS_MATRIX = "key_shared_rows_matrix";
    public static final String KEY_SHARED_COLUMNS_MATRIX = "key_shared_columns_matrix";
    public static final String KEY_SHARED_IS_CALCULATED = "key_shared_is_calculated";

    private int spRowsMatrixAPosition = 0;
    private int spColumnsMatrixAPosition = 0;
    private int spColumnsMatrixBPosition = 0;

    private JSONObject jsonObjectMatrixA;
    private JSONObject jsonObjectMatrixB;

    private double determinant;

    private Action action;

    private boolean isCalculated;

    private Context context;

    public SavingStateInstance(Context context) {
        this.context = context;
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

    public SavingStateInstance setDeterminant(double determinant) {
        this.determinant = determinant;
        return this;
    }

    public void commit() throws Exception {
        switch (action)
        {
            case ADDITION:
                saveStateBasicOperation();
                break;
            case SUBTRACTION:
                saveStateBasicOperation();
                break;
            case TRANSPOSING:
                saveStateTranspose();
                break;
            case DETERMINATION:break;
            case INVERSION:break;
            case MULTIPLICATION:
                saveStateMultiplication();
                break;
        }
    }

    private void saveStateTranspose() throws JSONException {
        SharedPreferences.Editor editor = context.getSharedPreferences(KEY_STATE_INSTANCE,Context.MODE_PRIVATE).edit();

        JSONObject jsonObject = new JSONObject();

        jsonObject.put(KEY_SHARED_ROWS_MATRIX,spRowsMatrixAPosition);
        jsonObject.put(KEY_SHARED_COLUMNS_MATRIX,spColumnsMatrixAPosition);
        jsonObject.put(SavingInstance.KEY_SHARED_MATRIX_A,jsonObjectMatrixA.toString());
        jsonObject.put(KEY_SHARED_IS_CALCULATED,isCalculated);

        editor.putString(KEY_SAVE_STATE_TRANSOPSE,jsonObject.toString());

        editor.apply();
    }

    private void saveStateMultiplication() throws Exception {
        SharedPreferences.Editor editor = context.getSharedPreferences(KEY_STATE_INSTANCE,Context.MODE_PRIVATE).edit();

        JSONObject jsonObject = new JSONObject();

        jsonObject.put(KEY_SHARED_rows_matrix_a,spRowsMatrixAPosition);
        jsonObject.put(KEY_SHARED_columns_matrix_a,spColumnsMatrixAPosition);
        jsonObject.put(KEY_SHARED_columns_matrix_b,spColumnsMatrixBPosition);
        jsonObject.put(SavingInstance.KEY_SHARED_MATRIX_A,jsonObjectMatrixA.toString());
        jsonObject.put(SavingInstance.KEY_SHARED_MATRIX_B,jsonObjectMatrixB.toString());
        jsonObject.put(SavingInstance.KEY_SHARED_ACTION,action.toString());
        jsonObject.put(KEY_SHARED_IS_CALCULATED,isCalculated);

        editor.putString(KEY_SAVE_STATE_MULTIPLICATION,jsonObject.toString());
        editor.apply();
    }

    private void saveStateBasicOperation() throws Exception {
        SharedPreferences.Editor editor = context.getSharedPreferences(KEY_STATE_INSTANCE,Context.MODE_PRIVATE).edit();

        JSONObject jsonObject = new JSONObject();

        jsonObject.put(KEY_SHARED_columns_matrix_a,spColumnsMatrixAPosition);
        jsonObject.put(KEY_SHARED_rows_matrix_a,spRowsMatrixAPosition);
        jsonObject.put(SavingInstance.KEY_SHARED_MATRIX_A,jsonObjectMatrixA.toString());
        jsonObject.put(SavingInstance.KEY_SHARED_MATRIX_B,jsonObjectMatrixB.toString());
        jsonObject.put(SavingInstance.KEY_SHARED_ACTION,action.toString());
        jsonObject.put(KEY_SHARED_IS_CALCULATED,isCalculated);

        editor.putString(KEY_SAVE_STATE_BASIC_OPERATIONS,jsonObject.toString());
        editor.apply();
    }
}
