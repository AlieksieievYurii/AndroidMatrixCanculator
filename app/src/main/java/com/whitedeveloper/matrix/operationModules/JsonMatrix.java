package com.whitedeveloper.matrix.operationModules;

import org.json.JSONException;
import org.json.JSONObject;

public class JsonMatrix {
    public static String getJsonFromMatrix(double[][] matrix) throws Exception {
        JSONObject rowObject = new JSONObject();

        for (int i = 0; i < matrix.length; i++) {
            JSONObject columnObject = new JSONObject();
            for (int j = 0; j < matrix[i].length; j++) {
                columnObject.put(String.valueOf(j), matrix[i][j]);
            }
            rowObject.put(String.valueOf(i), columnObject);
        }

        return rowObject.toString();
    }

    public static double[][] getMatrixFromJsonObject(JSONObject jsonMatrix) throws JSONException {
        int countRow = jsonMatrix.length();
        int countColumn = jsonMatrix.getJSONObject("0").length();

        double[][] matrix = new double[countRow][countColumn];

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++)
                matrix[i][j] = jsonMatrix.getJSONObject(String.valueOf(i)).getDouble(String.valueOf(j));
        }

        return matrix;
    }
}
