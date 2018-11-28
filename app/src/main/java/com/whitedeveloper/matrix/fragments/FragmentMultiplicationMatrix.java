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
import com.whitedeveloper.matrix.ListView.SavingHelper;
import com.whitedeveloper.matrix.instance.SavingInstance;
import com.whitedeveloper.matrix.operationModules.MultiplicationMatrix;

import static com.whitedeveloper.matrix.fragments.Tags.TAG_ID_MATRIX_A;
import static com.whitedeveloper.matrix.fragments.Tags.TAG_ID_MATRIX_B;

public class FragmentMultiplicationMatrix extends Fragment implements
        AdapterView.OnItemSelectedListener,
        TextWatcher,
        OnPressSaveResualtListener, View.OnLongClickListener {
    private View view;

    private Spinner spCountRowsMatrices;
    private Spinner spCountColumnsMatrixA;
    private Spinner spCountColumnsMatrixB;


    private GridLayout glMatrixA;
    private GridLayout glMatrixB;
    private GridLayout glMatrixResult;

    private RelativeLayout rlResult;
    private SavingHelper savingHelper;
    private ManagerMatrix managerMatrix;

    private int rowsMatrixA;
    private int columnsMatrixA;
    private int columnsMatrixB;
    private int bufferRowsMatrixA;
    private int bufferColumnsMatrixA;
    private int bufferColumnsMatrixB;

    private double[][] matrixA;
    private double[][] matrixB;
    private double[][] matrixResult;
    private SetMatrix setMatrixA;
    private SetMatrix setMatrixB;

    private boolean setMatrixAFromSaving = false;
    private boolean setMatrixBFromSaving = false;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.i("TAG","TEST");
        view = inflater.inflate(R.layout.fragment_multiplication_matrix, container, false);
        init();
        return view;
    }

    private void init() {
        spCountColumnsMatrixA = view.findViewById(R.id.sp_count_columns_matrix_a);
        spCountRowsMatrices = view.findViewById(R.id.sp_count_rows_matrix_a);
        spCountColumnsMatrixB = view.findViewById(R.id.sp_count_columns_matrix_b);

        glMatrixA = view.findViewById(R.id.gl_matrix_a);
        glMatrixB = view.findViewById(R.id.gl_matrix_b);
        glMatrixResult = view.findViewById(R.id.gl_matrix_result);

        savingHelper = new SavingHelper(getContext());

        final TextView tvMatrixA = view.findViewById(R.id.tv_matrix_a);
        final TextView tvMatrixB = view.findViewById(R.id.tv_matrix_b);
        tvMatrixA.setOnLongClickListener(this);
        tvMatrixB.setOnLongClickListener(this);

        rlResult = view.findViewById(R.id.rl_result);

        managerMatrix = new ManagerMatrix(getContext());

        spCountRowsMatrices.setOnItemSelectedListener(this);
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

        setMatrixA = new SetMatrix() {
            @Override
            public void setMatrix(double[][] matrix) {
                matrixA = matrix;
            }

            @Override
            public void setSizeMatrix(int countRows, int countColumns) {
                if (countRows == rowsMatrixA && countColumns == columnsMatrixA)
                    managerMatrix.fillUpMatrix(glMatrixA, TAG_ID_MATRIX_A, matrixA);
                else {
                    setColumnsForSpinnerA(countColumns);
                    setRowsMatrixA(countRows);
                    setMatrixAFromSaving = true;
                }
            }
        };

        setMatrixB = new SetMatrix() {
            @Override
            public void setMatrix(double[][] matrix) {
                matrixB = matrix;

            }

            @Override
            public void setSizeMatrix(int countRows, int countColumns) {
                if (countRows == columnsMatrixA && countColumns == columnsMatrixB)
                    managerMatrix.fillUpMatrix(glMatrixB, TAG_ID_MATRIX_B, matrixB);
                else if (countRows == columnsMatrixA) {
                    setColumnsForSpinnerB(countColumns);
                    setMatrixBFromSaving = true;
                } else
                    Toast.makeText(getContext(), "Rows matrix B not equals matrix A", Toast.LENGTH_SHORT).show();

            }
        };
    }

    private void setRowsMatrixA(int countRows) {
        bufferRowsMatrixA = countRows;
        for (int i = 0; i < spCountRowsMatrices.getCount(); i++)
            if (Integer.parseInt((String) spCountRowsMatrices.getItemAtPosition(i)) == countRows)
                spCountRowsMatrices.setSelection(i);
    }

    private void setColumnsForSpinnerB(int countColumns) {
        bufferColumnsMatrixB = countColumns;
        bufferRowsMatrixA = rowsMatrixA;
        for (int i = 0; i < spCountColumnsMatrixB.getCount(); i++)
            if (Integer.parseInt((String) spCountColumnsMatrixB.getItemAtPosition(i)) == countColumns)
                spCountColumnsMatrixB.setSelection(i);

    }

    private void setColumnsForSpinnerA(int countColumns) {
        bufferColumnsMatrixA = countColumns;
        for (int i = 0; i < spCountColumnsMatrixA.getCount(); i++)
            if (Integer.parseInt((String) spCountColumnsMatrixA.getItemAtPosition(i)) == countColumns)
                spCountColumnsMatrixA.setSelection(i);
    }

    private void calculate() {
        if (managerMatrix.allIsFill(glMatrixA, TAG_ID_MATRIX_A, rowsMatrixA, columnsMatrixA) &&
                managerMatrix.allIsFill(glMatrixB, TAG_ID_MATRIX_B, columnsMatrixA, columnsMatrixB)) {
            matrixA = managerMatrix.readMatrix(glMatrixA, TAG_ID_MATRIX_A, rowsMatrixA, columnsMatrixA);
            matrixB = managerMatrix.readMatrix(glMatrixB, TAG_ID_MATRIX_B, columnsMatrixA, columnsMatrixB);

            MultiplicationMatrix multiplicationMatrix = new MultiplicationMatrix(matrixA, matrixB);
            matrixResult = multiplicationMatrix.multiplicationMatrices();
            showResult(matrixResult);
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


        switch (adapterView.getId())
        {
            case R.id.sp_count_rows_matrix_a:
                rowsMatrixA = Integer.parseInt(spCountRowsMatrices.getSelectedItem().toString());
                break;
            case R.id.sp_count_columns_matrix_a:
                columnsMatrixA = Integer.parseInt(spCountColumnsMatrixA.getSelectedItem().toString());
                break;
            case R.id.sp_count_columns_matrix_b:
                columnsMatrixB = Integer.parseInt(spCountColumnsMatrixB.getSelectedItem().toString());
                break;
        }

        if(!setMatrixBFromSaving)
            managerMatrix.generateMatrix(glMatrixA, TAG_ID_MATRIX_A, rowsMatrixA, columnsMatrixA, this);

        managerMatrix.generateMatrix(glMatrixB, TAG_ID_MATRIX_B, columnsMatrixA, columnsMatrixB, this);

        if ((bufferRowsMatrixA == rowsMatrixA && bufferColumnsMatrixA == columnsMatrixA) || (bufferRowsMatrixA == rowsMatrixA && bufferColumnsMatrixB == columnsMatrixB)) {
            if (setMatrixAFromSaving)
            {
                managerMatrix.fillUpMatrix(glMatrixA, TAG_ID_MATRIX_A, matrixA);
                setMatrixAFromSaving = false;
                matrixA = null;
            } else if (setMatrixBFromSaving) {
                managerMatrix.fillUpMatrix(glMatrixB, TAG_ID_MATRIX_B, matrixB);
                setMatrixBFromSaving = false;
                matrixB = null;
            }
            bufferRowsMatrixA = 0;
            bufferColumnsMatrixA = 0;
            bufferColumnsMatrixB = 0;
        }
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
                            .setAction(Action.MULTIPLICATION)
                            .setMatrixA(matrixA)
                            .setMatrixB(matrixB)
                            .setMatrixResult(matrixResult)
                            .commit();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        alertDialogSave.show();
    }

    @Override
    public boolean onLongClick(View view) {
        switch (view.getId()) {
            case R.id.tv_matrix_a:
                savingHelper.callAlertListSavingForMatrix(setMatrixA);
                break;
            case R.id.tv_matrix_b:
                savingHelper.callAlertListSavingForMatrix(setMatrixB);
                break;
        }
        return true;
    }
}
