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
import com.whitedeveloper.matrix.HiddenKeyboard;
import com.whitedeveloper.matrix.ListView.SavingHelper;
import com.whitedeveloper.matrix.ManagerMatrix;
import com.whitedeveloper.matrix.R;
import com.whitedeveloper.matrix.activities.OnPressSaveResultListener;
import com.whitedeveloper.matrix.alerts.AlertDialogSave;
import com.whitedeveloper.matrix.instance.SavedStateInstance;
import com.whitedeveloper.matrix.instance.SavingInstance;
import com.whitedeveloper.matrix.instance.SavingStateInstance;
import com.whitedeveloper.matrix.operationModules.MultiplicationMatrixByNumber;
import com.whitedeveloper.matrix.tags.Action;
import com.whitedeveloper.matrix.tags.TagKeys;
import org.json.JSONException;

import static com.whitedeveloper.matrix.tags.TagKeys.TAG_ID_MATRIX_A;

public class FragmentMultiplicationMatrixByNumber extends Fragment implements
        AdapterView.OnItemSelectedListener,
        TextWatcher,
        OnPressSaveResultListener, View.OnLongClickListener {
    private View view;

    private Spinner spCountRowsMatrix;
    private Spinner spCountColumnsMatrix;
    /*
     *
     * Count rows of matrix B always equals count columns of matrix A
     *
     * */

    private GridLayout glMatrix;
    private GridLayout glMatrixResult;
    private EditText edtNumberK;

    private RelativeLayout rlResult;
    private SavingHelper savingHelper;
    private ManagerMatrix managerMatrix;
    private SavedStateInstance savedStateInstance;

    private int rowsMatrix;
    private int columnsMatrix;
    private int bufferRowsMatrix;
    private int bufferColumnsMatrix;

    private boolean isCalculated = false;
    private boolean setSavedState = false;

    private double[][] matrix;
    private double[][] matrixResult;
    private SetMatrix setMatrix;

    private boolean setMatrixFromSaving = false;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_multiplication_matrix_by_number, container, false);
        init();
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        try {
            savedStateInstance.load(TagKeys.KEY_SAVE_STATE_MULTIPLICATION_BY_NUMBER);
            loadLastState();
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void loadLastState() {
        spCountRowsMatrix.setSelection(savedStateInstance.getSpRowsMatrixAPosition());
        spCountColumnsMatrix.setSelection(savedStateInstance.getSpColumnsMatrixAPosition());

        edtNumberK.setText(String.valueOf(savedStateInstance.getK()));

        bufferRowsMatrix = Integer.parseInt((String) spCountRowsMatrix.getSelectedItem());
        bufferColumnsMatrix = Integer.parseInt((String) spCountColumnsMatrix.getSelectedItem());

        setSavedState = true;
    }

    private void init() {
        spCountColumnsMatrix = view.findViewById(R.id.sp_count_columns_matrix);
        spCountRowsMatrix = view.findViewById(R.id.sp_count_rows_matrix);

        glMatrix = view.findViewById(R.id.gl_matrix);
        glMatrixResult = view.findViewById(R.id.gl_matrix_result);
        edtNumberK = view.findViewById(R.id.edt_number);
        edtNumberK.addTextChangedListener(this);

        savingHelper = new SavingHelper(getContext());

        final TextView tvMatrixA = view.findViewById(R.id.tv_matrix);
        tvMatrixA.setOnLongClickListener(this);

        rlResult = view.findViewById(R.id.rl_result);

        managerMatrix = new ManagerMatrix(getContext());
        savedStateInstance = new SavedStateInstance(getContext());

        spCountRowsMatrix.setOnItemSelectedListener(this);
        spCountColumnsMatrix.setOnItemSelectedListener(this);

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
                managerMatrix.clearMatrix(glMatrix, TAG_ID_MATRIX_A, rowsMatrix, columnsMatrix);
                edtNumberK.setText("");
                removeResult();
            }
        });

        setMatrix = new SetMatrix() {
            @Override
            public void setMatrix(double[][] matrix) {
                FragmentMultiplicationMatrixByNumber.this.matrix = matrix;
            }

            @Override
            public void setSizeMatrix(int countRows, int countColumns) {
                if (countRows == rowsMatrix && countColumns == columnsMatrix)
                    managerMatrix.fillUpMatrix(glMatrix, TAG_ID_MATRIX_A, matrix);
                else {
                    setColumnsForSpinner(countColumns);
                    setRowsMatrix(countRows);
                    setMatrixFromSaving = true;
                }
            }
        };

    }

    private void setRowsMatrix(int countRows) {
        bufferRowsMatrix = countRows;
        for (int i = 0; i < spCountRowsMatrix.getCount(); i++)
            if (Integer.parseInt((String) spCountRowsMatrix.getItemAtPosition(i)) == countRows)
                spCountRowsMatrix.setSelection(i);
    }

    private void setColumnsForSpinner(int countColumns) {
        bufferColumnsMatrix = countColumns;
        for (int i = 0; i < spCountColumnsMatrix.getCount(); i++)
            if (Integer.parseInt((String) spCountColumnsMatrix.getItemAtPosition(i)) == countColumns)
                spCountColumnsMatrix.setSelection(i);
    }

    private void calculate() {
        if (!managerMatrix.allIsFill(glMatrix, TAG_ID_MATRIX_A, rowsMatrix, columnsMatrix)) {
            Toast.makeText(getContext(), R.string.text_warming_fill_up_matrix, Toast.LENGTH_SHORT).show();
            return;
        }

        if (edtNumberK.getText().toString().equals("")) {
            Toast.makeText(getContext(), R.string.text_warming_enter_number_k, Toast.LENGTH_SHORT).show();
            return;
        }


        matrix = managerMatrix.readMatrix(glMatrix, TAG_ID_MATRIX_A, rowsMatrix, columnsMatrix);
        MultiplicationMatrixByNumber multiplicationMatrixByNumber = new MultiplicationMatrixByNumber(matrix);

        matrixResult = multiplicationMatrixByNumber.calculate(Double.parseDouble(edtNumberK.getText().toString()));
        showResult(matrixResult);
    }

    private void showResult(double[][] matrixResult) {
        HiddenKeyboard.hideKeyboardFrom(getContext(), view);
        rlResult.setVisibility(View.VISIBLE);
        managerMatrix.generateAndFillUpMatrixResult(glMatrixResult, matrixResult);
        isCalculated = true;
    }

    private void removeResult() {
        glMatrixResult.removeAllViews();
        rlResult.setVisibility(View.INVISIBLE);
        isCalculated = false;
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

        removeResult();
        HiddenKeyboard.hideKeyboardFrom(getContext(), view);


        switch (adapterView.getId()) {
            case R.id.sp_count_rows_matrix:
                rowsMatrix = Integer.parseInt(spCountRowsMatrix.getSelectedItem().toString());
                break;
            case R.id.sp_count_columns_matrix:
                columnsMatrix = Integer.parseInt(spCountColumnsMatrix.getSelectedItem().toString());
                break;
        }

        if (!setMatrixFromSaving && !setSavedState)
            managerMatrix.generateMatrix(glMatrix, TAG_ID_MATRIX_A, rowsMatrix, columnsMatrix, this);


        if (bufferRowsMatrix == rowsMatrix && bufferColumnsMatrix == columnsMatrix) {
            managerMatrix.generateMatrix(glMatrix, TAG_ID_MATRIX_A, rowsMatrix, columnsMatrix, this);

            if (setMatrixFromSaving) {

                managerMatrix.fillUpMatrix(glMatrix, TAG_ID_MATRIX_A, matrix);
                setMatrixFromSaving = false;
                matrix = null;
                clearBuffers();
            } else if (setSavedState) {
                try {
                    managerMatrix.fillUpMatrixByJson(glMatrix, TAG_ID_MATRIX_A, savedStateInstance.getJsonObjectMatrixA(), rowsMatrix, columnsMatrix);

                } catch (Exception e) {
                    e.printStackTrace();
                }

                if (savedStateInstance.isCalculated())
                    calculate();

                clearBuffers();
                setSavedState = false;
            }

        }
    }

    private void clearBuffers() {
        bufferColumnsMatrix = 0;
        bufferRowsMatrix = 0;
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
                            .setAction(Action.MULTIPLICATION_BY_NUMBER)
                            .setMatrixA(matrix)
                            .setMatrixResult(matrixResult)
                            .setNumberK(Double.parseDouble(edtNumberK.getText().toString()))
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
            case R.id.tv_matrix:
                savingHelper.callAlertListSavingForMatrix(setMatrix);
                break;
        }
        return true;
    }

    @Override
    public void onPause() {
        super.onPause();

        try {
            SavingStateInstance savingStateInstance = new SavingStateInstance(getContext())
                    .setSpRowsMatrixAPosition(spCountRowsMatrix.getSelectedItemPosition())
                    .setSpColumnsMatrixAPosition(spCountColumnsMatrix.getSelectedItemPosition())
                    .setJsonObjectMatrixA(managerMatrix.getJsonMatrix(glMatrix, TAG_ID_MATRIX_A, rowsMatrix, columnsMatrix))
                    .setAction(Action.MULTIPLICATION_BY_NUMBER)
                    .setK(Integer.parseInt(edtNumberK.getText().toString().equals("") ? "0" : edtNumberK.getText().toString()))
                    .setCalculated(isCalculated);

            savingStateInstance.commit();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
