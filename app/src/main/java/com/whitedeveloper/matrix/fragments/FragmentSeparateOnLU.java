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
import com.whitedeveloper.matrix.HiddenKeyboard;
import com.whitedeveloper.matrix.ListView.SavingHelper;
import com.whitedeveloper.matrix.ManagerMatrix;
import com.whitedeveloper.matrix.R;
import com.whitedeveloper.matrix.activities.OnPressSaveResultListener;
import com.whitedeveloper.matrix.alerts.AlertDialogSave;
import com.whitedeveloper.matrix.instance.SavedStateInstance;
import com.whitedeveloper.matrix.instance.SavingInstance;
import com.whitedeveloper.matrix.instance.SavingStateInstance;
import com.whitedeveloper.matrix.operationModules.MatrixOnLU;
import com.whitedeveloper.matrix.tags.Action;
import com.whitedeveloper.matrix.tags.TagKeys;
import org.json.JSONException;

import static com.whitedeveloper.matrix.tags.TagKeys.TAG_ID_MATRIX_A;

public class FragmentSeparateOnLU extends Fragment implements AdapterView.OnItemSelectedListener, TextWatcher, OnPressSaveResultListener {

    private View view;

    private GridLayout glMatrix;
    private GridLayout glMatrixL;
    private GridLayout glMatrixU;

    private LinearLayout lnResult;
    private TextView tvDeterminantZero;

    private ManagerMatrix managerMatrix;
    private SavedStateInstance savedStateInstance;

    private double[][] matrix;
    private double[][] matrixL;
    private double[][] matrixU;

    private int dimensionMatrix;

    private SetMatrix setMatrix;
    private Spinner spDimensionMatrix;
    private boolean setMatrixFromSaving = false;
    private boolean isCalculated = false;
    private boolean setSavedState = false;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_separation_on_l_u, container, false);
        init();
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        try {
            savedStateInstance.load(TagKeys.KEY_SAVE_STATE_LU);
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
        glMatrixL = view.findViewById(R.id.gl_matrix_l_result);
        glMatrixU = view.findViewById(R.id.gl_matrix_u_result);

        lnResult = view.findViewById(R.id.ln_result);
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
                FragmentSeparateOnLU.this.matrix = matrix;
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
        MatrixOnLU matrixOnLU = new MatrixOnLU(matrix);

        if(!matrixOnLU.calculate()) {
            tvDeterminantZero.setVisibility(View.VISIBLE);
            return;
        }

        matrixL = matrixOnLU.getMatrixL();
        matrixU = matrixOnLU.getMatrixU();

        showResult();

}

    private void showResult() {
        HiddenKeyboard.hideKeyboardFrom(getContext(), view);
        lnResult.setVisibility(View.VISIBLE);
        managerMatrix.generateAndFillUpMatrixResult(glMatrixL, matrixL);
        managerMatrix.generateAndFillUpMatrixResult(glMatrixU,matrixU);
        isCalculated = true;
    }

    private void removeResult() {
        glMatrixU.removeAllViews();
        glMatrixL.removeAllViews();
        lnResult.setVisibility(View.INVISIBLE);

        if(tvDeterminantZero.getVisibility() == View.VISIBLE)
            tvDeterminantZero.setVisibility(View.GONE);

        isCalculated = false;
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        if (lnResult.getVisibility() == View.VISIBLE || tvDeterminantZero.getVisibility() == View.VISIBLE)
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
            } catch (Exception e) {
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
        if (lnResult.getVisibility() == View.INVISIBLE) {
            Toast.makeText(getContext(), R.string.text_calculate, Toast.LENGTH_SHORT).show();
            return;
        }

        AlertDialogSave alertDialogSave = new AlertDialogSave(getContext(), new AlertDialogSave.CallBackFromAlertDialogSave() {
            @Override
            public void callBack(String name) {
                try {
                    new SavingInstance(getContext())
                            .setNameSaving(name)
                            .setAction(Action.SEPARATION)
                            .setMatrixA(matrix)
                            .setMatrixL(matrixL)
                            .setMatrixU(matrixU)
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
                    .setAction(Action.SEPARATION)
                    .setCalculated(isCalculated);

            savingStateInstance.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
