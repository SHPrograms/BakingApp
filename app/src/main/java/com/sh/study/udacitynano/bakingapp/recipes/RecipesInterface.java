package com.sh.study.udacitynano.bakingapp.recipes;

import android.view.View;

import com.sh.study.udacitynano.bakingapp.model.Recipe;

interface RecipesInterface {
    void onClickIngedient(Recipe recipe);
    void onClickStep(Recipe recipe);
}
