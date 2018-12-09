package com.whitedeveloper.matrix.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.GridLayout;
import com.whitedeveloper.matrix.ManagerMatrix;
import com.whitedeveloper.matrix.R;
import com.whitedeveloper.matrix.instance.SavedInstance;

import static com.whitedeveloper.matrix.tags.TagKeys.EXTRA_NAME_SAVING;

public class FragmentShowSavedMatrixFour extends Fragment {

    private SavedInstance savedInstance;
    private ManagerMatrix managerMatrix;
    private View view;
    private GridLayout glMatrix;
    private GridLayout glMatrixResult;
    private EditText edtNumberK;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_show_matrices_4, container, false);

        try {
            init();
        } catch (Exception e) {
            e.printStackTrace();
            return view;
        }

        setAll();

        return view;
    }


    private void init() throws Exception {
        managerMatrix = new ManagerMatrix(getContext());
        glMatrix = view.findViewById(R.id.gl_matrix);

        glMatrixResult = view.findViewById(R.id.gl_matrix_result);
        edtNumberK = view.findViewById(R.id.edt_number);
        savedInstance = new SavedInstance(getContext(),getArguments().getString(EXTRA_NAME_SAVING));
    }

    private void setAll() {

        glMatrix.setEnabled(false);
        glMatrixResult.setEnabled(false);
        edtNumberK.setEnabled(false);

        managerMatrix.generateAndFillUpMatrixResult(glMatrix, savedInstance.getMatrixA());
        managerMatrix.generateAndFillUpMatrixResult(glMatrixResult, savedInstance.getMatrixResult());
        edtNumberK.setText(String.valueOf(savedInstance.getNumberK()));

    }

}
