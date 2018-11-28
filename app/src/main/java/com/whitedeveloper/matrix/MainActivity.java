package com.whitedeveloper.matrix;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import com.whitedeveloper.matrix.fragments.*;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, AlertDialogExit.CallBack {
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private OnPressSaveResualtListener onPressSaveResualtListener;

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.option_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.menu_save:
                onPressSaveResualtListener.onPressSave();
                break;
        }

        return true;
    }

    private void setSelectedFirstTime() {
        navigationView.getMenu().getItem(0).setChecked(true);
        loadBasicActionsFragment();
    }

    private void loadBasicActionsFragment()
    {
        FragmentBaseOperationsMatrix fragmentBaseOperationsMatrix = new FragmentBaseOperationsMatrix();
        onPressSaveResualtListener = fragmentBaseOperationsMatrix;

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, fragmentBaseOperationsMatrix)
                .commit();
        setTitle(getResources().getString(R.string.basic_actions_of_matrix));
    }


    private void loadMultiplicationFragment() {

        FragmentMultiplicationMatrix multiplicationMatrix = new FragmentMultiplicationMatrix();
        onPressSaveResualtListener = multiplicationMatrix;

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container,multiplicationMatrix)
                .commit();
        setTitle(getResources().getString(R.string.multiplication_of_matrix));
    }

    private void loadTransposeFragment()
    {
        FragmentTransposeMatrix fragmentTransposeMatrix = new FragmentTransposeMatrix();
        onPressSaveResualtListener = fragmentTransposeMatrix;

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, fragmentTransposeMatrix)
                .commit();
        setTitle(getResources().getString(R.string.transpose_of_matrix));
    }

    private void loadDeterminantFragment()
    {
        FragmentDeterminantMatrix fragmentDeterminantMatrix = new FragmentDeterminantMatrix();
        onPressSaveResualtListener = fragmentDeterminantMatrix;

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container,fragmentDeterminantMatrix)
                .commit();
        setTitle(getResources().getString(R.string.determinant_of_matrix));
    }

    private void loadInverseFragment()
    {
        FragmentInversionMatrix fragmentInversionMatrix = new FragmentInversionMatrix();
        onPressSaveResualtListener = fragmentInversionMatrix;

        getSupportFragmentManager()
                .beginTransaction().
                replace(R.id.fragment_container,fragmentInversionMatrix)
                .commit();

        setTitle(getResources().getString(R.string.inverse_of_matrix));
    }

    private void startActivityAbout() {
        startActivity(new Intent(this,ActivityAbout.class));
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item)
    {
        switch (item.getItemId()) {
            case R.id.nav_addition_matrix:
                loadBasicActionsFragment();
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
            case R.id.nav_about:
                startActivityAbout();
                break;
        }

        drawerLayout.closeDrawer(GravityCompat.START);

        return true;
    }

    @Override
    public void doFinishActivity() {
        finish();
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START))
            drawerLayout.closeDrawer(GravityCompat.START);
        else
            new AlertDialogExit(this,this).showAlert();
    }

}
