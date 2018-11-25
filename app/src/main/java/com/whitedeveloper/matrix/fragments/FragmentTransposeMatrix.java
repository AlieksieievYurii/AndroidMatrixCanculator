package com.whitedeveloper.matrix.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.whitedeveloper.matrix.*;
import com.whitedeveloper.matrix.instance.SavingInstance;
import com.whitedeveloper.matrix.operationModules.TransposeMatrix;

import static com.whitedeveloper.matrix.fragments.Tags.TAG_ID_MATRIX_A;

public class FragmentTransposeMatrix extends Fragment implements AdapterView.OnItemSelectedListener, TextWatcher, OnPressSaveResualtListener {
    private View view;

    private Spinner spRowsMatrix;
    private Spinner spColumnMatrix;

    private GridLayout glMatrix;
    private GridLayout glResult;

    private RelativeLayout rlResult;

    private ManagerMatrix managerMatrix;
    private int numberOfRows;
    private int numberOfColumns;

    private double[][] matrix;
    private double[][] matrixResult;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_transpose_matrix, container, false);
        init();
        return view;
    }

    private void init() {
        spRowsMatrix = view.findViewById(R.id.sp_count_rows_matrix);
        spColumnMatrix = view.findViewById(R.id.sp_count_columns_matrix);

        spRowsMatrix.setOnItemSelectedListener(this);
        spColumnMatrix.setOnItemSelectedListener(this);

        glMatrix = view.findViewById(R.id.gl_matrix_transpose);
        glResult = view.findViewById(R.id.gl_matrix_result);

        rlResult = view.findViewById(R.id.rl_result);

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
                managerMatrix.clearMatrix(glMatrix, TAG_ID_MATRIX_A, numberOfRows, numberOfColumns);
                removeResult();
            }
        });
    }

    private void calculate() {
        if (managerMatrix.allIsFill(glMatrix, TAG_ID_MATRIX_A, numberOfRows, numberOfColumns)) {
            matrix = managerMatrix.readMatrix(glMatrix, TAG_ID_MATRIX_A, numberOfRows, numberOfColumns);
            TransposeMatrix transposeMatrix = new TransposeMatrix(matrix);

            matrixResult = transposeMatrix.transposeMatrix();
            showResult(matrixResult);
        } else
            Toast.makeText(getContext(), R.string.text_warming_fill_up_matrix, Toast.LENGTH_SHORT).show();
    }

    private void showResult(double[][] matrixResult) {
        HidenKeyboard.hideKeyboardFrom(getContext(), view);
        rlResult.setVisibility(View.VISIBLE);
        managerMatrix.generateAndFillUpMatrixResult(glResult, matrixResult);
    }

    private void removeResult() {
        glResult.removeAllViews();
        rlResult.setVisibility(View.INVISIBLE);
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        removeResult();
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        HidenKeyboard.hideKeyboardFrom(getContext(), view);
        numberOfRows = Integer.parseInt(spRowsMatrix.getSelectedItem().toString());
        numberOfColumns = Integer.parseInt(spColumnMatrix.getSelectedItem().toString());

        managerMatrix.generateMatrix(glMatrix, TAG_ID_MATRIX_A, numberOfRows, numberOfColumns, this);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public void onPressSave() {
        if (rlResult.getVisibility() == View.INVISIBLE) {
            Toast.makeText(getContext(), R.string.text_calculate, Toast.LENGTH_SHORT).show();
            return;
        }

        AlertDialogSave alertDialogSave = new AlertDialogSave(getContext(), new AlertDialogSave.CallBackFromAlertDialogSave() {
            @Override
            public void callBack(String name) {
                try {
                    new SavingInstance(getContext())
                            .setNameSaving(name)
                            .setAction(Action.TRANSPOSING)
                            .setMatrixA(matrix)
                            .setMatrixResult(matrixResult)
                            .commit();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        alertDialogSave.show();
    }
}
