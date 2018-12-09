package com.whitedeveloper.matrix.instance;

import android.content.Context;
import android.content.SharedPreferences;
import com.whitedeveloper.matrix.tags.Action;
import com.whitedeveloper.matrix.tags.TagKeys;
import org.json.JSONException;
import org.json.JSONObject;

public class SavedStateInstance {
    private Context context;

    private int spRowsMatrixAPosition = 0;
    private int spColumnsMatrixAPosition = 0;
    private int spColumnsMatrixBPosition = 0;
    private int spDimensionMatrix = 0;
    private double k;//This this for multiplication matrix by a number


    private JSONObject jsonObjectMatrixA;
    private JSONObject jsonObjectMatrixB;

    private Action action;

    private boolean isCalculated;

    public SavedStateInstance(Context context) {
        this.context = context;
    }

    public void load(String saveAction) throws JSONException {
        SharedPreferences sharedPreferences = context.getSharedPreferences(TagKeys.KEY_STATE_INSTANCE, Context.MODE_PRIVATE);

        switch (saveAction) {
            case TagKeys.KEY_SAVE_STATE_BASIC_OPERATIONS:
                loadLastStateBasicOperation(new JSONObject(sharedPreferences.getString(TagKeys.KEY_SAVE_STATE_BASIC_OPERATIONS, "")));
                break;
            case TagKeys.KEY_SAVE_STATE_MULTIPLICATION:
                loadLastStateMultiplication(new JSONObject(sharedPreferences.getString(TagKeys.KEY_SAVE_STATE_MULTIPLICATION, "")));
                break;
            case TagKeys.KEY_SAVE_STATE_TRANSPOSE:
                loadLastStateTranspose(new JSONObject(sharedPreferences.getString(TagKeys.KEY_SAVE_STATE_TRANSPOSE, "")));
                break;
            case TagKeys.KEY_SAVE_STATE_DETERMINANT:
                loadLastStateDeterminant(new JSONObject(sharedPreferences.getString(TagKeys.KEY_SAVE_STATE_DETERMINANT, "")));
                break;
            case TagKeys.KEY_SAVE_STATE_INVERSE:
                loadLastStateInverse(new JSONObject(sharedPreferences.getString(TagKeys.KEY_SAVE_STATE_INVERSE, "")));
                break;
            case TagKeys.KEY_SAVE_STATE_LU:
                loadLastStateSeparationLU(new JSONObject(sharedPreferences.getString(TagKeys.KEY_SAVE_STATE_LU, "")));
                break;
            case TagKeys.KEY_SAVE_STATE_MULTIPLICATION_BY_NUMBER:
                loadLastStateMultiplicationByNumber(new JSONObject(sharedPreferences.getString(TagKeys.KEY_SAVE_STATE_MULTIPLICATION_BY_NUMBER, "")));

        }

    }


    public int getSpDimensionMatrix() {
        return spDimensionMatrix;
    }

    public int getSpRowsMatrixAPosition() {
        return spRowsMatrixAPosition;
    }

    public int getSpColumnsMatrixAPosition() {
        return spColumnsMatrixAPosition;
    }

    public int getSpColumnsMatrixBPosition() {
        return spColumnsMatrixBPosition;
    }

    public JSONObject getJsonObjectMatrixA() {
        return jsonObjectMatrixA;
    }

    public JSONObject getJsonObjectMatrixB() {
        return jsonObjectMatrixB;
    }

    public double getK() {
        return k;
    }

    public Action getAction() {
        return action;
    }

    public boolean isCalculated() {
        return isCalculated;
    }

    private void loadLastStateTranspose(JSONObject jsonObject) throws JSONException {
        spRowsMatrixAPosition = jsonObject.getInt(TagKeys.KEY_SHARED_ROWS_MATRIX);
        spColumnsMatrixAPosition = jsonObject.getInt(TagKeys.KEY_SHARED_COLUMNS_MATRIX);

        jsonObjectMatrixA = new JSONObject(jsonObject.getString(TagKeys.KEY_SHARED_MATRIX_A));

        isCalculated = jsonObject.getBoolean(TagKeys.KEY_SHARED_IS_CALCULATED);
    }

