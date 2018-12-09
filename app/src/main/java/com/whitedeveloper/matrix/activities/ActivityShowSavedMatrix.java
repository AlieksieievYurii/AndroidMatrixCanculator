package com.whitedeveloper.matrix.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;
import com.whitedeveloper.matrix.R;
import com.whitedeveloper.matrix.fragments.*;
import com.whitedeveloper.matrix.instance.SavedInstance;
import com.whitedeveloper.matrix.tags.Action;

import static com.whitedeveloper.matrix.tags.TagKeys.EXTRA_NAME_SAVING;


public class ActivityShowSavedMatrix extends AppCompatActivity {
    private SavedInstance savedInstance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_saved_matrix);
        init();
        setFragment(savedInstance.getAction());
    }

    private void setFragment(Action action) {
        Bundle bundle = new Bundle();
        bundle.putString(EXTRA_NAME_SAVING, savedInstance.getNameSaving());
        setTitle(savedInstance.getNameSaving());
        switch (action) {
            case ADDITION:

                loadFragmentMatrixOne(bundle);
                break;
            case SUBTRACTION:
                loadFragmentMatrixOne(bundle);
                break;
            case MULTIPLICATION:
                loadFragmentMatrixOne(bundle);
                break;
            case DETERMINATION:
                loadFragmentMatrixThree(bundle);
                break;
            case TRANSPOSING:
                loadFragmentMatrixTwo(bundle);
                break;
            case INVERSION:
                loadFragmentMatrixTwo(bundle);
                break;
            case SEPARATION:
                loadFragmentSeparationLU(bundle);
        }
    }

    private void loadFragmentSeparationLU(Bundle bundle)
    {
        FragmentShowMatricesLU showMatricesLU = new FragmentShowMatricesLU();
        showMatricesLU.setArguments(bundle);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container,showMatricesLU)
                .commit();
    }

    private void loadFragmentMatrixThree(Bundle bundle)
    {
        FragmentShowSavedMatrixThree showSavedMatrixThree = new FragmentShowSavedMatrixThree();
        showSavedMatrixThree.setArguments(bundle);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container,showSavedMatrixThree)
                .commit();
    }

    private void loadFragmentMatrixOne(Bundle bundle) {

        FragmentShowSavedMatrixOne showSavedMatrixOne = new FragmentShowSavedMatrixOne();
        showSavedMatrixOne.setArguments(bundle);

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fragment_container, showSavedMatrixOne)
                .commit();
    }
    private void loadFragmentMatrixTwo(Bundle bundle)
    {
        FragmentShowMatricesTwo fragmentShowMatricesTwo = new FragmentShowMatricesTwo();
        fragmentShowMatricesTwo.setArguments(bundle);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container,fragmentShowMatricesTwo)
                .commit();
    }

    private void init()
    {
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        setSupportActionBar(toolbar);

        try {
            savedInstance = new SavedInstance(this, getIntent().getStringExtra(EXTRA_NAME_SAVING));
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, R.string.text_error_opening_saving, Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
