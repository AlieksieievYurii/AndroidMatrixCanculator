package com.whitedeveloper.matrix.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.TextView;
import com.whitedeveloper.matrix.ActivityShowSavedMatrix;
import com.whitedeveloper.matrix.ManagerMatrix;
import com.whitedeveloper.matrix.R;
import com.whitedeveloper.matrix.instance.SavedInstance;

public class FragmentShowSavedMatrixOne extends Fragment {

    private SavedInstance savedInstance;
    private ManagerMatrix managerMatrix;
    private View view;
    private GridLayout glMatrixA;
    private GridLayout glMatrixB;
    private GridLayout glMatrixResult;
    private TextView tvAction;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_show_matrices_1, container, false);

        try {
            init();
        } catch (Exception e) {
            e.printStackTrace();
            return view;
        }

        setAll();
        setAction();

        return view;
    }

    private void setAction()
    {
        switch (savedInstance.getAction())
        {
            case ADDITION:
                tvAction.setText(R.string.addition_symbol);
                break;
            case SUBTRACTION:
                tvAction.setText(R.string.subtraction_symbol);
                break;
            case MULTIPLICATION:
                tvAction.setText(R.string.multiplication_symbol);
                break;

        }
    }

    private void init() throws Exception {
        managerMatrix = new ManagerMatrix(getContext());
        glMatrixA = view.findViewById(R.id.gl_matrix_a);
        glMatrixB = view.findViewById(R.id.gl_matrix_b);
        glMatrixResult = view.findViewById(R.id.gl_matrix_result);
        tvAction = view.findViewById(R.id.tv_action_symbol);
        savedInstance = new SavedInstance(getContext(),getArguments().getString(ActivityShowSavedMatrix.EXTRA_NAME_SAVING));
    }

    private void setAll() {

        glMatrixA.setEnabled(false);
        glMatrixB.setEnabled(false);
        glMatrixResult.setEnabled(false);

        managerMatrix.generateAndFillUpMatrixResult(glMatrixA, savedInstance.getMatrixA());
        managerMatrix.generateAndFillUpMatrixResult(glMatrixB, savedInstance.getMatrixB());
        managerMatrix.generateAndFillUpMatrixResult(glMatrixResult, savedInstance.getMatrixResult());

    }

}
