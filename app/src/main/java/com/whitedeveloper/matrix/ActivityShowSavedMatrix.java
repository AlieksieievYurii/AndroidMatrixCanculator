package com.whitedeveloper.matrix;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;
import com.whitedeveloper.matrix.fragments.FragmentShowMatricesTwo;
import com.whitedeveloper.matrix.fragments.FragmentShowSavedMatrixOne;
import com.whitedeveloper.matrix.instance.GetInstance;

public class ActivityShowSavedMatrix extends AppCompatActivity {

    public static final String EXTRA_NAME_SAVING = "name_saving";
    private GetInstance getInstance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_saved_matrix);
        init();
        setFragment(getInstance.getAction());
    }

    private void setFragment(Action action) {
        Bundle bundle = new Bundle();
        bundle.putString(EXTRA_NAME_SAVING, getInstance.getNameSaving());
        setTitle(getInstance.getNameSaving());
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
                break;
            case TRANSPOSING:
                loadFragmentMatrixTwo(bundle);
                break;
            case INVERSION:
                loadFragmentMatrixTwo(bundle);
                break;
        }
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
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        try {
            getInstance = new GetInstance(this, getIntent().getStringExtra(EXTRA_NAME_SAVING));
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, R.string.text_error_opening_saving, Toast.LENGTH_SHORT).show();
            finish();
        }
    }
}
