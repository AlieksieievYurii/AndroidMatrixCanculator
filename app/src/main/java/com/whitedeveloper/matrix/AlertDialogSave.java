package com.whitedeveloper.matrix;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AlertDialogSave extends Dialog
{
    public interface CallBackFromAlertDialogSave{
        void callBack(String name);
    }

    private CallBackFromAlertDialogSave callBack;

    public AlertDialogSave(@NonNull Context context, CallBackFromAlertDialogSave callBack) {
        super(context);
        this.callBack = callBack;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alert_dialog_name_saving);

        init();
    }

    private void init()
    {
        final Button btnSave = findViewById(R.id.btn_save);
        final Button btnCancel = findViewById(R.id.btn_cancel);
        final EditText edtName = findViewById(R.id.edt_name_save);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!edtName.getText().toString().trim().equals(""))
                {
                    callBack.callBack(edtName.getText().toString());
                    hide();
                    dismiss();
                }else
                    Toast.makeText(getContext(),R.string.text_name_can_not_be_empty,Toast.LENGTH_SHORT).show();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hide();
                dismiss();
            }
        });
    }
}
