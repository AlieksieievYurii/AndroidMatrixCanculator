package com.whitedeveloper.matrix;

import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import com.whitedeveloper.matrix.fragments.FragmentAdditionMatrix;
import com.whitedeveloper.matrix.fragments.FragmentMultiplicationMatrix;
import com.whitedeveloper.matrix.fragments.FragmentSubtractionMatrix;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
        setSelectedFirstTime();
    }

    private void init() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);

        setSupportActionBar(toolbar);

        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setSelected(true);

        ActionBarDrawerToggle toggle =
                new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }

    private void setSelectedFirstTime() {
        navigationView.getMenu().getItem(0).setChecked(true);
        loadAdditionFragment();
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START))
            drawerLayout.closeDrawer(GravityCompat.START);
        else
            super.onBackPressed();
    }

    private void loadAdditionFragment() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, new FragmentAdditionMatrix())
                .commit();
        setTitle(getResources().getString(R.string.addition_of_matrix));
    }

    private void loadSubtractionFragment() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, new FragmentSubtractionMatrix())
                .commit();
        setTitle(getResources().getString(R.string.subtraction_of_matrix));
    }

    private void loadMultiplicationFragment() {
        //TODO Implementation Multiplication of matrix!
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container,new FragmentMultiplicationMatrix())
                .commit();
        setTitle(getResources().getString(R.string.multiplication_of_matrix));
    }

    private void loadTransposeFragment() {
        //TODO Implementation Transpose of matrix!
    }

    private void loadDeterminantFragment() {
        //TODO Implementation Determinant of matrix!
    }

    private void loadInverseFragment() {
        //TODO Implementation Inverse of matrix!
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_addition_matrix:
                loadAdditionFragment();
                break;
            case R.id.nav_subtraction_matrix:
                loadSubtractionFragment();
                break;
            case R.id.nav_multiplication_matrix:
                loadMultiplicationFragment();
                break;
            case R.id.nav_transpose_matrix:
                loadTransposeFragment();
                break;
            case R.id.nav_determinant_matrix:
                loadDeterminantFragment();
                break;
            case R.id.nav_inverse_matrix:
                loadInverseFragment();
                break;
        }

        drawerLayout.closeDrawer(GravityCompat.START);

        return true;
    }
}
