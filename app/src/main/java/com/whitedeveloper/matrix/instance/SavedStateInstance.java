package com.whitedeveloper.matrix.instance;

import android.content.Context;
import android.content.SharedPreferences;
import com.whitedeveloper.matrix.Action;
import org.json.JSONException;
import org.json.JSONObject;

public class SavedStateInstance {
    private Context context;

    private int spRowsMatrixAPosition = 0;
    private int spColumnsMatrixAPosition = 0;
    private int spColumnsMatrixBPosition = 0;

    private JSONObject jsonObjectMatrixA;
    private JSONObject jsonObjectMatrixB;

    private double determinant;

    private Action action;

    private boolean isCalculated;

    public SavedStateInstance(Context context) {
        this.context = context;
    }

    public void load(String saveAction) throws JSONException {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SavingStateInstance.KEY_STATE_INSTANCE, Context.MODE_PRIVATE);

        switch (saveAction) {
            case SavingStateInstance.KEY_SAVE_STATE_BASIC_OPERATIONS:
                loadLastStateBasicOperation(new JSONObject(sharedPreferences.getString(SavingStateInstance.KEY_SAVE_STATE_BASIC_OPERATIONS, "")));
                break;
            case SavingStateInstance.KEY_SAVE_STATE_MULTIPLICATION:
                loadLastStateMultiplication(new JSONObject(sharedPreferences.getString(SavingStateInstance.KEY_SAVE_STATE_MULTIPLICATION, "")));
                break;
            case SavingStateInstance.KEY_SAVE_STATE_TRANSOPSE:
                loadLastStateTranspose(new JSONObject(sharedPreferences.getString(SavingStateInstance.KEY_SAVE_STATE_TRANSOPSE,"")));
                break;
        }

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


    public double getDeterminant() {
        return determinant;
    }

    public Action getAction() {
        return action;
    }

    public boolean isCalculated() {
        return isCalculated;
    }

    private void loadLastStateTranspose(JSONObject jsonObject) throws JSONException {
        spRowsMatrixAPosition = jsonObject.getInt(SavingStateInstance.KEY_SHARED_ROWS_MATRIX);
        spColumnsMatrixAPosition = jsonObject.getInt(SavingStateInstance.KEY_SHARED_COLUMNS_MATRIX);

        jsonObjectMatrixA = new JSONObject(jsonObject.getString(SavingInstance.KEY_SHARED_MATRIX_A));

        isCalculated = jsonObject.getBoolean(SavingStateInstance.KEY_SHARED_IS_CALCULATED);
    }

    private void loadLastStateBasicOperation(JSONObject jsonObject) throws JSONException {
        spRowsMatrixAPosition = jsonObject.getInt(SavingStateInstance.KEY_SHARED_rows_matrix_a);
        spColumnsMatrixAPosition = jsonObject.getInt(SavingStateInstance.KEY_SHARED_columns_matrix_a);

        jsonObjectMatrixA = new JSONObject(jsonObject.getString(SavingInstance.KEY_SHARED_MATRIX_A));
        jsonObjectMatrixB = new JSONObject(jsonObject.getString(SavingInstance.KEY_SHARED_MATRIX_B));

        isCalculated = jsonObject.getBoolean(SavingStateInstance.KEY_SHARED_IS_CALCULATED);

        action = Action.findByName(jsonObject.getString(SavingInstance.KEY_SHARED_ACTION));
    }

    private void loadLastStateMultiplication(JSONObject jsonObject) throws JSONException {
        spRowsMatrixAPosition = jsonObject.getInt(SavingStateInstance.KEY_SHARED_rows_matrix_a);
        spColumnsMatrixAPosition = jsonObject.getInt(SavingStateInstance.KEY_SHARED_columns_matrix_a);
        spColumnsMatrixBPosition = jsonObject.getInt(SavingStateInstance.KEY_SHARED_columns_matrix_b);

        jsonObjectMatrixA = new JSONObject(jsonObject.getString(SavingInstance.KEY_SHARED_MATRIX_A));
        jsonObjectMatrixB = new JSONObject(jsonObject.getString(SavingInstance.KEY_SHARED_MATRIX_B));

        isCalculated = jsonObject.getBoolean(SavingStateInstance.KEY_SHARED_IS_CALCULATED);
    }
}
