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
import com.whitedeveloper.matrix.operationModules.AdditionMatrix;
import com.whitedeveloper.matrix.tags.Action;
import com.whitedeveloper.matrix.tags.TagKeys;
import org.json.JSONException;

import static com.whitedeveloper.matrix.tags.TagKeys.TAG_ID_MATRIX_A;
import static com.whitedeveloper.matrix.tags.TagKeys.TAG_ID_MATRIX_B;

public class FragmentBaseOperationsMatrix extends Fragment implements
        AdapterView.OnItemSelectedListener,
        TextWatcher,
        OnPressSaveResultListener {

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
    private SavedStateInstance savedStateInstance;
    private SavingHelper savingHelper;
    private RelativeLayout tvResult;

    private double[][] matrixA;
    private double[][] matrixB;
    private double[][] matrixResult;
    private SetMatrix setMatrixA;
    private SetMatrix setMatrixB;

    private boolean setMatrixAFromSaving = false;
    private boolean setMatrixBFromSaving = false;
    private boolean setSavedState = false;
    private boolean isCalculated = false;

    private Action action = Action.ADDITION;
    private Button btnChangeAction;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_base_operation_matrix, container, false);
        init();

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        try {
            savedStateInstance.load(TagKeys.KEY_SAVE_STATE_BASIC_OPERATIONS);
            loadLastState();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void loadLastState() {
        spCountRowsMatrices.setSelection(savedStateInstance.getSpRowsMatrixAPosition());
        spCountColumnsMatrices.setSelection(savedStateInstance.getSpColumnsMatrixAPosition());

        bufferRowsMatrices = Integer.parseInt((String)spCountRowsMatrices.getSelectedItem());
        bufferColumnsMatrices = Integer.parseInt((String)spCountColumnsMatrices.getSelectedItem());

        action = savedStateInstance.getAction();
        setAction(action);

        setSavedState = true;
    }

    private void init() {
        spCountRowsMatrices = view.findViewById(R.id.sp_count_rows_matrix);
        spCountColumnsMatrices = view.findViewById(R.id.sp_count_columns_matrix);

        glMatrixA = view.findViewById(R.id.gl_matrix_a);
        glMatrixB = view.findViewById(R.id.gl_matrix_b);
        glMatrixResult = view.findViewById(R.id.gl_matrix_result);
        tvResult = view.findViewById(R.id.rl_result);

        final Button btnInsertMatrixA = view.findViewById(R.id.btn_insert_matrix_a);
        btnInsertMatrixA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                savingHelper.callAlertListSavingForMatrix(setMatrixA);
            }
        });

        final Button btnInsertMatrixB = view.findViewById(R.id.btn_insert_matrix_b);
        btnInsertMatrixB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                savingHelper.callAlertListSavingForMatrix(setMatrixB);
            }
        });

        spCountColumnsMatrices.setOnItemSelectedListener(this);
        spCountRowsMatrices.setOnItemSelectedListener(this);

        managerMatrix = new ManagerMatrix(getContext());
        savingHelper = new SavingHelper(getContext());
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
                managerMatrix.clearMatrix(glMatrixA, TAG_ID_MATRIX_A, rowsMatrices, columnsMatrices);
                managerMatrix.clearMatrix(glMatrixB, TAG_ID_MATRIX_B, rowsMatrices, columnsMatrices);
                matrixA = null;
                matrixB = null;
                removeResult();
            }
        });

        btnChangeAction = view.findViewById(R.id.tv_action_symbol);
        btnChangeAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (action == Action.ADDITION)
                    action = Action.SUBTRACTION;
                else
                    action = Action.ADDITION;

                setAction(action);
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

    private void setAction(Action action) {
        if (action == Action.ADDITION)
            btnChangeAction.setText(R.string.addition_symbol);
        else if (action == Action.SUBTRACTION)
            btnChangeAction.setText(R.string.subtraction_symbol);
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

            AdditionMatrix additionMatrix = new AdditionMatrix(matrixA, action == Action.SUBTRACTION ? AdditionMatrix.doNegative(matrixB) : matrixB);
            matrixResult = additionMatrix.additionMatrix();

            showResult(matrixResult);

        } else
            Toast.makeText(getContext(), R.string.text_warming_fill_up_matrix, Toast.LENGTH_SHORT).show();
    }

    private void showResult(double[][] matrixResult) {
        HiddenKeyboard.hideKeyboardFrom(getContext(), view);
        tvResult.setVisibility(View.VISIBLE);
        managerMatrix.generateAndFillUpMatrixResult(glMatrixResult, matrixResult);
        isCalculated = true;
    }

    private void removeResult() {
        glMatrixResult.removeAllViews();
        tvResult.setVisibility(View.INVISIBLE);
        isCalculated = false;
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        removeResult();

        HiddenKeyboard.hideKeyboardFrom(getContext(), view);

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
                matrixA = null;
                setMatrixAFromSaving = false;
            } else if (setMatrixBFromSaving) {
                managerMatrix.fillUpMatrix(glMatrixB, TAG_ID_MATRIX_B, matrixB);
                setMatrixBFromSaving = false;
                matrixB = null;
            } else if (setSavedState) {
                try {
                    managerMatrix.fillUpMatrixByJson(glMatrixA, TAG_ID_MATRIX_A, savedStateInstance.getJsonObjectMatrixA(), rowsMatrices, columnsMatrices);
                    managerMatrix.fillUpMatrixByJson(glMatrixB, TAG_ID_MATRIX_B, savedStateInstance.getJsonObjectMatrixB(), rowsMatrices, columnsMatrices);

                    if (savedStateInstance.isCalculated())
                        calculate();

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                setSavedState = false;
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
    public void onPause() {
        super.onPause();

        try {
            SavingStateInstance savingStateInstance = new SavingStateInstance(getContext())
                    .setSpColumnsMatrixAPosition(spCountColumnsMatrices.getSelectedItemPosition())
                    .setSpRowsMatrixAPosition(spCountRowsMatrices.getSelectedItemPosition())
                    .setAction(action)
                    .setJsonObjectMatrixA(managerMatrix.getJsonMatrix(glMatrixA, TAG_ID_MATRIX_A, rowsMatrices, columnsMatrices))
                    .setJsonObjectMatrixB(managerMatrix.getJsonMatrix(glMatrixB, TAG_ID_MATRIX_B, rowsMatrices, columnsMatrices))
                    .setCalculated(isCalculated);

            savingStateInstance.commit();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
