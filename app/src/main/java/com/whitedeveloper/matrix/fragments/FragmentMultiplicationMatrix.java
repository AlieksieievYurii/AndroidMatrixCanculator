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
import com.whitedeveloper.matrix.HidenKeyboard;
import com.whitedeveloper.matrix.ManagerMatrix;
import com.whitedeveloper.matrix.OnPressSaveResualtListener;
import com.whitedeveloper.matrix.R;
import com.whitedeveloper.matrix.operationModules.MultiplicationMatrix;

import static com.whitedeveloper.matrix.fragments.Tags.TAG_ID_MATRIX_A;
import static com.whitedeveloper.matrix.fragments.Tags.TAG_ID_MATRIX_B;

public class FragmentMultiplicationMatrix extends Fragment implements
        AdapterView.OnItemSelectedListener,
        TextWatcher,
        OnPressSaveResualtListener {
    private View view;

    private Spinner spCountRowsMatrixA;
    private Spinner spCountColumnsMatrixA;
    private Spinner spCountColumnsMatrixB;

    private GridLayout glMatrixA;
    private GridLayout glMatrixB;
    private GridLayout glMatrixResult;

    private RelativeLayout rlResult;

    private ManagerMatrix managerMatrix;

    private int rowsMatrixA;
    private int columnsMatrixA;
    private int columnsMatrixB;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_multiplication_matrix, container, false);
        init();
        return view;
    }

    private void init() {
        spCountColumnsMatrixA = view.findViewById(R.id.sp_count_columns_matrix_a);
        spCountRowsMatrixA = view.findViewById(R.id.sp_count_rows_matrix_a);
        spCountColumnsMatrixB = view.findViewById(R.id.sp_count_columns_matrix_b);

        glMatrixA = view.findViewById(R.id.gl_matrix_a);
        glMatrixB = view.findViewById(R.id.gl_matrix_b);
        glMatrixResult = view.findViewById(R.id.gl_matrix_result);

        rlResult = view.findViewById(R.id.rl_result);

        managerMatrix = new ManagerMatrix(getContext());

        spCountRowsMatrixA.setOnItemSelectedListener(this);
        spCountColumnsMatrixA.setOnItemSelectedListener(this);
        spCountColumnsMatrixB.setOnItemSelectedListener(this);

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
                managerMatrix.clearMatrix(glMatrixA, TAG_ID_MATRIX_A, rowsMatrixA, columnsMatrixA);
                managerMatrix.clearMatrix(glMatrixB, TAG_ID_MATRIX_B, columnsMatrixA, columnsMatrixB);
                removeResult();
            }
        });
    }

    private void calculate() {
        if (managerMatrix.allIsFill(glMatrixA, TAG_ID_MATRIX_A, rowsMatrixA, columnsMatrixA) &&
                managerMatrix.allIsFill(glMatrixB, TAG_ID_MATRIX_B, columnsMatrixA, columnsMatrixB)) {
            MultiplicationMatrix multiplicationMatrix = new MultiplicationMatrix(
                    managerMatrix.readMatrix(glMatrixA, TAG_ID_MATRIX_A, rowsMatrixA, columnsMatrixA),
                    managerMatrix.readMatrix(glMatrixB, TAG_ID_MATRIX_B, columnsMatrixA, columnsMatrixB));
            showResult(multiplicationMatrix.multiplicationMatrices());
        } else
            Toast.makeText(getContext(), R.string.text_warming_fill_up_matrix, Toast.LENGTH_SHORT).show();
    }

    private void showResult(double[][] matrixResult) {
        HidenKeyboard.hideKeyboardFrom(getContext(), view);
        rlResult.setVisibility(View.VISIBLE);
        managerMatrix.generateAndFillUpMatrixResult(glMatrixResult, matrixResult);
    }

    private void removeResult() {
        glMatrixResult.removeAllViews();
        rlResult.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

        removeResult();
        HidenKeyboard.hideKeyboardFrom(getContext(), view);

        rowsMatrixA = Integer.parseInt(spCountRowsMatrixA.getSelectedItem().toString());
        columnsMatrixA = Integer.parseInt(spCountColumnsMatrixA.getSelectedItem().toString());
        columnsMatrixB = Integer.parseInt(spCountColumnsMatrixB.getSelectedItem().toString());

        managerMatrix.generateMatrix(glMatrixA, TAG_ID_MATRIX_A, rowsMatrixA, columnsMatrixA, this);
        managerMatrix.generateMatrix(glMatrixB, TAG_ID_MATRIX_B, columnsMatrixA, columnsMatrixB, this);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        if (rlResult.getVisibility() == View.VISIBLE)
            removeResult();
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }

    @Override
    public void onPressSave() {
        //TODO Here gotta implement saving result!
    }
}
