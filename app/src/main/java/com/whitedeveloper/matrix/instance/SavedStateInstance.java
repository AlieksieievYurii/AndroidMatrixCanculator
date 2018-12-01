package com.whitedeveloper.matrix.instance;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
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

    public void load(String action) throws JSONException {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SavingStateInstance.KEY_STATE_INSTANCE,Context.MODE_PRIVATE);

        switch (action)
        {
            case SavingStateInstance.KEY_SAVE_STATE_BASIC_OPERATIONS:
                loadBasicOperation(new JSONObject(sharedPreferences.getString(SavingStateInstance.KEY_SAVE_STATE_BASIC_OPERATIONS,"")));
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

    private void loadBasicOperation(JSONObject jsonObject) throws JSONException {
        spRowsMatrixAPosition = jsonObject.getInt(SavingStateInstance.KEY_SHARED_rows_matrix_a);
        spColumnsMatrixAPosition = jsonObject.getInt(SavingStateInstance.KEY_SHARED_columns_matrix_a);

        jsonObjectMatrixA = new JSONObject(jsonObject.getString(SavingInstance.KEY_SHARED_MATRIX_A));
        jsonObjectMatrixB = new JSONObject(jsonObject.getString(SavingInstance.KEY_SHARED_MATRIX_B));

        isCalculated = jsonObject.getBoolean(SavingStateInstance.KEY_SHARED_IS_CALCULATED);

        action = Action.findByName(jsonObject.getString(SavingInstance.KEY_SHARED_ACTION));
    }
}
