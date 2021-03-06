package com.whitedeveloper.matrix.fragments;

import android.annotation.SuppressLint;
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
import com.whitedeveloper.matrix.operationModules.InversionMatrix;
import com.whitedeveloper.matrix.tags.Action;
import com.whitedeveloper.matrix.tags.TagKeys;
import org.json.JSONException;

import static com.whitedeveloper.matrix.tags.TagKeys.TAG_ID_MATRIX_A;

public class FragmentInversionMatrix extends Fragment implements AdapterView.OnItemSelectedListener, TextWatcher, OnPressSaveResultListener {
    private View view;

    private GridLayout glMatrix;
    private GridLayout glResult;

    private RelativeLayout rlResult;
    private TextView tvDeterminantZero;

    private ManagerMatrix managerMatrix;
    private SavedStateInstance savedStateInstance;

    private double[][] matrix;
    private double[][] matrixResult;

    private int dimensionMatrix;

    private SetMatrix setMatrix;
    private Spinner spDimensionMatrix;
    private boolean setMatrixFromSaving = false;
    private boolean isCalculated = false;
    private boolean setSavedState = false;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_inversion_matrix, container, false);
        init();
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        try {
            savedStateInstance.load(TagKeys.KEY_SAVE_STATE_INVERSE);
            loadLastState();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void loadLastState() {
        spDimensionMatrix.setSelection(savedStateInstance.getSpDimensionMatrix());
        setSavedState = true;
    }

    private void init() {
        spDimensionMatrix = view.findViewById(R.id.sp_dimension_matrix);
        spDimensionMatrix.setOnItemSelectedListener(this);

        glMatrix = view.findViewById(R.id.gl_matrix_inversion);
        glResult = view.findViewById(R.id.gl_matrix_result);

        rlResult = view.findViewById(R.id.rl_result);
        tvDeterminantZero = view.findViewById(R.id.tv_determinant_zero);

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
                managerMatrix.clearMatrix(glMatrix, TAG_ID_MATRIX_A, dimensionMatrix, dimensionMatrix);
                removeResult();
            }
        });
        setMatrix = new SetMatrix() {
            @Override
            public void setMatrix(double[][] matrix) {
                FragmentInversionMatrix.this.matrix = matrix;
            }

            @Override
            public void setSizeMatrix(int countRows, int countColumns) {
                if (countColumns != countRows) {
                    Toast.makeText(getContext(), R.string.rows_not_equals_columns, Toast.LENGTH_SHORT).show();
                    return;
                }

                if (countColumns == dimensionMatrix)
                    managerMatrix.fillUpMatrix(glMatrix, TAG_ID_MATRIX_A, matrix);
                else {
                    setDimensionForSpinner(countColumns);
                    setMatrixFromSaving = true;
                }
            }
        };

        final Button btnInsertMatrix = view.findViewById(R.id.btn_insert_matrix);
        btnInsertMatrix.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final SavingHelper savingHelper = new SavingHelper(getContext());
                savingHelper.callAlertListSavingForMatrix(setMatrix);
            }
        });
    }

    @SuppressLint("DefaultLocale")
    private void setDimensionForSpinner(int dim) {
        for (int i = 0; i < spDimensionMatrix.getCount(); i++)
            if (spDimensionMatrix.getItemAtPosition(i).equals(String.format("%dx%d", dim, dim)))
                spDimensionMatrix.setSelection(i);
    }

    private void calculate() {
        if (!managerMatrix.allIsFill(glMatrix, TAG_ID_MATRIX_A, dimensionMatrix, dimensionMatrix)) {
            Toast.makeText(getContext(), R.string.text_warming_fill_up_matrix, Toast.LENGTH_SHORT).show();
            return;
        }
        matrix = managerMatrix.readMatrix(glMatrix, TagKeys.TAG_ID_MATRIX_A, dimensionMatrix, dimensionMatrix);
        InversionMatrix inversionMatrix = new InversionMatrix(matrix);
        if (inversionMatrix.canBeInverted(matrix)) {
            matrixResult = inversionMatrix.inversionMatrix();
            showResult(matrixResult);
        }else
            tvDeterminantZero.setVisibility(View.VISIBLE);

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
        tvDeterminantZero.setVisibility(View.GONE);
        isCalculated = false;
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        if (rlResult.getVisibility() == View.VISIBLE || tvDeterminantZero.getVisibility() == View.VISIBLE)
            removeResult();
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        dimensionMatrix = i + 2;
        HiddenKeyboard.hideKeyboardFrom(getContext(), view);

        managerMatrix.generateMatrix(glMatrix, TagKeys.TAG_ID_MATRIX_A, dimensionMatrix, dimensionMatrix, this);

        if (setMatrixFromSaving) {
            managerMatrix.fillUpMatrix(glMatrix, TAG_ID_MATRIX_A, matrix);
            setMatrixFromSaving = false;
        } else if (setSavedState) {
            try {
                managerMatrix.fillUpMatrixByJson(glMatrix, TAG_ID_MATRIX_A, savedStateInstance.getJsonObjectMatrixA(), dimensionMatrix, dimensionMatrix);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            if (savedStateInstance.isCalculated())
                calculate();

            setSavedState = false;
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
                            .setAction(Action.INVERSION)
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

            savingStateInstance.setSpDimensionMatrix(spDimensionMatrix.getSelectedItemPosition())
                    .setJsonObjectMatrixA(managerMatrix.getJsonMatrix(glMatrix, TAG_ID_MATRIX_A, dimensionMatrix, dimensionMatrix))
                    .setAction(Action.INVERSION)
                    .setCalculated(isCalculated);

            savingStateInstance.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
