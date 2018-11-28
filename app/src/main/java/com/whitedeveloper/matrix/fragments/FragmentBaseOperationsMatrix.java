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
import com.whitedeveloper.matrix.ListView.SavingHelper;
import com.whitedeveloper.matrix.instance.SavingInstance;
import com.whitedeveloper.matrix.operationModules.AdditionMatrix;

import static com.whitedeveloper.matrix.fragments.Tags.TAG_ID_MATRIX_A;
import static com.whitedeveloper.matrix.fragments.Tags.TAG_ID_MATRIX_B;

public class FragmentBaseOperationsMatrix extends Fragment implements
        AdapterView.OnItemSelectedListener,
        TextWatcher,
        OnPressSaveResualtListener, View.OnLongClickListener {

    private View view;

    private Spinner spCountRowsMatrices;
    private Spinner spCountColumnsMatrices;

    private GridLayout glMatrixA;
    private GridLayout glMatrixB;
    private GridLayout glMatrixResult;

    private int rowsMatrices;
    private int columnsMatrices;
    private int bufferRowsMatrices;
    private int bufferColumnsMatrices;

    private ManagerMatrix managerMatrix;
    private SavingHelper savingHelper;
    private RelativeLayout tvResult;

    private double[][] matrixA;
    private double[][] matrixB;
    private double[][] matrixResult;
    private SetMatrix setMatrixA;
    private SetMatrix setMatrixB;

    private boolean setMatrixAFromSaving = false;
    private boolean setMatrixBFromSaving = false;

    private Action action = Action.ADDITION;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_base_operation_matrix, container, false);
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

        final TextView tvMatrixA = view.findViewById(R.id.tv_matrix_a);
        final TextView tvMatrixB = view.findViewById(R.id.tv_matrix_b);
        tvMatrixA.setOnLongClickListener(this);
        tvMatrixB.setOnLongClickListener(this);

        spCountColumnsMatrices.setOnItemSelectedListener(this);
        spCountRowsMatrices.setOnItemSelectedListener(this);

        managerMatrix = new ManagerMatrix(getContext());
        savingHelper = new SavingHelper(getContext());


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
                matrixA = null;
                matrixB = null;
                removeResult();
            }
        });

        final Button btnChangeAction = view.findViewById(R.id.tv_action_symbol);
        btnChangeAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (action == Action.ADDITION) {
                    btnChangeAction.setText(R.string.subtraction_symbol);
                    action = Action.SUBTRACTION;
                } else if (action == Action.SUBTRACTION) {
                    btnChangeAction.setText(R.string.addition_symbol);
                    action = Action.ADDITION;
                }
            }
        });

        setMatrixA = new SetMatrix() {
            @Override
            public void setMatrix(double[][] matrix) {
                matrixA = matrix;
            }

            @Override
            public void setSizeMatrix(int countRows, int countColumns) {
                if (countRows == rowsMatrices && countColumns == columnsMatrices)
                    managerMatrix.fillUpMatrix(glMatrixA, TAG_ID_MATRIX_A, matrixA);
                else {
                    setDimensionsForSpinners(countRows, countColumns);
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
                if (countRows == rowsMatrices && countColumns == columnsMatrices)
                    managerMatrix.fillUpMatrix(glMatrixB, TAG_ID_MATRIX_B, matrixB);
                else {
                    setDimensionsForSpinners(countRows, countColumns);
                    setMatrixBFromSaving = true;
                }

            }
        };
    }

    private void setDimensionsForSpinners(int countRows, int countColumns) {
        bufferRowsMatrices = countRows;
        bufferColumnsMatrices = countColumns;

        for (int i = 0; i < spCountRowsMatrices.getCount(); i++)
            if (Integer.parseInt((String) spCountRowsMatrices.getItemAtPosition(i)) == countRows)
                spCountRowsMatrices.setSelection(i);

        for (int i = 0; i < spCountColumnsMatrices.getCount(); i++)
            if (Integer.parseInt((String) spCountColumnsMatrices.getItemAtPosition(i)) == countColumns)
                spCountColumnsMatrices.setSelection(i);

    }

    private void calculate() {
        if (managerMatrix.allIsFill(glMatrixA, TAG_ID_MATRIX_A, rowsMatrices, columnsMatrices) &&
                managerMatrix.allIsFill(glMatrixB, TAG_ID_MATRIX_B, rowsMatrices, columnsMatrices)) {
            matrixA = managerMatrix.readMatrix(glMatrixA, TAG_ID_MATRIX_A, rowsMatrices, columnsMatrices);
            matrixB = managerMatrix.readMatrix(glMatrixB, TAG_ID_MATRIX_B, rowsMatrices, columnsMatrices);

            AdditionMatrix additionMatrix = new AdditionMatrix(matrixA, action == Action.SUBTRACTION?AdditionMatrix.doNegative(matrixB):matrixB);
            matrixResult = additionMatrix.additionMatrix();


            showResult(matrixResult);

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

        switch (adapterView.getId()) {
            case R.id.sp_count_rows_matrix:
                rowsMatrices = Integer.parseInt(spCountRowsMatrices.getSelectedItem().toString());
                break;
            case R.id.sp_count_columns_matrix:
                columnsMatrices = Integer.parseInt(spCountColumnsMatrices.getSelectedItem().toString());
                break;
        }

        managerMatrix.generateMatrix(glMatrixA, TAG_ID_MATRIX_A, rowsMatrices, columnsMatrices, this);
        managerMatrix.generateMatrix(glMatrixB, TAG_ID_MATRIX_B, rowsMatrices, columnsMatrices, this);


        if (bufferRowsMatrices == rowsMatrices && bufferColumnsMatrices == columnsMatrices) {
            if (setMatrixAFromSaving) {
                managerMatrix.fillUpMatrix(glMatrixA, TAG_ID_MATRIX_A, matrixA);
                setMatrixAFromSaving = false;
                matrixA = null;
            } else if (setMatrixBFromSaving) {
                managerMatrix.fillUpMatrix(glMatrixB, TAG_ID_MATRIX_B, matrixB);
                setMatrixBFromSaving = false;
                matrixB = null;
            }

            bufferColumnsMatrices = 0;
            bufferRowsMatrices = 0;
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
        if (tvResult.getVisibility() == View.VISIBLE)
            removeResult();
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }

    @Override
    public void onPressSave() {
        if (tvResult.getVisibility() == View.INVISIBLE) {
            Toast.makeText(getContext(), R.string.text_calculate, Toast.LENGTH_SHORT).show();
            return;
        }

        AlertDialogSave alertDialogSave = new AlertDialogSave(getContext(), new AlertDialogSave.CallBackFromAlertDialogSave() {
            @Override
            public void callBack(String name) {
                try {
                    new SavingInstance(getContext())
                            .setNameSaving(name)
                            .setAction(action)
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