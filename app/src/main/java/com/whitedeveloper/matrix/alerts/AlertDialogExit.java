package com.whitedeveloper.matrix.alerts;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import com.whitedeveloper.matrix.R;

public class AlertDialogExit {

    public interface CallBack {
        void doFinishActivity();
    }

    private AlertDialog alertDialog;

    public AlertDialogExit(final Context context, final CallBack callBack) {


        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setPositiveButton(context.getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                callBack.doFinishActivity();
                alertDialog.cancel();
                alertDialog.dismiss();
            }
        });

        builder.setNegativeButton(context.getResources().getString(R.string.no), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                alertDialog.cancel();
                alertDialog.dismiss();
            }
        });

        builder.setMessage(context.getResources().getString(R.string.question_exit));

        alertDialog = builder.create();

        alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                alertDialog.getButton(DialogInterface.BUTTON_POSITIVE).setTextColor(ContextCompat.getColor(context, R.color.colorPrimary));
                alertDialog.getButton(DialogInterface.BUTTON_NEGATIVE).setTextColor(ContextCompat.getColor(context, R.color.colorPrimary));
            }
        });
    }

    public void showAlert() {
        alertDialog.show();
    }
}