    private void loadLastStateBasicOperation(JSONObject jsonObject) throws JSONException {
        spRowsMatrixAPosition = jsonObject.getInt(TagKeys.KEY_SHARED_rows_matrix_a);
        spColumnsMatrixAPosition = jsonObject.getInt(TagKeys.KEY_SHARED_columns_matrix_a);

        jsonObjectMatrixA = new JSONObject(jsonObject.getString(TagKeys.KEY_SHARED_MATRIX_A));
        jsonObjectMatrixB = new JSONObject(jsonObject.getString(TagKeys.KEY_SHARED_MATRIX_B));

        isCalculated = jsonObject.getBoolean(TagKeys.KEY_SHARED_IS_CALCULATED);

        action = Action.findByName(jsonObject.getString(TagKeys.KEY_SHARED_ACTION));
    }

    private void loadLastStateMultiplication(JSONObject jsonObject) throws JSONException {
        spRowsMatrixAPosition = jsonObject.getInt(TagKeys.KEY_SHARED_rows_matrix_a);
        spColumnsMatrixAPosition = jsonObject.getInt(TagKeys.KEY_SHARED_columns_matrix_a);
        spColumnsMatrixBPosition = jsonObject.getInt(TagKeys.KEY_SHARED_columns_matrix_b);

        jsonObjectMatrixA = new JSONObject(jsonObject.getString(TagKeys.KEY_SHARED_MATRIX_A));
        jsonObjectMatrixB = new JSONObject(jsonObject.getString(TagKeys.KEY_SHARED_MATRIX_B));

        isCalculated = jsonObject.getBoolean(TagKeys.KEY_SHARED_IS_CALCULATED);
    }

    private void loadLastStateDeterminant(JSONObject jsonObject) throws JSONException {
        spDimensionMatrix = jsonObject.getInt(TagKeys.KEY_SHARED_DIMENSION_MATRIX);
        jsonObjectMatrixA = new JSONObject(jsonObject.getString(TagKeys.KEY_SHARED_MATRIX_A));
        isCalculated = jsonObject.getBoolean(TagKeys.KEY_SHARED_IS_CALCULATED);
    }

    private void loadLastStateInverse(JSONObject jsonObject) throws JSONException {
        spDimensionMatrix = jsonObject.getInt(TagKeys.KEY_SHARED_DIMENSION_MATRIX);
        jsonObjectMatrixA = new JSONObject(jsonObject.getString(TagKeys.KEY_SHARED_MATRIX_A));
        isCalculated = jsonObject.getBoolean(TagKeys.KEY_SHARED_IS_CALCULATED);
    }

    private void loadLastStateSeparationLU(JSONObject jsonObject) throws JSONException {
        spDimensionMatrix = jsonObject.getInt(TagKeys.KEY_SHARED_DIMENSION_MATRIX);
        jsonObjectMatrixA = new JSONObject(jsonObject.getString(TagKeys.KEY_SHARED_MATRIX_A));
        isCalculated = jsonObject.getBoolean(TagKeys.KEY_SHARED_IS_CALCULATED);
    }

    private void loadLastStateMultiplicationByNumber(JSONObject jsonObject) throws JSONException {

        spRowsMatrixAPosition = jsonObject.getInt(TagKeys.KEY_SHARED_rows_matrix_a);
        spColumnsMatrixAPosition = jsonObject.getInt(TagKeys.KEY_SHARED_columns_matrix_a);
        jsonObjectMatrixA = new JSONObject(jsonObject.getString(TagKeys.KEY_SHARED_MATRIX_A));
        k = jsonObject.getDouble(TagKeys.KEY_SHARED_K);
        isCalculated = jsonObject.getBoolean(TagKeys.KEY_SHARED_IS_CALCULATED);

    }

}
