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
import com.whitedeveloper.matrix.activities.OnPressSaveResultListener;
import com.whitedeveloper.matrix.alerts.AlertDialogSave;
import com.whitedeveloper.matrix.instance.SavedStateInstance;
import com.whitedeveloper.matrix.instance.SavingInstance;
import com.whitedeveloper.matrix.instance.SavingStateInstance;
import com.whitedeveloper.matrix.operationModules.TransposeMatrix;
import com.whitedeveloper.matrix.tags.Action;
import org.json.JSONException;

import static com.whitedeveloper.matrix.tags.TagKeys.*;

public class FragmentTransposeMatrix extends Fragment implements AdapterView.OnItemSelectedListener, TextWatcher, OnPressSaveResultListener {
    private View view;

    private Spinner spRowsMatrix;
    private Spinner spColumnMatrix;

    private GridLayout glMatrix;
    private GridLayout glResult;

    private RelativeLayout rlResult;

    private ManagerMatrix managerMatrix;
    private SavedStateInstance savedStateInstance;
    private int numberOfRows;
    private int numberOfColumns;

    private int bufferCountRows;
    private int bufferCountColumns;

    private double[][] matrix;
    private double[][] matrixResult;

    private SetMatrix setMatrix;
    private boolean setMatrixFromSaving = false;
    private boolean isCalculated = false;
    private boolean setSavedState = false;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_transpose_matrix, container, false);
        init();
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        try {
            savedStateInstance.load(KEY_SAVE_STATE_TRANSPOSE);
            loadLastState();
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void loadLastState() {
        spRowsMatrix.setSelection(savedStateInstance.getSpRowsMatrixAPosition());
        spColumnMatrix.setSelection(savedStateInstance.getSpColumnsMatrixAPosition());
        bufferCountRows = Integer.parseInt((String) spRowsMatrix.getSelectedItem());
        bufferCountColumns = Integer.parseInt((String) spColumnMatrix.getSelectedItem());

        setSavedState = true;
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
        savedStateInstance = new SavedStateInstance(getContext());

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

        setMatrix = new SetMatrix() {
            @Override
            public void setMatrix(double[][] matrix) {
                FragmentTransposeMatrix.this.matrix = matrix;
            }

            @Override
            public void setSizeMatrix(int countRows, int countColumns) {
                if (countColumns == numberOfColumns && countRows == numberOfRows)
                    managerMatrix.fillUpMatrix(glMatrix, TAG_ID_MATRIX_A, matrix);
                else {
                    setDimensionForSpinner(countRows, countColumns);
                    setMatrixFromSaving = true;
                }

            }
        };
        final TextView tvMatrix = view.findViewById(R.id.tv_matrix);
        tvMatrix.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                SavingHelper savingHelper = new SavingHelper(getContext());
                savingHelper.callAlertListSavingForMatrix(setMatrix);
                return true;
            }
        });
    }

    private void setDimensionForSpinner(int countRows, int countColumns) {
        bufferCountColumns = countColumns;
        bufferCountRows = countRows;

        for (int i = 0; i < spRowsMatrix.getCount(); i++)
            if (Integer.parseInt((String) spRowsMatrix.getItemAtPosition(i)) == countRows)
                spRowsMatrix.setSelection(i);

        for (int i = 0; i < spColumnMatrix.getCount(); i++)
            if (Integer.parseInt((String) spColumnMatrix.getItemAtPosition(i)) == countColumns)
                spColumnMatrix.setSelection(i);
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
        HiddenKeyboard.hideKeyboardFrom(getContext(), view);
        rlResult.setVisibility(View.VISIBLE);
        managerMatrix.generateAndFillUpMatrixResult(glResult, matrixResult);
        isCalculated = true;
    }

    private void removeResult() {
        glResult.removeAllViews();
        rlResult.setVisibility(View.INVISIBLE);
        isCalculated = false;
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
        HiddenKeyboard.hideKeyboardFrom(getContext(), view);

        switch (adapterView.getId()) {
            case R.id.sp_count_rows_matrix:
                numberOfRows = Integer.parseInt(spRowsMatrix.getSelectedItem().toString());
                break;
            case R.id.sp_count_columns_matrix:
                numberOfColumns = Integer.parseInt(spColumnMatrix.getSelectedItem().toString());
                break;
        }

        managerMatrix.generateMatrix(glMatrix, TAG_ID_MATRIX_A, numberOfRows, numberOfColumns, this);

        if (bufferCountRows == numberOfRows && bufferCountColumns == numberOfColumns) {
            if (setMatrixFromSaving) {
                managerMatrix.fillUpMatrix(glMatrix, TAG_ID_MATRIX_A, matrix);
                setMatrixFromSaving = false;
            }else if(setSavedState)
            {
                try {
                    managerMatrix.fillUpMatrixByJson(glMatrix,TAG_ID_MATRIX_A,savedStateInstance.getJsonObjectMatrixA(),numberOfRows,numberOfColumns);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                if(savedStateInstance.isCalculated())
                    calculate();
            }
        }
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

    @Override
    public void onPause() {
        super.onPause();

        try {
            SavingStateInstance savingStateInstance = new SavingStateInstance(getContext());

            savingStateInstance.setSpRowsMatrixAPosition(spRowsMatrix.getSelectedItemPosition())
                    .setSpColumnsMatrixAPosition(spColumnMatrix.getSelectedItemPosition())
                    .setJsonObjectMatrixA(managerMatrix.getJsonMatrix(glMatrix, TAG_ID_MATRIX_A, numberOfRows, numberOfColumns))
                    .setAction(Action.TRANSPOSING)
                    .setCalculated(isCalculated);
            savingStateInstance.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
