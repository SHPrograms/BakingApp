package com.sh.study.udacitynano.bakingapp.recipes;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.sh.study.udacitynano.bakingapp.R;

import butterknife.ButterKnife;

public class RecipesActivity extends AppCompatActivity implements RecipesFragment.OnRecipeClickListener {
    private boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipes);
        ButterKnife.bind(this);
    }

    @Override
    public void onRecipeSelected(int position) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
