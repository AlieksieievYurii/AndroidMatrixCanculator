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
import com.whitedeveloper.matrix.operationModules.DeterminantMatrix;
import com.whitedeveloper.matrix.tags.Action;
import com.whitedeveloper.matrix.tags.TagKeys;
import org.json.JSONException;

import static com.whitedeveloper.matrix.tags.TagKeys.TAG_ID_MATRIX_A;


public class FragmentDeterminantMatrix extends Fragment implements AdapterView.OnItemSelectedListener, TextWatcher, OnPressSaveResultListener {
    private View view;

    private GridLayout glMatrix;

    private RelativeLayout rlResult;
    private TextView tvDet;

    private ManagerMatrix managerMatrix;
    private SavedStateInstance savedStateInstance;
    private double[][] matrix;
    private double determinant;

    private int dimensionMatrix;
    private SetMatrix setMatrix;
    private boolean setMatrixFromSaving = false;
    private Spinner spSizeMatrix;
    private boolean isCalculated = false;
    private boolean setSavedState = false;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_determinant_matrix, container, false);
        init();
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        try {
            savedStateInstance.load(TagKeys.KEY_SAVE_STATE_DETERMINANT);
            loadLastState();
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void loadLastState()
    {
        spSizeMatrix.setSelection(savedStateInstance.getSpDimensionMatrix());
        setSavedState = true;
    }

    private void init() {
        spSizeMatrix = view.findViewById(R.id.sp_dimension_matrix);
        spSizeMatrix.setOnItemSelectedListener(this);
        glMatrix = view.findViewById(R.id.gl_matrix_determinant);

        spSizeMatrix.setOnItemSelectedListener(this);

        rlResult = view.findViewById(R.id.rl_result);
        tvDet = view.findViewById(R.id.tv_determinant);

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
                FragmentDeterminantMatrix.this.matrix = matrix;
            }

            @Override
            public void setSizeMatrix(int countRows, int countColumns) {
                if (countColumns != countRows) {
                    Toast.makeText(getContext(), R.string.rows_not_equals_columns, Toast.LENGTH_SHORT).show();
                    return;
                }

                if (countColumns == dimensionMatrix) {
                    managerMatrix.fillUpMatrix(glMatrix, TAG_ID_MATRIX_A, matrix);
                } else {
                    setDimensionForSpinnerBar(countRows);
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
    private void setDimensionForSpinnerBar(int dim) {
        for (int i = 0; i < spSizeMatrix.getCount(); i++)
            if (spSizeMatrix.getItemAtPosition(i).equals(String.format("%dx%d", dim, dim)))
                spSizeMatrix.setSelection(i);
    }

    private void calculate() {
        if (managerMatrix.allIsFill(glMatrix, TAG_ID_MATRIX_A, dimensionMatrix, dimensionMatrix)) {
            matrix = managerMatrix.readMatrix(glMatrix, TAG_ID_MATRIX_A, dimensionMatrix, dimensionMatrix);

            DeterminantMatrix determinantMatrix = new DeterminantMatrix(matrix);
            determinant = determinantMatrix.countDeterminant();
            showResult(determinant);

        } else
            Toast.makeText(getContext(), R.string.text_warming_fill_up_matrix, Toast.LENGTH_SHORT).show();
    }

    private void showResult(double det) {
        HiddenKeyboard.hideKeyboardFrom(getContext(), view);
        tvDet.setVisibility(View.VISIBLE);
        tvDet.setText(getResources().getString(R.string.text_determinant).concat(String.valueOf(det)));
        rlResult.setVisibility(View.VISIBLE);
        isCalculated = true;
    }

    private void removeResult() {
        rlResult.setVisibility(View.INVISIBLE);
        tvDet.setVisibility(View.INVISIBLE);
        isCalculated = false;
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        removeResult();
        dimensionMatrix = i + 2;
        HiddenKeyboard.hideKeyboardFrom(getContext(), view);

        managerMatrix.generateMatrix(glMatrix, TagKeys.TAG_ID_MATRIX_A, dimensionMatrix, dimensionMatrix, this);

        if (setMatrixFromSaving) {
            managerMatrix.fillUpMatrix(glMatrix, TAG_ID_MATRIX_A, matrix);
            setMatrixFromSaving = false;
        }else if(setSavedState)
        {
            try {
                managerMatrix.fillUpMatrixByJson(glMatrix,TAG_ID_MATRIX_A,savedStateInstance.getJsonObjectMatrixA(),dimensionMatrix,dimensionMatrix);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            if(savedStateInstance.isCalculated())
                calculate();
            setSavedState = false;
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
                            .setAction(Action.DETERMINATION)
                            .setMatrixA(matrix)
                            .setDeterminant(determinant)
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

            savingStateInstance.setSpDimensionMatrix(spSizeMatrix.getSelectedItemPosition())
                    .setJsonObjectMatrixA(managerMatrix.getJsonMatrix(glMatrix,TAG_ID_MATRIX_A,dimensionMatrix,dimensionMatrix))
                    .setAction(Action.DETERMINATION)
                    .setCalculated(isCalculated);

            savingStateInstance.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
