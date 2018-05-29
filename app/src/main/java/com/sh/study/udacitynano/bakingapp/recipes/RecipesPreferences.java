package com.sh.study.udacitynano.bakingapp.recipes;

import android.content.Context;
import android.preference.PreferenceManager;

import com.google.gson.Gson;
import com.sh.study.udacitynano.bakingapp.model.Recipe;

/**
 * Collected preferences for Recipes activity
 *
 * @author Sławomir Hagiel
 * @version 1.0
 * @since 2018-05-05
 */
public final class RecipesPreferences {
    private RecipesPreferences() {
        throw new AssertionError();
    }

    static final String RECIPE = "recipe";

    public static Recipe getRecipePreferences(Context context) {
        Gson gson = new Gson();
        String json = PreferenceManager.getDefaultSharedPreferences(context)
                .getString(RECIPE, "");
        Recipe recipe = gson.fromJson(json, Recipe.class);
        return recipe;
    }

    static void setRecipePreferences(Context context, Recipe recipe) {
        Gson gson = new Gson();
        String json = gson.toJson(recipe);
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit().putString(RECIPE, json)
                .apply();
    }
}
