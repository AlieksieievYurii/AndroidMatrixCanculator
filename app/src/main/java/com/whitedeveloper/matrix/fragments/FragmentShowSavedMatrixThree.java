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

public class FragmentShowSavedMatrixThree extends Fragment {

    private SavedInstance savedInstance;
    private ManagerMatrix managerMatrix;
    private View view;
    private GridLayout glMatrix;

    private TextView tvDeterminant;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_show_matrices_3, container, false);

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
        tvDeterminant = view.findViewById(R.id.tv_determinant);
        savedInstance = new SavedInstance(getContext(),getArguments().getString(ActivityShowSavedMatrix.EXTRA_NAME_SAVING));
    }

    private void setAll() {

        glMatrix.setEnabled(false);

        managerMatrix.generateAndFillUpMatrixResult(glMatrix, savedInstance.getMatrixA());
        tvDeterminant.setText(String.valueOf(savedInstance.getDeterminant()));
    }

}
