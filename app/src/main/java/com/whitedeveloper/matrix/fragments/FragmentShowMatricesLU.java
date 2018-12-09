package com.whitedeveloper.matrix.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import com.whitedeveloper.matrix.ManagerMatrix;
import com.whitedeveloper.matrix.R;
import com.whitedeveloper.matrix.instance.SavedInstance;

import static com.whitedeveloper.matrix.tags.TagKeys.EXTRA_NAME_SAVING;

public class FragmentShowMatricesLU extends Fragment
{
    private View view;
    private GridLayout glMatrix;
    private GridLayout glMatrixU;
    private GridLayout glMatrixL;
    private ManagerMatrix managerMatrix;
    private SavedInstance savedInstance;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_show_matrices_lu,container,false);

        try {
            init();
        } catch (Exception e) {
            e.printStackTrace();
            return view;
        }

        setAll();

        return view;
    }

    private void setAll()
    {
        managerMatrix.generateAndFillUpMatrixResult(glMatrix, savedInstance.getMatrixA());
        managerMatrix.generateAndFillUpMatrixResult(glMatrixL, savedInstance.getMatrixL());
        managerMatrix.generateAndFillUpMatrixResult(glMatrixU, savedInstance.getMatrixU());
    }

    private void init() throws Exception
    {
        glMatrix = view.findViewById(R.id.gl_matrix);
        glMatrixL = view.findViewById(R.id.gl_matrix_l_result);
        glMatrixU = view.findViewById(R.id.gl_matrix_u_result);

        glMatrix.setEnabled(false);
        glMatrixL.setEnabled(false);
        glMatrixU.setEnabled(false);

        managerMatrix = new ManagerMatrix(getContext());
        savedInstance = new SavedInstance(getContext(),getArguments().getString(EXTRA_NAME_SAVING));

    }
}
