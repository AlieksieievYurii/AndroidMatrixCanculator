package com.whitedeveloper.matrix.alerts;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.whitedeveloper.matrix.R;
import com.whitedeveloper.matrix.instance.SavingInstance;

public class AlertDialogSave extends AlertDialog {
    public interface CallBackFromAlertDialogSave {
        void callBack(String name);
    }

    private CallBackFromAlertDialogSave callBack;
    private TextView tvError;

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

    private void init() {
        tvError = findViewById(R.id.tv_error);

        final Button btnSave = findViewById(R.id.btn_save);
        final Button btnCancel = findViewById(R.id.btn_cancel);
        final EditText edtName = findViewById(R.id.edt_name_save);
        edtName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (tvError.getVisibility() == View.VISIBLE)
                    tvError.setVisibility(View.GONE);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!edtName.getText().toString().trim().equals("")) {
                    if (SavingInstance.isNameExisted(getContext(), edtName.getText().toString())) {
                        if (tvError.getVisibility() == View.GONE) {
                            tvError.setVisibility(View.VISIBLE);
                            tvError.setText(R.string.text_existed_already);
                        }
                        return;
                    }
                    callBack.callBack(edtName.getText().toString());
                    hide();
                    dismiss();
                } else
                if (tvError.getVisibility() == View.GONE) {
                    tvError.setVisibility(View.VISIBLE);
                    tvError.setText(R.string.cannot_be_empty);
                }

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
