package com.whitedeveloper.matrix.instance;

import android.content.Context;
import android.content.SharedPreferences;
import com.whitedeveloper.matrix.tags.Action;
import com.whitedeveloper.matrix.ListView.ItemMatrices;
import com.whitedeveloper.matrix.operationModules.JsonMatrix;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Map;

import static com.whitedeveloper.matrix.tags.TagKeys.*;

public class SavingInstance {



    private Context context;
    private String nameSaving;
    private Action action;
    private double determinant;
    private double[][] matrixA;
    private double[][] matrixB;
    private double[][] matrixResult;
    private double[][] matrixL;
    private double[][] matrixU;

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

    public Context getContext() {
        return context;
    }

    public Action getAction() {
        return action;
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

    public SavingInstance setMatrixL(double[][] matrixL) {
        this.matrixL = matrixL;
        return this;
    }

    public SavingInstance setMatrixU(double[][] matrixU) {
        this.matrixU = matrixU;
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
            case SEPARATION:
                saveSeparation();
                break;
        }
    }

    private void saveSeparation() throws Exception
    {
        SharedPreferences.Editor sharedPreferences = context.getSharedPreferences(KEY_SHARED_MATRICES,Context.MODE_PRIVATE).edit();

        JSONObject jsonObject = new JSONObject();
        jsonObject.put(KEY_SHARED_ACTION,Action.SEPARATION.toString());
        jsonObject.put(KEY_SHARED_MATRIX_A,JsonMatrix.getJsonFromMatrix(matrixA));
        jsonObject.put(KEY_SHARED_MATRIX_L,JsonMatrix.getJsonFromMatrix(matrixL));
        jsonObject.put(KEY_SHARED_MATRIX_U,JsonMatrix.getJsonFromMatrix(matrixU));

        sharedPreferences.putString(nameSaving,jsonObject.toString());
        sharedPreferences.apply();
    }

    private void saveTransposing() throws Exception {
        SharedPreferences.Editor sharedPreferences = context.getSharedPreferences(KEY_SHARED_MATRICES, Context.MODE_PRIVATE).edit();

        JSONObject jsonObject = new JSONObject();
        jsonObject.put(KEY_SHARED_ACTION, Action.TRANSPOSING.toString());
        jsonObject.put(KEY_SHARED_MATRIX_A, JsonMatrix.getJsonFromMatrix(matrixA));
        jsonObject.put(KEY_SHARED_MATRIX_RESULT, JsonMatrix.getJsonFromMatrix(matrixResult));

        sharedPreferences.putString(nameSaving, jsonObject.toString());
        sharedPreferences.apply();

    }

    private void saveDetermination() throws Exception {
        SharedPreferences.Editor sharedPreferences = context.getSharedPreferences(KEY_SHARED_MATRICES, Context.MODE_PRIVATE).edit();

        JSONObject jsonObject = new JSONObject();
        jsonObject.put(KEY_SHARED_ACTION, Action.DETERMINATION.toString());
        jsonObject.put(KEY_SHARED_MATRIX_A, JsonMatrix.getJsonFromMatrix(matrixA));
        jsonObject.put(KEY_SHARED_DETERMINANT, determinant);

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

    public static ArrayList<ItemMatrices> getAllSaving(Context context) {
        Map<String, ?> all = context.getSharedPreferences(KEY_SHARED_MATRICES, Context.MODE_PRIVATE).getAll();
        ArrayList<ItemMatrices> arrayList = new ArrayList<>();
        for (Map.Entry<String, ?> entry : all.entrySet())
            arrayList.add(getItemMatrix(context, entry.getKey()));
        return arrayList;
    }

    private static ArrayList<String> getNamesAllSaving(Context context) {
        Map<String, ?> all = context.getSharedPreferences(KEY_SHARED_MATRICES, Context.MODE_PRIVATE).getAll();
        ArrayList<String> arrayList = new ArrayList<>();
        for (Map.Entry<String, ?> entry : all.entrySet())
            arrayList.add(entry.getKey());
        return arrayList;
    }

    public static boolean isNameExisted(Context context, String nameSaving) {
        ArrayList<String> existedNames = getNamesAllSaving(context);

        for (String str : existedNames)
            if (str.equals(nameSaving))
                return true;
        return false;
    }

    private static ItemMatrices getItemMatrix(Context context, String nameSaving) {
        ItemMatrices itemMatrices = new ItemMatrices();
        SavedInstance savedInstance;
        try {
            savedInstance = new SavedInstance(context, nameSaving);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        itemMatrices.setNameSaving(nameSaving);
        itemMatrices.setAction(savedInstance.getAction());

        if (savedInstance.getAction() == Action.DETERMINATION)
            itemMatrices.setResultItem(String.valueOf(savedInstance.getDeterminant()));
        else {
            itemMatrices.setCountRows(savedInstance.getMatrixResult().length);
            itemMatrices.setCountColumns(savedInstance.getMatrixResult()[0].length);
        }

        return itemMatrices;
    }

    public static void removeSaving(Context context, String nameSaving) {
        context.getSharedPreferences(KEY_SHARED_MATRICES, Context.MODE_PRIVATE)
                .edit().
                remove(nameSaving)
                .apply();
    }

}
