package com.sh.study.udacitynano.bakingapp.recipes;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.sh.study.udacitynano.bakingapp.R;
import com.sh.study.udacitynano.bakingapp.constants.SHDebug;

import butterknife.ButterKnife;

public class RecipesActivity extends AppCompatActivity implements RecipesFragment.OnRecipeClickListener {
    private static final String CLASS_NAME = "RecipesActivity";
    private boolean twoPane;

    //TODO: onSavedInstanceState save clicked recipe.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipes);
        ButterKnife.bind(this);
        SHDebug.debugTag(CLASS_NAME, "onCreate:End");
    }

    @Override
    public void onRecipeSelected(int position) {
        SHDebug.debugTag(CLASS_NAME, "onRecipeSelected");

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        SHDebug.debugTag(CLASS_NAME, "onPointerCaptureChanged");
    }
}
