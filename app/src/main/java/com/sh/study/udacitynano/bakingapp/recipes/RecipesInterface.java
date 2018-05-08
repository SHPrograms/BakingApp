package com.sh.study.udacitynano.bakingapp.recipes;

import com.sh.study.udacitynano.bakingapp.model.Recipe;

/**
 * Recipe was clicked
 *
 * @author Sławomir Hagiel
 * @version 1.0
 * @since 2018-05-05
 */
interface RecipesInterface {
    void onClickIngedient(Recipe recipe);
    void onClickStep(Recipe recipe);
}
