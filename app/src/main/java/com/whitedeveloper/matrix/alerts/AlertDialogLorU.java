package com.whitedeveloper.matrix.alerts;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.whitedeveloper.matrix.R;

public class AlertDialogLorU extends AlertDialog {

    public interface CallBack {
        void selectedMatrixL();

        void selectedMatrixU();
    }

    private final CallBack callBack;

    public AlertDialogLorU(Context context, CallBack callBack) {
        super(context);
        this.callBack = callBack;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alert_dialog_l_or_u);
        init();
    }

    private void init() {
        final Button btnMatrixL = findViewById(R.id.btn_matrix_l);
        final Button btnMatrixU = findViewById(R.id.btn_matrix_u);

        btnMatrixL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callBack.selectedMatrixL();
                hide();
                dismiss();
            }
        });

        btnMatrixU.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callBack.selectedMatrixU();
                hide();
                dismiss();
            }
        });
    }
}
