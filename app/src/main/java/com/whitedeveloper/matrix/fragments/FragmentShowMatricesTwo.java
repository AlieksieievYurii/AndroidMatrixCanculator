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
import com.whitedeveloper.matrix.instance.GetInstance;

public class FragmentShowMatricesTwo extends Fragment
{
    private View view;
    private GridLayout glMatrix;
    private GridLayout glMatrixResult;
    private TextView tvAction;
    private ManagerMatrix managerMatrix;
    private GetInstance getInstance;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_show_matrices_2,container,false);

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
        switch (getInstance.getAction())
        {
            case INVERSION:
                tvAction.setText(R.string.inversing);
                break;
            case TRANSPOSING:
                tvAction.setText(R.string.transposing);
                break;
        }
    }

    private void setAll()
    {
        managerMatrix.generateAndFillUpMatrixResult(glMatrix,getInstance.getMatrixA());
        managerMatrix.generateAndFillUpMatrixResult(glMatrixResult,getInstance.getMatrixResult());
    }

    private void init() throws Exception
    {
        glMatrix = view.findViewById(R.id.gl_matrix);
        glMatrixResult = view.findViewById(R.id.gl_matrix_result);
        tvAction = view.findViewById(R.id.tv_action);

        glMatrix.setEnabled(false);
        glMatrixResult.setEnabled(false);

        managerMatrix = new ManagerMatrix(getContext());
        getInstance = new GetInstance(getContext(),getArguments().getString(ActivityShowSavedMatrix.EXTRA_NAME_SAVING));

    }
}
