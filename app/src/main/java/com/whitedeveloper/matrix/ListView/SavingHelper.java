package com.whitedeveloper.matrix.ListView;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;
import com.whitedeveloper.matrix.alerts.AlertDialogLorU;
import com.whitedeveloper.matrix.tags.Action;
import com.whitedeveloper.matrix.activities.ActivityShowSavedMatrix;
import com.whitedeveloper.matrix.R;
import com.whitedeveloper.matrix.fragments.SetMatrix;
import com.whitedeveloper.matrix.instance.SavedInstance;
import com.whitedeveloper.matrix.instance.SavingInstance;

import static com.whitedeveloper.matrix.tags.TagKeys.EXTRA_NAME_SAVING;


public class SavingHelper implements ListOfSavingMatrices.CallBack, AlertDialogLorU.CallBack {

    private final Context context;
    private ListOfSavingMatrices listOfSavingMatrices;
    private SetMatrix setMatrix;
    private SavedInstance savedInstance;

    public SavingHelper(Context context) {
        this.context = context;
    }

    public void callAlertListSavingForMatrix(SetMatrix setMatrix) {
        this.setMatrix = setMatrix;
        listOfSavingMatrices = new ListOfSavingMatrices(context, this);
        listOfSavingMatrices.show();

    }

    @Override
    public void selectedItem(String nameSaving) {
        try {
            savedInstance = new SavedInstance(context, nameSaving);

            if (savedInstance.getAction() == Action.DETERMINATION) {
                Toast.makeText(context, R.string.text_not_allowed, Toast.LENGTH_SHORT).show();
                return;
            } else if (savedInstance.getAction() == Action.SEPARATION) {
                AlertDialogLorU alertDialogLorU = new AlertDialogLorU(context, this);
                alertDialogLorU.show();
                return;
            }

            setMatrix.setMatrix(savedInstance.getMatrixResult());
            setMatrix.setSizeMatrix(savedInstance.getMatrixResult().length, savedInstance.getMatrixResult()[0].length);
            listOfSavingMatrices.hide();
            listOfSavingMatrices.dismiss();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void selectedMatrixA(String nameSaving) {
        try {
            savedInstance = new SavedInstance(context, nameSaving);

            setMatrix.setMatrix(savedInstance.getMatrixA());
            setMatrix.setSizeMatrix(savedInstance.getMatrixA().length, savedInstance.getMatrixA()[0].length);
            listOfSavingMatrices.hide();
            listOfSavingMatrices.dismiss();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void selectedMatrixB(String nameSaving) {
        try {
            savedInstance = new SavedInstance(context, nameSaving);

            setMatrix.setMatrix(savedInstance.getMatrixB());
            setMatrix.setSizeMatrix(savedInstance.getMatrixB().length, savedInstance.getMatrixB()[0].length);
            listOfSavingMatrices.hide();
            listOfSavingMatrices.dismiss();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void selectedMatrix(String nameSaving) {
        selectedMatrixA(nameSaving);
        //"MatrixA" equals Matrix
        //In SavedInstance object is "MatrixA", it means just "Matrix"
    }

    @Override
    public void removeItem(String nameSaving) {
        SavingInstance.removeSaving(context, nameSaving);
    }

    @Override
    public void showItem(String nameSaving) {
        Intent intent = new Intent(context, ActivityShowSavedMatrix.class);
        intent.putExtra(EXTRA_NAME_SAVING, nameSaving);
        context.startActivity(intent);
    }

    @Override
    public void selectedMatrixL() {
        setMatrix.setMatrix(savedInstance.getMatrixL());
        setMatrix.setSizeMatrix(savedInstance.getMatrixL().length, savedInstance.getMatrixL()[0].length);
        listOfSavingMatrices.hide();
        listOfSavingMatrices.dismiss();
    }

    @Override
    public void selectedMatrixU() {
        setMatrix.setMatrix(savedInstance.getMatrixU());
        setMatrix.setSizeMatrix(savedInstance.getMatrixU().length, savedInstance.getMatrixU()[0].length);
        listOfSavingMatrices.hide();
        listOfSavingMatrices.dismiss();
    }
}
