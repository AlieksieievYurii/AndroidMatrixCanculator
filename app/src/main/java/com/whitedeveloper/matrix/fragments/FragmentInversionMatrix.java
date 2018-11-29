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
import com.whitedeveloper.matrix.instance.SavingInstance;
import com.whitedeveloper.matrix.operationModules.InversionMatrix;

import static com.whitedeveloper.matrix.fragments.Tags.TAG_ID_MATRIX_A;

public class FragmentInversionMatrix extends Fragment implements AdapterView.OnItemSelectedListener, TextWatcher, OnPressSaveResualtListener {
    private View view;

    private GridLayout glMatrix;
    private GridLayout glResult;

    private RelativeLayout rlResult;

    private ManagerMatrix managerMatrix;
    private double[][] matrix;
    private double[][] matrixResult;

    private int dimensionMatrix;

    private SetMatrix setMatrix;
    private Spinner spDimensionMatrix;
    private boolean setMatrixFromSaving = false;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_inversion_matrix, container, false);
        init();
        return view;
    }

    private void init() {
        spDimensionMatrix = view.findViewById(R.id.sp_dimension_matrix);
        spDimensionMatrix.setOnItemSelectedListener(this);

        glMatrix = view.findViewById(R.id.gl_matrix_inversion);
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
            public void setSizeMatrix(int countRows, int countColumns)
            {
                if(countColumns != countRows)
                {
                    Toast.makeText(getContext(),R.string.rows_not_equals_columns,Toast.LENGTH_SHORT).show();
                    return;
                }

                if(countColumns == dimensionMatrix)
                    managerMatrix.fillUpMatrix(glMatrix,TAG_ID_MATRIX_A,matrix);
                else
                {
                    setDimensionForSpinner(countColumns);
                    setMatrixFromSaving = true;
                }
            }
        };

        final TextView tvMatrix = view.findViewById(R.id.tv_matrix);
        tvMatrix.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view)
            {
                SavingHelper savingHelper = new SavingHelper(getContext());
                savingHelper.callAlertListSavingForMatrix(setMatrix);
                return true;
            }
        });
    }

    @SuppressLint("DefaultLocale")
    private void setDimensionForSpinner(int dim)
    {
        for(int i = 0; i < spDimensionMatrix.getCount(); i++)
            if(spDimensionMatrix.getItemAtPosition(i).equals(String.format("%dx%d",dim,dim)))
                spDimensionMatrix.setSelection(i);
    }

    private void calculate() {
        if (managerMatrix.allIsFill(glMatrix, TAG_ID_MATRIX_A, dimensionMatrix, dimensionMatrix))
        {
            matrix = managerMatrix.readMatrix(glMatrix, Tags.TAG_ID_MATRIX_A, dimensionMatrix, dimensionMatrix);
            InversionMatrix inversionMatrix = new InversionMatrix(matrix);
            matrixResult = inversionMatrix.inversionMatrix();
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
        if (rlResult.getVisibility() == View.VISIBLE)
            removeResult();
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        dimensionMatrix = i + 2;
        HidenKeyboard.hideKeyboardFrom(getContext(), view);

        managerMatrix.generateMatrix(glMatrix, Tags.TAG_ID_MATRIX_A, dimensionMatrix, dimensionMatrix, this);

        if(setMatrixFromSaving)
        {
            managerMatrix.fillUpMatrix(glMatrix,TAG_ID_MATRIX_A,matrix);
            setMatrixFromSaving = false;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public void onPressSave()
    {
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
}
