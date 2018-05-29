package com.sh.study.udacitynano.bakingapp.recipes;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;

import com.sh.study.udacitynano.bakingapp.R;
import com.sh.study.udacitynano.bakingapp.constants.SHDebug;
import com.sh.study.udacitynano.bakingapp.model.Recipe;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Main activity used to show:
 * - lists of recipes, list of ingredients for recipe for smartphones
 *
 * @author Sławomir Hagiel
 * @version 1.0
 * @since 2018-04-14
 */
public class RecipesActivity extends AppCompatActivity implements RecipesInterface {
    @SuppressWarnings("WeakerAccess")
    @BindView(R.id.ingredients_layout)
    public LinearLayout ingredientsLayout;

    private static final String CLASS_NAME = "RecipesActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipes);
        ButterKnife.bind(this);
        SHDebug.debugTag(CLASS_NAME, "onCreate:End");
        if (RecipesPreferences.getRecipePreferences(this) == null)
            ingredientsLayout.setVisibility(View.GONE);
        else this.setTitle(RecipesPreferences.getRecipePreferences(this).getName());
    }

    @Override
    public void onClickIngedient(Recipe recipe) {
        SHDebug.debugTag(CLASS_NAME, "onClickIngedient");
        if (ingredientsLayout.getVisibility() != View.VISIBLE)
            ingredientsLayout.setVisibility(View.VISIBLE);
        RecipesPreferences.setRecipePreferences(this, recipe);
        this.setTitle(recipe.getName());
    }

    @Override
    public void onClickStep(Recipe recipe) {
        SHDebug.debugTag(CLASS_NAME, "onClickStep");
        if (ingredientsLayout.getVisibility() != View.VISIBLE)
            ingredientsLayout.setVisibility(View.VISIBLE);
        RecipesPreferences.setRecipePreferences(this, recipe);
        this.setTitle(recipe.getName());
    }
}
