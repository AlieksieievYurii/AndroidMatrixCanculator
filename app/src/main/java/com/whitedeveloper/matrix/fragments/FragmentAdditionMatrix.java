package com.whitedeveloper.matrix.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.whitedeveloper.matrix.*;
import com.whitedeveloper.matrix.operationModules.AdditionMatrix;

import static com.whitedeveloper.matrix.fragments.Tags.TAG_ID_MATRIX_A;
import static com.whitedeveloper.matrix.fragments.Tags.TAG_ID_MATRIX_B;

public class FragmentAdditionMatrix extends Fragment implements
        AdapterView.OnItemSelectedListener,
        TextWatcher,
        OnPressSaveResualtListener {

    private View view;

    private Spinner spCountRowsMatrices;
    private Spinner spCountColumnsMatrices;

    private GridLayout glMatrixA;
    private GridLayout glMatrixB;
    private GridLayout glMatrixResult;

    private int rowsMatrices;
    private int columnsMatrices;

    private ManagerMatrix managerMatrix;
    private RelativeLayout tvResult;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_addition_matrix, container, false);
        init();
        return view;
    }


    private void init() {
        spCountRowsMatrices = view.findViewById(R.id.sp_count_rows_matrix);
        spCountColumnsMatrices = view.findViewById(R.id.sp_count_columns_matrix);

        glMatrixA = view.findViewById(R.id.gl_matrix_a);
        glMatrixB = view.findViewById(R.id.gl_matrix_b);
        glMatrixResult = view.findViewById(R.id.gl_matrix_result);
        tvResult = view.findViewById(R.id.rl_result);

        spCountColumnsMatrices.setOnItemSelectedListener(this);
        spCountRowsMatrices.setOnItemSelectedListener(this);

        managerMatrix = new ManagerMatrix(getContext());


        final Button btnRun = view.findViewById(R.id.btn_run);
        btnRun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calculate();

            }
        });

        final Button btnClear = view.findViewById(R.id.btn_clear);
        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                managerMatrix.clearMatrix(glMatrixA, TAG_ID_MATRIX_A, rowsMatrices, columnsMatrices);
                managerMatrix.clearMatrix(glMatrixB, TAG_ID_MATRIX_B, rowsMatrices, columnsMatrices);
                removeResult();
            }
        });
    }

    private void calculate() {
        if (managerMatrix.allIsFill(glMatrixA, TAG_ID_MATRIX_A, rowsMatrices, columnsMatrices) &&
                managerMatrix.allIsFill(glMatrixB, TAG_ID_MATRIX_B, rowsMatrices, columnsMatrices)) {
            AdditionMatrix additionMatrix = new AdditionMatrix(managerMatrix.readMatrix(glMatrixA, TAG_ID_MATRIX_A, rowsMatrices, columnsMatrices),
                    managerMatrix.readMatrix(glMatrixB, TAG_ID_MATRIX_B, rowsMatrices, columnsMatrices));

            showResult(additionMatrix.additionMatrix());

        } else
            Toast.makeText(getContext(), R.string.text_warming_fill_up_matrix, Toast.LENGTH_SHORT).show();
    }

    private void showResult(double[][] matrixResult) {
        HidenKeyboard.hideKeyboardFrom(getContext(), view);
        tvResult.setVisibility(View.VISIBLE);
        managerMatrix.generateAndFillUpMatrixResult(glMatrixResult, matrixResult);
    }

    private void removeResult() {
        glMatrixResult.removeAllViews();
        tvResult.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        removeResult();

        HidenKeyboard.hideKeyboardFrom(getContext(), view);

        rowsMatrices = Integer.parseInt(spCountRowsMatrices.getSelectedItem().toString());
        columnsMatrices = Integer.parseInt(spCountColumnsMatrices.getSelectedItem().toString());

        managerMatrix.generateMatrix(glMatrixA, TAG_ID_MATRIX_A, rowsMatrices, columnsMatrices, this);
        managerMatrix.generateMatrix(glMatrixB, TAG_ID_MATRIX_B, rowsMatrices, columnsMatrices, this);


    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        if (tvResult.getVisibility() == View.VISIBLE)
            removeResult();
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }

    @Override
    public void onPressSave()
    {
        //TODO Here gotta implement saving result!
        AlertDialogSave alertDialogSave = new AlertDialogSave(getContext(), new AlertDialogSave.CallBackFromAlertDialogSave() {
            @Override
            public void callBack(String name) {
                Log.i("NAME",name);
            }
        });
        alertDialogSave.show();
    }
}
