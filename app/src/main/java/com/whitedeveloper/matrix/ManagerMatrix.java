package com.whitedeveloper.matrix;

import android.content.Context;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.TextView;
import com.whitedeveloper.matrix.fragments.Style;
import org.json.JSONException;
import org.json.JSONObject;

public class ManagerMatrix {
    private Context context;

    public ManagerMatrix(Context context) {
        this.context = context;
    }

    public void generateMatrix(GridLayout gridLayout, String tagId, int rowsMatrices, int columnsMatrices, TextWatcher textWatcher) {
        gridLayout.removeAllViews();
        gridLayout.setColumnCount(columnsMatrices);
        for (int i = 0; i < rowsMatrices; i++) {
            for (int j = 0; j < columnsMatrices; j++) {
                EditText editText = new EditText(context);
                editText.addTextChangedListener(textWatcher);
                Style.setStyleEditViewForGridLayout(context, editText);
                if (tagId != null)
                    editText.setTag(tagId + String.valueOf(i) + String.valueOf(j));
                gridLayout.addView(editText);
            }
        }
    }

    public double[][] readMatrix(GridLayout gridLayout, String tagId, int rowsMatrices, int columnsMatrices) {
        double[][] matrix = new double[rowsMatrices][columnsMatrices];
        for (int i = 0; i < rowsMatrices; i++)
            for (int j = 0; j < columnsMatrices; j++) {
                String tag = tagId + String.valueOf(i) + String.valueOf(j);
                EditText editText = gridLayout.findViewWithTag(tag);
                matrix[i][j] = Double.parseDouble(editText.getText().toString());
            }
        return matrix;
    }

    public JSONObject getJsonMatrix(GridLayout gridLayout, String tagId, int rowsMatrices, int columnsMatrices) throws JSONException {
        JSONObject rowObject = new JSONObject();

        for (int i = 0; i < rowsMatrices; i++) {
            JSONObject columnObject = new JSONObject();
            for (int j = 0; j < columnsMatrices; j++) {
                String tag = tagId + String.valueOf(i) + String.valueOf(j);
                EditText editText = gridLayout.findViewWithTag(tag);
                columnObject.put(String.valueOf(j), editText.getText().toString());
            }
            rowObject.put(String.valueOf(i), columnObject);
        }

        return rowObject;
    }

    public void fillUpMatrixByJson(GridLayout gridLayout, String tagId,JSONObject jsonObject, int rowsMatrices, int columnsMatrices) throws JSONException {
        for(int i = 0; i < rowsMatrices; i++)
            for (int j = 0; j < columnsMatrices; j++)
            {
                String tag = tagId + String.valueOf(i) + String.valueOf(j);
                EditText editText = gridLayout.findViewWithTag(tag);
                String text = jsonObject.getJSONObject(String.valueOf(i)).getString(String.valueOf(j));
                editText.setText(text);
            }
    }

    public boolean allIsFill(GridLayout gridLayout, String tagId, int rowsMatrices, int columnsMatrices) {
        for (int i = 0; i < rowsMatrices; i++)
            for (int j = 0; j < columnsMatrices; j++) {
                String tag = tagId + String.valueOf(i) + String.valueOf(j);
                EditText editText = gridLayout.findViewWithTag(tag);
                try {
                    Double.parseDouble(editText.getText().toString());
                } catch (Exception e) {
                    return false;
                }
            }
        return true;
    }

    public void generateAndFillUpMatrixResult(GridLayout gridLayout, double[][] matrix) {
        gridLayout.removeAllViews();
        gridLayout.setColumnCount(matrix[0].length);
        for (double[] aMatrix : matrix) {
            for (double anAMatrix : aMatrix) {
                TextView textView = new TextView(context);
                Style.setStyleTextViewForGridLayout(context, textView);
                textView.setText(String.valueOf(anAMatrix));
                gridLayout.addView(textView);
            }
        }
    }

    public void fillUpMatrix(GridLayout gridLayout, String tagId, double[][] matrix) {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                String tag = tagId + String.valueOf(i) + String.valueOf(j);
                EditText editText = gridLayout.findViewWithTag(tag);
                editText.setText(String.valueOf(matrix[i][j]));
            }
        }
    }

    public void clearMatrix(GridLayout gridLayout, String tagId, int rowsMatrices, int columnsMatrices) {
        for (int i = 0; i < rowsMatrices; i++)
            for (int j = 0; j < columnsMatrices; j++) {
                String tag = tagId + String.valueOf(i) + String.valueOf(j);
                EditText editText = gridLayout.findViewWithTag(tag);
                editText.getText().clear();
            }

    }

}
