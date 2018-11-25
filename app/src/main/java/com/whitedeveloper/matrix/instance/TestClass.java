package com.whitedeveloper.matrix.instance;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.util.Arrays;
import java.util.Map;

public class TestClass {
    public static void test(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SavingInstance.KEY_SHARED_MATRICES, Context.MODE_PRIVATE);

        Map<String, ?> maps = sharedPreferences.getAll();

        for (Map.Entry<String, ?> entry : maps.entrySet()) {
            Log.i("LOL", entry.getKey() + " : " + entry.getValue().toString());
        }
        testTwo(context);
    }

    public static void testTwo(Context context) {
        try {
            GetInstance getInstance = new GetInstance(context, "kek");

            switch (getInstance.getAction()) {
                case ADDITION:
                    Log.i("MATRIX_A", Arrays.deepToString(getInstance.getMatrixA()));
                    Log.i("MATRIX_B", Arrays.deepToString(getInstance.getMatrixB()));
                    Log.i("MATRIX_RESULT_ADD", Arrays.deepToString(getInstance.getMatrixResult()));
                    break;

                case SUBTRACTION:
                    Log.i("MATRIX_A", Arrays.deepToString(getInstance.getMatrixA()));
                    Log.i("MATRIX_B", Arrays.deepToString(getInstance.getMatrixB()));
                    Log.i("MATRIX_RESULT_SUBTR", Arrays.deepToString(getInstance.getMatrixResult()));
                    break;

                case MULTIPLICATION:
                    Log.i("MATRIX_A", Arrays.deepToString(getInstance.getMatrixA()));
                    Log.i("MATRIX_B", Arrays.deepToString(getInstance.getMatrixB()));
                    Log.i("MATRIX_RESULT_MULTI", Arrays.deepToString(getInstance.getMatrixResult()));
                    break;
                case TRANSPOSING:
                    Log.i("MATRIX", Arrays.deepToString(getInstance.getMatrixA()));
                    Log.i("MATRIX_RESULT_TRAN", Arrays.deepToString(getInstance.getMatrixResult()));
                    break;
                case DETERMINATION:
                    Log.i("MATRIX", Arrays.deepToString(getInstance.getMatrixA()));
                    Log.i("DETERMINANT", String.valueOf(getInstance.getDeterminant()));
                    break;
                case INVERSION:
                    Log.i("MATRIX", Arrays.deepToString(getInstance.getMatrixA()));
                    Log.i("MATRIX_RESULT_IN", Arrays.deepToString(getInstance.getMatrixResult()));
                    break;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
