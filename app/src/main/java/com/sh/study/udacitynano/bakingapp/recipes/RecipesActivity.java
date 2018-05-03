package com.sh.study.udacitynano.bakingapp.recipes;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;

import com.sh.study.udacitynano.bakingapp.R;
import com.sh.study.udacitynano.bakingapp.constants.SHDebug;
import com.sh.study.udacitynano.bakingapp.model.Recipe;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.widget.LinearLayout.*;

public class RecipesActivity extends AppCompatActivity implements RecipesFragment.OnRecipeClickListener {
    @BindView(R.id.ingredients_layout)
    public LinearLayout ingredientsLayout;

    private static final String CLASS_NAME = "RecipesActivity";
    private boolean twoPane;

    //TODO: onSavedInstanceState save clicked recipe.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipes);
        ButterKnife.bind(this);
        SHDebug.debugTag(CLASS_NAME, "onCreate:End");
/*
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
        } else {
            if (ingredientsLayout.getVisibility() == View.VISIBLE) {
                LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(
                        0,
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        2.0f
                );
                ingredientsLayout.setLayoutParams(param);
            }
            else {
                LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(
                        0,
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        0.0f
                );
                ingredientsLayout.setLayoutParams(param);
            }
        }*/
    }

    @Override
    public void onRecipeSelected(Recipe recipe) {
        SHDebug.debugTag(CLASS_NAME, "onRecipeSelected");
       ingredientsLayout.setVisibility(View.VISIBLE);
    }

}
