package com.whitedeveloper.matrix.ListView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;
import com.whitedeveloper.matrix.Action;
import com.whitedeveloper.matrix.ActivityShowSavedMatrix;
import com.whitedeveloper.matrix.R;
import com.whitedeveloper.matrix.fragments.SetMatrix;
import com.whitedeveloper.matrix.instance.GetInstance;
import com.whitedeveloper.matrix.instance.SavingInstance;


public class SavingHelper implements ListOfSavingMatrices.CallBack {

    private final Context context;
    private ListOfSavingMatrices listOfSavingMatrices;
    private SetMatrix setMatrix;
    private GetInstance getInstance;

    public SavingHelper(Context context) {
        this.context = context;
    }

    public void callAlertListSavingForMatrix(SetMatrix setMatrix) {
        this.setMatrix = setMatrix;
        listOfSavingMatrices = new ListOfSavingMatrices(context, this);
        listOfSavingMatrices.show();

    }

    @Override
    public void selectedItem(String name) {
        try {
            getInstance = new GetInstance(context, name);

            if (getInstance.getAction() == Action.DETERMINATION) {
                Toast.makeText(context, R.string.text_not_allowed, Toast.LENGTH_SHORT).show();
                return;
            }

            setMatrix.setMatrix(getInstance.getMatrixResult());
            setMatrix.setSizeMatrix(getInstance.getMatrixResult().length, getInstance.getMatrixResult()[0].length);
            listOfSavingMatrices.hide();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void removeItem(String name) {
        SavingInstance.removeSaving(context,name);
    }

    @Override
    public void showItem(String name) {
        Intent intent = new Intent(context,ActivityShowSavedMatrix.class);
        intent.putExtra(ActivityShowSavedMatrix.EXTRA_NAME_SAVING,name);
        context.startActivity(intent);
    }
}
